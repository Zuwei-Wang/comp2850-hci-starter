package data

import java.io.File
import java.util.concurrent.atomic.AtomicInteger

/**
 * NOTE FOR NON-INTELLIJ IDEs (VSCode, Eclipse, etc.):
 * IntelliJ IDEA automatically adds imports as you type. If using a different IDE,
 * you may need to manually add imports. The commented imports below show what you'll need
 * for future weeks. Uncomment them as needed when following the lab instructions.
 *
 * When using IntelliJ: You can ignore the commented imports below - your IDE will handle them.
 */

// Week 7+ imports (no new imports needed for find/update methods)

// Week 8+ imports (search functionality added, no new imports needed)

// Week 10+ evolution note:
// In the solution repo, this file is split into two separate files:
//
// 1. model/Task.kt (data class with validation):
//    import java.time.LocalDateTime
//    import java.time.format.DateTimeFormatter
//    import java.util.UUID
//
// 2. storage/TaskStore.kt (CSV persistence using Apache Commons CSV):
//    import model.Task
//    import org.apache.commons.csv.CSVFormat
//    import org.apache.commons.csv.CSVParser
//    import org.apache.commons.csv.CSVPrinter
//    import java.io.FileReader
//    import java.io.FileWriter
//    import java.time.format.DateTimeParseException

/**
 * Simple task data model for Week 6.
 *
 * **Week 7 evolution**: Add `completed: Boolean` field
 * **Week 8 evolution**: Add `createdAt` timestamp for sorting
 */
data class Task(val id: Int, var title: String)

/**
 * Week 8: Page data class for pagination
 */
data class Page<T>(
    val items: List<T>,
    val page: Int,
    val size: Int,
    val total: Int
) {
    val pages: Int get() = if (total == 0) 1 else (total + size - 1) / size
}

/**
 * In-memory repository with CSV persistence.
 *
 * **Simple approach for Week 6**: Singleton object with integer IDs
 * **Week 10 evolution**: Refactor to class with UUID for production-readiness
 */
object TaskRepository {
    private val file = File("data/tasks.csv")
    private val tasks = mutableListOf<Task>()
    private val idCounter = AtomicInteger(1)

    init {
        file.parentFile?.mkdirs()
        if (!file.exists()) {
            file.writeText("id,title\n")
        } else {
            file.readLines().drop(1).forEach { line ->
                val parts = line.split(",", limit = 2)
                if (parts.size == 2) {
                    val id = parts[0].toIntOrNull() ?: return@forEach
                    tasks.add(Task(id, parts[1]))
                    idCounter.set(maxOf(idCounter.get(), id + 1))
                }
            }
        }
    }

    fun all(): List<Task> = tasks.toList()

    fun add(title: String): Task {
        val task = Task(idCounter.getAndIncrement(), title)
        tasks.add(task)
        persist()
        return task
    }

    fun delete(id: Int): Boolean {
        val removed = tasks.removeIf { it.id == id }
        if (removed) persist()
        return removed
    }

    /**
     * Week 7: Find task by ID
     * Returns null if not found
     */
    fun find(id: Int): Task? = tasks.find { it.id == id }

    /**
     * Week 7: Update existing task
     * Modifies the task in memory and persists to CSV
     */
    fun update(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
            persist()
        }
    }

    /**
     * Week 8: Search and paginate tasks
     * @param query Filter string (searches in title, case-insensitive)
     * @param page Page number (1-indexed)
     * @param size Items per page
     * @return Page object with filtered and paginated results
     */
    fun search(query: String = "", page: Int = 1, size: Int = 10): Page<Task> {
        // Filter tasks by query
        val filtered = if (query.isBlank()) {
            tasks.toList()
        } else {
            tasks.filter { it.title.contains(query, ignoreCase = true) }
        }

        val total = filtered.size
        val safePage = page.coerceIn(1, maxOf(1, (total + size - 1) / size))
        val start = (safePage - 1) * size
        val end = minOf(start + size, total)

        val items = if (start < total) filtered.subList(start, end) else emptyList()

        return Page(
            items = items,
            page = safePage,
            size = size,
            total = total
        )
    }

    private fun persist() {
        file.writeText("id,title\n" + tasks.joinToString("\n") { "${it.id},${it.title}" })
    }
}
