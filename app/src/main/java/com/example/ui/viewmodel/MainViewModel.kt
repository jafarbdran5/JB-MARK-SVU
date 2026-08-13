package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.SavedSubjectEntity
import com.example.data.local.SubjectRepository
import com.example.data.model.CalculationResult
import com.example.data.model.SvuPresets
import com.example.data.model.SvuProgram
import com.example.data.model.TargetExamScoreResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SubjectRepository

    val savedSubjects: StateFlow<List<SavedSubjectEntity>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = SubjectRepository(database.savedSubjectDao())
        savedSubjects = repository.allSavedSubjects.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    private val _selectedProgram = MutableStateFlow(SvuPresets.allPrograms.first())
    val selectedProgram: StateFlow<SvuProgram> = _selectedProgram.asStateFlow()

    private val _currentHwWeight = MutableStateFlow(SvuPresets.allPrograms.first().defaultHwWeight)
    val currentHwWeight: StateFlow<Double> = _currentHwWeight.asStateFlow()

    private val _hwInput = MutableStateFlow("")
    val hwInput: StateFlow<String> = _hwInput.asStateFlow()

    private val _mcqInput = MutableStateFlow("")
    val mcqInput: StateFlow<String> = _mcqInput.asStateFlow()

    private val _essayInput = MutableStateFlow("")
    val essayInput: StateFlow<String> = _essayInput.asStateFlow()

    private val _calculationResult = MutableStateFlow<CalculationResult?>(null)
    val calculationResult: StateFlow<CalculationResult?> = _calculationResult.asStateFlow()

    private val _targetExamScore = MutableStateFlow<TargetExamScoreResult?>(null)
    val targetExamScore: StateFlow<TargetExamScoreResult?> = _targetExamScore.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _isArabic = MutableStateFlow(true)
    val isArabic: StateFlow<Boolean> = _isArabic.asStateFlow()

    private val _showPrivacyModal = MutableStateFlow(false)
    val showPrivacyModal: StateFlow<Boolean> = _showPrivacyModal.asStateFlow()

    private val _showGradebookSheet = MutableStateFlow(false)
    val showGradebookSheet: StateFlow<Boolean> = _showGradebookSheet.asStateFlow()

    private val _customEssayMaxScore = MutableStateFlow(30.0)
    val customEssayMaxScore: StateFlow<Double> = _customEssayMaxScore.asStateFlow()

    private val _customMcqMaxScore = MutableStateFlow(70.0)
    val customMcqMaxScore: StateFlow<Double> = _customMcqMaxScore.asStateFlow()

    fun selectProgram(program: SvuProgram) {
        _selectedProgram.value = program
        _currentHwWeight.value = program.defaultHwWeight
        _customEssayMaxScore.value = if (program.hasEssay) program.essayMaxScore else 0.0
        _customMcqMaxScore.value = if (program.hasEssay) (100.0 - program.essayMaxScore) else program.mcqMaxScore
        recalculateIfPossible()
    }

    fun setCustomEssayMaxScore(maxScore: Double) {
        val clampedEssayMax = maxScore.coerceIn(0.0, 100.0)
        _customEssayMaxScore.value = clampedEssayMax
        _customMcqMaxScore.value = 100.0 - clampedEssayMax
        recalculateIfPossible()
    }

    fun setHwWeight(weight: Double) {
        _currentHwWeight.value = weight
        recalculateIfPossible()
    }

    fun onHwInputChange(value: String) {
        _hwInput.value = value
        recalculateIfPossible()
    }

    fun onMcqInputChange(value: String) {
        _mcqInput.value = value
        recalculateIfPossible()
    }

    fun onEssayInputChange(value: String) {
        _essayInput.value = value
        recalculateIfPossible()
    }

    private fun recalculateIfPossible() {
        val program = _selectedProgram.value
        val weight = _currentHwWeight.value
        val essayMax = _customEssayMaxScore.value
        val mcqMax = _customMcqMaxScore.value

        val hwNum = _hwInput.value.toDoubleOrNull()
        val mcqNum = _mcqInput.value.toDoubleOrNull()
        val essayNumRaw = _essayInput.value.toDoubleOrNull()
        val essayNum = essayNumRaw ?: 0.0

        val isMcqError = mcqNum != null && (mcqNum < 0 || mcqNum > mcqMax)
        val isEssayError = _essayInput.value.isNotEmpty() && (essayNumRaw == null || essayNumRaw < 0 || essayNumRaw > essayMax)

        // 1. Target score calculation if only HW is entered
        if (hwNum != null && mcqNum == null && (_mcqInput.value.isEmpty() && _essayInput.value.isEmpty())) {
            _targetExamScore.value = SvuPresets.calculateTargetExamScore(
                program = program,
                hwWeight = weight,
                hwInput = hwNum
            )
        } else {
            _targetExamScore.value = null
        }

        // 2. Full calculation if Exam marks are also provided
        if (hwNum != null && mcqNum != null && !isMcqError && !isEssayError) {
            _calculationResult.value = SvuPresets.calculateGrade(
                program = program,
                hwWeight = weight,
                hwInput = hwNum,
                mcqInput = mcqNum,
                essayInput = essayNum
            )
        } else {
            _calculationResult.value = null
        }
    }

    fun calculateGradeManual() {
        val program = _selectedProgram.value
        val weight = _currentHwWeight.value
        val hwNum = _hwInput.value.toDoubleOrNull() ?: 0.0
        val mcqNum = _mcqInput.value.toDoubleOrNull() ?: 0.0
        val essayNum = _essayInput.value.toDoubleOrNull() ?: 0.0

        _calculationResult.value = SvuPresets.calculateGrade(
            program = program,
            hwWeight = weight,
            hwInput = hwNum,
            mcqInput = mcqNum,
            essayInput = essayNum
        )
    }

    fun resetInputs() {
        _hwInput.value = ""
        _mcqInput.value = ""
        _essayInput.value = ""
        _calculationResult.value = null
        _targetExamScore.value = null
    }

    fun saveCurrentResultToGradebook(subjectName: String) {
        val result = _calculationResult.value ?: return
        val program = _selectedProgram.value

        viewModelScope.launch {
            val entity = SavedSubjectEntity(
                subjectName = subjectName,
                programCode = program.code,
                programNameAr = program.nameAr,
                hwScore = result.hwInput,
                mcqScore = result.mcqInput,
                essayScore = result.essayInput,
                examTotal = result.examTotal,
                finalGrade = result.finalGrade,
                displayedGrade = result.displayedGrade,
                statusName = result.status.name,
                hwWeight = result.hwWeightUsed
            )
            repository.saveSubject(entity)
        }
    }

    fun deleteSavedSubject(id: Long) {
        viewModelScope.launch {
            repository.deleteSubject(id)
        }
    }

    fun clearAllSavedSubjects() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun toggleLanguage() {
        _isArabic.value = !_isArabic.value
    }

    fun setShowPrivacyModal(show: Boolean) {
        _showPrivacyModal.value = show
    }

    fun setShowGradebookSheet(show: Boolean) {
        _showGradebookSheet.value = show
    }
}
