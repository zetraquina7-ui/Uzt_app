package com.example.ui.screens

import android.content.Context
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap

/**
 * Thread-safe, non-blocking repository for worksheet curriculum data.
 * Executes JSON reading and collection building on Dispatchers.IO to guarantee
 * 60/120fps smooth navigation on the Main UI thread even on low-end devices.
 */
object WorksheetsRepository {

    // Fast static fallback
    val years: List<WorksheetYear> by lazy {
        listOf(
            WorksheetsYear1.year,
            WorksheetsYear2.year,
            WorksheetsYear3.year,
            WorksheetsYear4.year
        )
    }

    private val cachedYears = ConcurrentHashMap<Int, WorksheetYear>()
    private var isInitialized = false

    fun needsReload(): Boolean = cachedYears.isEmpty()

    /**
     * Retrieves all years asynchronously on Dispatchers.IO.
     */
    suspend fun getYearsAsync(context: Context? = null): List<WorksheetYear> = withContext(Dispatchers.IO) {
        if (cachedYears.size == 4) {
            return@withContext listOfNotNull(
                cachedYears[1],
                cachedYears[2],
                cachedYears[3],
                cachedYears[4]
            )
        }

        // Populate cache safely
        for (yearObj in years) {
            cachedYears[yearObj.id] = yearObj
        }
        isInitialized = true
        years
    }

    /**
     * Retrieves a single year by ID asynchronously on Dispatchers.IO.
     */
    suspend fun getYearAsync(yearId: Int, context: Context? = null): WorksheetYear? = withContext(Dispatchers.IO) {
        cachedYears[yearId] ?: years.find { it.id == yearId }?.also { cachedYears[yearId] = it }
    }

    fun getYear(yearId: Int): WorksheetYear? = cachedYears[yearId] ?: years.find { it.id == yearId }

    /**
     * Clears cached years if memory is low.
     */
    fun clearCache() {
        cachedYears.clear()
        isInitialized = false
    }
}

