package utils

import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.UUID

/**
 * Week 9: Server-side metrics logging for usability evaluation
 * 
 * Logs task completion events to CSV for analysis.
 * Privacy by design: anonymous session IDs, no PII.
 */
object MetricsLogger {
    private val metricsFile = File("data/metrics.csv")
    private val formatter = DateTimeFormatter.ISO_INSTANT

    /**
     * Initialize metrics file with CSV header if it doesn't exist
     */
    fun init() {
        if (!metricsFile.exists()) {
            metricsFile.parentFile?.mkdirs()
            metricsFile.writeText("ts_iso,session_id,request_id,task_code,step,outcome,ms,http_status,js_mode\n")
        }
    }

    /**
     * Log a metrics event
     * 
     * @param sessionId Anonymous session token (e.g., "7a9f2c")
     * @param requestId Unique request identifier (e.g., UUID)
     * @param taskCode Task identifier (e.g., "T1_filter", "T2_edit", "T3_add", "T4_delete")
     * @param step Event type: "start", "success", "validation_error", "server_error"
     * @param outcome Additional details (e.g., "blank_title" for validation errors)
     * @param durationMs Duration in milliseconds (0 for start events)
     * @param httpStatus HTTP status code (200, 400, 500, etc.)
     * @param jsMode JavaScript mode: "on", "off", "unknown"
     */
    fun log(
        sessionId: String,
        requestId: String,
        taskCode: String,
        step: String,
        outcome: String = "",
        durationMs: Long = 0,
        httpStatus: Int = 200,
        jsMode: String = "unknown"
    ) {
        val timestamp = formatter.format(Instant.now())
        val line = "$timestamp,$sessionId,$requestId,$taskCode,$step,$outcome,$durationMs,$httpStatus,$jsMode\n"
        
        synchronized(metricsFile) {
            metricsFile.appendText(line)
        }
    }

    /**
     * Generate a unique request ID
     */
    fun generateRequestId(): String = UUID.randomUUID().toString().substring(0, 8)

    /**
     * Get session ID from cookie or generate anonymous one
     * Privacy: no tracking of personal information
     */
    fun getOrCreateSessionId(cookies: Map<String, String>): String {
        return cookies["sid"] ?: generateSessionId()
    }

    /**
     * Generate anonymous session ID (6-character hex token)
     * Example: "7a9f2c"
     */
    private fun generateSessionId(): String {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6)
    }

    /**
     * Detect JS mode from HTTP headers
     * HTMX requests include HX-Request: true header
     */
    fun detectJsMode(headers: Map<String, String>): String {
        return if (headers["HX-Request"]?.equals("true", ignoreCase = true) == true) {
            "on"
        } else {
            // Could also be "unknown" if we can't determine
            // For simplicity, assume no HX-Request header = JS off
            "off"
        }
    }
}
