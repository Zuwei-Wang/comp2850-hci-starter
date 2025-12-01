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
     * Week 8 Lab 2: Added error and msg parameters for no-JS validation feedback
     */
    get("/tasks") {
        val query = call.request.queryParameters["q"].orEmpty()
        val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
        val editingId = call.request.queryParameters["editing"]?.toIntOrNull()
        val error = call.request.queryParameters["error"]
        val msg = call.request.queryParameters["msg"]
        
        val pageData = TaskRepository.search(query = query, page = page, size = 10)
        
        val model = mapOf(
            "title" to "Tasks",
            "page" to pageData,
            "query" to query,
            "editingId" to editingId,
            "error" to error,
            "msg" to msg
        )
        val template = pebble.getTemplate("tasks/index.peb")
        val writer = StringWriter()
        template.evaluate(writer, model)
        call.respondText(writer.toString(), ContentType.Text.Html)
    }

    /**
     * Week 8: GET /tasks/fragment - Return task list + pager fragments for HTMX
     * Week 9: Added metrics logging for filter tasks (T1)
     */
    get("/tasks/fragment") {
        val startTime = System.currentTimeMillis()
        
        val query = call.request.queryParameters["q"].orEmpty()
        val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
        val editingId = call.request.queryParameters["editing"]?.toIntOrNull()
        
        val pageData = TaskRepository.search(query = query, page = page, size = 10)
        
        // Week 9: Log filter request if query is not empty (T1_filter task)
        if (query.isNotEmpty()) {
            val sessionId = call.request.cookies["sid"] ?: "anonymous"
            val requestId = utils.MetricsLogger.generateRequestId()
            val duration = System.currentTimeMillis() - startTime
            
            utils.MetricsLogger.log(
                sessionId = sessionId,
                requestId = requestId,
                taskCode = "T1_filter",
                step = "success",
                outcome = "found_${pageData.total}",
                durationMs = duration,
                httpStatus = 200,
                jsMode = "on" // Fragment endpoint is HTMX-only
            )
        }
        
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
     * Week 9: Added metrics logging for evaluation
     */
    post("/tasks") {
        val startTime = System.currentTimeMillis()
        
        // Week 9: Get session and request IDs for metrics
        val sessionId = call.request.cookies["sid"] ?: "anonymous"
        val requestId = utils.MetricsLogger.generateRequestId()
        val jsMode = if (call.isHtmx()) "on" else "off"
        
        val title = call.receiveParameters()["title"].orEmpty().trim()
        
        // Validation: blank title
        if (title.isBlank()) {
            val duration = System.currentTimeMillis() - startTime
            utils.MetricsLogger.log(
                sessionId = sessionId,
                requestId = requestId,
                taskCode = "T3_add",
                step = "validation_error",
                outcome = "blank_title",
                durationMs = duration,
                httpStatus = 400,
                jsMode = jsMode
            )
            
            if (call.isHtmx()) {
                val status = """<div id="status" hx-swap-oob="true">Title is required.</div>"""
                return@post call.respondText(status, ContentType.Text.Html, HttpStatusCode.BadRequest)
            } else {
                // No-JS: redirect with error query param
                return@post call.respondRedirect("/tasks?error=title")
            }
        }

        // Validation: title too long
        if (title.length > 200) {
            val duration = System.currentTimeMillis() - startTime
            utils.MetricsLogger.log(
                sessionId = sessionId,
                requestId = requestId,
                taskCode = "T3_add",
                step = "validation_error",
                outcome = "too_long",
                durationMs = duration,
                httpStatus = 400,
                jsMode = jsMode
            )
            
            if (call.isHtmx()) {
                val status = """<div id="status" hx-swap-oob="true">Title too long (max 200 characters).</div>"""
                return@post call.respondText(status, ContentType.Text.Html, HttpStatusCode.BadRequest)
            } else {
                return@post call.respondRedirect("/tasks?error=title&msg=too_long")
            }
        }

        // Success path
        val task = TaskRepository.add(title)
        val duration = System.currentTimeMillis() - startTime
        
        // Week 9: Log successful task addition
        utils.MetricsLogger.log(
            sessionId = sessionId,
            requestId = requestId,
            taskCode = "T3_add",
            step = "success",
            outcome = "",
            durationMs = duration,
            httpStatus = 201,
            jsMode = jsMode
        )

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
        call.respondRedirect("/tasks")
    }

    /**
     * Week 8 Lab 2: DELETE /tasks/{id} - Delete task (HTMX path with confirmation)
     * Week 9: Added metrics logging
     */
    delete("/tasks/{id}") {
        val startTime = System.currentTimeMillis()
        val sessionId = call.request.cookies["sid"] ?: "anonymous"
        val requestId = utils.MetricsLogger.generateRequestId()
        val jsMode = "on" // DELETE is HTMX-only
        
        val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
        val task = TaskRepository.find(id)
        TaskRepository.delete(id)
        
        val duration = System.currentTimeMillis() - startTime
        utils.MetricsLogger.log(
            sessionId = sessionId,
            requestId = requestId,
            taskCode = "T4_delete",
            step = "success",
            outcome = "",
            durationMs = duration,
            httpStatus = 200,
            jsMode = jsMode
        )

        val status = """<div id="status" hx-swap-oob="true">Deleted "${task?.title ?: "task"}".</div>"""
        // Return empty string (outerHTML swap removes the <li>)
        call.respondText(status, ContentType.Text.Html)
    }

    /**
     * Week 8 Lab 2: POST /tasks/{id}/delete - Delete task (no-JS fallback)
     * Week 9: Added metrics logging
     */
    post("/tasks/{id}/delete") {
        val startTime = System.currentTimeMillis()
        val sessionId = call.request.cookies["sid"] ?: "anonymous"
        val requestId = utils.MetricsLogger.generateRequestId()
        val jsMode = "off" // This route is no-JS fallback
        
        val id = call.parameters["id"]?.toIntOrNull() ?: return@post call.respond(HttpStatusCode.BadRequest)
        TaskRepository.delete(id)
        
        val duration = System.currentTimeMillis() - startTime
        utils.MetricsLogger.log(
            sessionId = sessionId,
            requestId = requestId,
            taskCode = "T4_delete",
            step = "success",
            outcome = "",
            durationMs = duration,
            httpStatus = 303,
            jsMode = jsMode
        )
        
        call.respondRedirect("/tasks")
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
     * Week 9: Added metrics logging
     */
    post("/tasks/{id}") {
        val startTime = System.currentTimeMillis()
        val sessionId = call.request.cookies["sid"] ?: "anonymous"
        val requestId = utils.MetricsLogger.generateRequestId()
        val jsMode = if (call.isHtmx()) "on" else "off"
        
        val id = call.parameters["id"]?.toIntOrNull()
        val task = id?.let { TaskRepository.find(it) }

        if (task == null) {
            call.response.headers.append("Location", "/tasks")
            return@post call.respond(HttpStatusCode.SeeOther)
        }

        val newTitle = call.receiveParameters()["title"].orEmpty().trim()

        // Validation
        if (newTitle.isBlank()) {
            val duration = System.currentTimeMillis() - startTime
            utils.MetricsLogger.log(
                sessionId = sessionId,
                requestId = requestId,
                taskCode = "T2_edit",
                step = "validation_error",
                outcome = "blank_title",
                durationMs = duration,
                httpStatus = 400,
                jsMode = jsMode
            )
            
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
        
        val duration = System.currentTimeMillis() - startTime
        utils.MetricsLogger.log(
            sessionId = sessionId,
            requestId = requestId,
            taskCode = "T2_edit",
            step = "success",
            outcome = "",
            durationMs = duration,
            httpStatus = 200,
            jsMode = jsMode
        )

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
