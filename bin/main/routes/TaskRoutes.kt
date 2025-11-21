package routes

import data.TaskRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.pebbletemplates.pebble.PebbleEngine
import java.io.StringWriter

/**
 * NOTE FOR NON-INTELLIJ IDEs (VSCode, Eclipse, etc.):
 * IntelliJ IDEA automatically adds imports as you type. If using a different IDE,
 * you may need to manually add imports. The commented imports below show what you'll need
 * for future weeks. Uncomment them as needed when following the lab instructions.
 *
 * When using IntelliJ: You can ignore the commented imports below - your IDE will handle them.
 */

// Week 7+ imports (inline edit, toggle completion):
// import model.Task               // When Task becomes separate model class
// import model.ValidationResult   // For validation errors
// import renderTemplate            // Extension function from Main.kt
// import isHtmxRequest             // Extension function from Main.kt

// Week 8+ imports (pagination, search, URL encoding):
// import io.ktor.http.encodeURLParameter  // For query parameter encoding
// import utils.Page                       // Pagination helper class

// Week 9+ imports (metrics logging, instrumentation):
// import utils.jsMode              // Detect JS mode (htmx/nojs)
// import utils.logValidationError  // Log validation failures
// import utils.timed               // Measure request timing

// Note: Solution repo uses storage.TaskStore instead of data.TaskRepository
// You may refactor to this in Week 10 for production readiness

/**
 * Week 6 Lab 1: Simple task routes with HTMX progressive enhancement.
 *
 * **Teaching approach**: Start simple, evolve incrementally
 * - Week 6: Basic CRUD with Int IDs
 * - Week 7: Add toggle, inline edit
 * - Week 8: Add pagination, search
 */

