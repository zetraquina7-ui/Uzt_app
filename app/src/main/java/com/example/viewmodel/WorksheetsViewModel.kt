package com.example.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ui.screens.Worksheet
import com.example.ui.screens.WorksheetDiscipline
import com.example.ui.screens.WorksheetYear
import com.example.ui.screens.WorksheetsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Lifecycle-aware ViewModel for the Worksheets (Fichas) subsystem.
 * Ensures:
 * 1. Safe async non-blocking loading of worksheet data from JSON/disk.
 * 2. Complete memory cleanup on exit or lifecycle teardown to prevent leaks on low-end devices.
 * 3. Reactive UI states for exercises, evaluation and solutions.
 */
class WorksheetsViewModel : ViewModel() {

    // Async loading states for smooth navigation without UI thread freeze
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _years = MutableStateFlow<List<WorksheetYear>>(emptyList())
    val years: StateFlow<List<WorksheetYear>> = _years.asStateFlow()

    private var loadJob: Job? = null

    // Current worksheet being played
    var activeWorksheet by mutableStateOf<Worksheet?>(null)
        private set

    // Player States
    var currentExerciseIndex by mutableIntStateOf(0)
    var score by mutableIntStateOf(0)

    // Answer States
    var selectedAnswer by mutableStateOf<String?>(null)
    var selectedImageAnswer by mutableStateOf<String?>(null)
    var inputAnswer by mutableStateOf("")
    var orderingList by mutableStateOf<List<String>>(emptyList())
    var matchingLeftSelected by mutableStateOf<String?>(null)
    var matchingRightSelected by mutableStateOf<String?>(null)
    var matchedPairs by mutableStateOf<Map<String, String>>(emptyMap())

    // Enhanced states for tactile Ordering
    var selectedOrderList by mutableStateOf<List<String>>(emptyList())
    var availableOrderList by mutableStateOf<List<String>>(emptyList())

    // Verification States
    var hasAnswered by mutableStateOf(false)
    var isAnswerCorrect by mutableStateOf(false)
    var showSolutionModal by mutableStateOf(false)

    init {
        // Pre-warm years asynchronously in IO dispatcher
        loadYearsAsync()
    }

    /**
     * Loads worksheet years asynchronously on Dispatchers.IO to prevent main-thread jank.
     */
    fun loadYearsAsync(context: Context? = null) {
        if (_years.value.isNotEmpty() && !WorksheetsRepository.needsReload()) {
            return
        }
        loadJob?.cancel()
        loadJob = viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val loadedYears = WorksheetsRepository.getYearsAsync(context)
                withContext(Dispatchers.Main) {
                    _years.value = loadedYears
                }
            } catch (e: Exception) {
                // Fallback to static repository if error
                withContext(Dispatchers.Main) {
                    _years.value = WorksheetsRepository.years
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Start playing a worksheet: initialize state and shuffle ordering options
     */
    fun startWorksheet(worksheet: Worksheet) {
        activeWorksheet = worksheet
        currentExerciseIndex = 0
        score = 0
        resetAnswerStatesForCurrentExercise()
    }

    /**
     * Resets answer state for the current exercise index
     */
    fun resetAnswerStatesForCurrentExercise() {
        selectedAnswer = null
        selectedImageAnswer = null
        inputAnswer = ""
        matchingLeftSelected = null
        matchingRightSelected = null
        matchedPairs = emptyMap()
        hasAnswered = false
        isAnswerCorrect = false
        showSolutionModal = false

        activeWorksheet?.exercises?.getOrNull(currentExerciseIndex)?.let { exercise ->
            orderingList = exercise.options.shuffled()
            selectedOrderList = emptyList()
            availableOrderList = exercise.options.shuffled()
        }
    }

    /**
     * Move to the next exercise
     */
    fun nextExercise(): Boolean {
        val worksheet = activeWorksheet ?: return false
        if (currentExerciseIndex < worksheet.exercises.lastIndex) {
            currentExerciseIndex++
            resetAnswerStatesForCurrentExercise()
            return true
        }
        return false
    }

    /**
     * Clears all worksheet states when exiting the worksheet player screen
     * to prevent memory retention of large items, images, lists, etc. on low-end devices.
     */
    fun clearWorksheetState() {
        activeWorksheet = null
        currentExerciseIndex = 0
        score = 0
        selectedAnswer = null
        selectedImageAnswer = null
        inputAnswer = ""
        orderingList = emptyList()
        matchingLeftSelected = null
        matchingRightSelected = null
        matchedPairs = emptyMap()
        selectedOrderList = emptyList()
        availableOrderList = emptyList()
        hasAnswered = false
        isAnswerCorrect = false
        showSolutionModal = false
    }

    override fun onCleared() {
        super.onCleared()
        loadJob?.cancel()
        loadJob = null
        clearWorksheetState()
    }
}