fun Route.taskRoutes() {
    val pebble = PebbleEngine.Builder()
        .loader(io.pebbletemplates.pebble.loader.ClasspathLoader().apply {
            prefix = "templates/"
        })
        .build()

    /**
     * Helper: Check if request is from HTMX
     */
    fun ApplicationCall.isHtmx(): Boolean =
        request.headers["HX-Request"]?.equals("true", ignoreCase = true) == true

    /**
     * GET /tasks - List all tasks with pagination and filtering
     * Week 8: Added query and page parameters
     */
    get("/tasks") {
        val query = call.request.queryParameters["q"].orEmpty()
        val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
        val editingId = call.request.queryParameters["editing"]?.toIntOrNull()
        
        val pageData = TaskRepository.search(query = query, page = page, size = 10)
        
        val model = mapOf(
            "title" to "Tasks",
            "page" to pageData,
            "query" to query,
            "editingId" to editingId
        )
        val template = pebble.getTemplate("tasks/index.peb")
        val writer = StringWriter()
        template.evaluate(writer, model)
        call.respondText(writer.toString(), ContentType.Text.Html)
    }

    /**
     * Week 8: GET /tasks/fragment - Return task list + pager fragments for HTMX
     */
    get("/tasks/fragment") {
        val query = call.request.queryParameters["q"].orEmpty()
        val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
        val editingId = call.request.queryParameters["editing"]?.toIntOrNull()
        
        val pageData = TaskRepository.search(query = query, page = page, size = 10)
        
        val model = mapOf(
            "page" to pageData,
            "query" to query,
            "editingId" to editingId
        )
        
        // Render list partial
        val listTemplate = pebble.getTemplate("tasks/_list.peb")
        val listWriter = StringWriter()
        listTemplate.evaluate(listWriter, model)
        
        // Render pager partial
        val pagerTemplate = pebble.getTemplate("tasks/_pager.peb")
        val pagerWriter = StringWriter()
        pagerTemplate.evaluate(pagerWriter, model)
        
        // Status update with result count
        val status = """<div id="status" hx-swap-oob="true">Found ${pageData.total} task${if (pageData.total != 1) "s" else ""}${if (query.isNotEmpty()) " matching \"$query\"" else ""}.</div>"""
        
        call.respondText(listWriter.toString() + pagerWriter.toString() + status, ContentType.Text.Html)
    }

    /**
     * POST /tasks - Add new task
     * Dual-mode: HTMX fragment or PRG redirect
     */
    post("/tasks") {
        val title = call.receiveParameters()["title"].orEmpty().trim()
        // Validation
        if (title.isBlank()) {
            // Validation error handling
            if (call.isHtmx()) {
                // Week 7 fix: Use #error region with assertive ARIA
                val error = """<div id="error" hx-swap-oob="true">Title is required. Please enter at least one character.</div>"""
                return@post call.respondText(error, ContentType.Text.Html, HttpStatusCode.BadRequest)
            } else {
                // No-JS: redirect back (could add error query param)
                call.response.headers.append("Location", "/tasks")
                return@post call.respond(HttpStatusCode.SeeOther)
            }
        }

        val task = TaskRepository.add(title)

        if (call.isHtmx()) {
            // Return HTML fragment for new task
            val fragment = """<li id="task-${task.id}">
                <span>${task.title}</span>
                <form action="/tasks/${task.id}/delete" method="post" style="display: inline;"
                      hx-post="/tasks/${task.id}/delete"
                      hx-target="#task-${task.id}"
                      hx-swap="outerHTML">
                  <button type="submit" aria-label="Delete task: ${task.title}">Delete</button>
                </form>
            </li>"""

            val status = """<div id="status" hx-swap-oob="true">Task "${task.title}" added successfully.</div>"""

            return@post call.respondText(fragment + status, ContentType.Text.Html, HttpStatusCode.Created)
        }

        // No-JS: POST-Redirect-GET pattern (303 See Other)
        call.response.headers.append("Location", "/tasks")
        call.respond(HttpStatusCode.SeeOther)
    }

    /**
     * POST /tasks/{id}/delete - Delete task
     * Dual-mode: HTMX empty response or PRG redirect
     */
    post("/tasks/{id}/delete") {
        val id = call.parameters["id"]?.toIntOrNull()
        val removed = id?.let { TaskRepository.delete(it) } ?: false

        if (call.isHtmx()) {
            val message = if (removed) "Task deleted." else "Could not delete task."
            val status = """<div id="status" hx-swap-oob="true">$message</div>"""
            // Return empty content to trigger outerHTML swap (removes the <li>)
            return@post call.respondText(status, ContentType.Text.Html)
        }

        // No-JS: POST-Redirect-GET pattern (303 See Other)
        call.response.headers.append("Location", "/tasks")
        call.respond(HttpStatusCode.SeeOther)
    }

    /**
     * Week 7: GET /tasks/{id}/edit - Show edit form
     * HTMX: Returns edit form fragment
     * No-JS: Redirects to full page with ?editing={id}
     */
    get("/tasks/{id}/edit") {
        val id = call.parameters["id"]?.toIntOrNull()
        val task = id?.let { TaskRepository.find(it) }

        if (task == null) {
            call.response.headers.append("Location", "/tasks")
            return@get call.respond(HttpStatusCode.SeeOther)
        }

        if (call.isHtmx()) {
            // Return edit form fragment
            val template = pebble.getTemplate("tasks/_edit.peb")
            val writer = StringWriter()
            template.evaluate(writer, mapOf("task" to task))
            return@get call.respondText(writer.toString(), ContentType.Text.Html)
        }

        // No-JS: Redirect to index with editing parameter
        call.response.headers.append("Location", "/tasks?editing=${task.id}")
        call.respond(HttpStatusCode.SeeOther)
    }

    /**
     * Week 7: POST /tasks/{id} - Save edited task
     * Dual-mode with validation
     */
    post("/tasks/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        val task = id?.let { TaskRepository.find(it) }

        if (task == null) {
            call.response.headers.append("Location", "/tasks")
            return@post call.respond(HttpStatusCode.SeeOther)
        }

        val newTitle = call.receiveParameters()["title"].orEmpty().trim()

        // Validation
        if (newTitle.isBlank()) {
            if (call.isHtmx()) {
                // Return edit form with error
                val template = pebble.getTemplate("tasks/_edit.peb")
                val writer = StringWriter()
                template.evaluate(writer, mapOf(
                    "task" to task,
                    "error" to "Title is required. Please enter at least one character."
                ))
                // Week 7 fix: Use #error region with assertive ARIA
                val errorMsg = """<div id="error" hx-swap-oob="true">Title cannot be blank.</div>"""
                return@post call.respondText(writer.toString() + errorMsg, ContentType.Text.Html, HttpStatusCode.BadRequest)
            } else {
                // No-JS: Redirect back to edit mode
                call.response.headers.append("Location", "/tasks?editing=${task.id}")
                return@post call.respond(HttpStatusCode.SeeOther)
            }
        }

        // Update task
        task.title = newTitle
        TaskRepository.update(task)

        if (call.isHtmx()) {
            // Return updated view fragment
            val template = pebble.getTemplate("tasks/_item.peb")
            val writer = StringWriter()
            template.evaluate(writer, mapOf("task" to task))
            val status = """<div id="status" hx-swap-oob="true">Task "${task.title}" updated successfully.</div>"""
            return@post call.respondText(writer.toString() + status, ContentType.Text.Html)
        }

        // No-JS: Redirect to tasks list
        call.response.headers.append("Location", "/tasks")
        call.respond(HttpStatusCode.SeeOther)
    }

    /**
     * Week 7: GET /tasks/{id}/view - Cancel edit (HTMX only)
     * Returns view mode fragment
     */
    get("/tasks/{id}/view") {
        val id = call.parameters["id"]?.toIntOrNull()
        val task = id?.let { TaskRepository.find(it) }

        if (task == null) {
            return@get call.respondText("", ContentType.Text.Html, HttpStatusCode.NotFound)
        }

        val template = pebble.getTemplate("tasks/_item.peb")
        val writer = StringWriter()
        template.evaluate(writer, mapOf("task" to task))
        call.respondText(writer.toString(), ContentType.Text.Html)
    }
}
