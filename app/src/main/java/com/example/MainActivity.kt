package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.FooterSection
import com.example.ui.components.GradebookSheet
import com.example.ui.components.HeaderBanner
import com.example.ui.components.PrivacyPolicyModal
import com.example.ui.components.ProgramSelectorCard
import com.example.ui.components.ResultDisplayCard
import com.example.ui.components.ScoreInputFields
import com.example.ui.components.TargetScoreCard
import com.example.ui.theme.JbMarkSvuTheme
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JbMarkSvuApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JbMarkSvuApp(viewModel: MainViewModel = viewModel()) {
    val selectedProgram by viewModel.selectedProgram.collectAsStateWithLifecycle()
    val currentHwWeight by viewModel.currentHwWeight.collectAsStateWithLifecycle()
    val hwInput by viewModel.hwInput.collectAsStateWithLifecycle()
    val mcqInput by viewModel.mcqInput.collectAsStateWithLifecycle()
    val essayInput by viewModel.essayInput.collectAsStateWithLifecycle()
    val calculationResult by viewModel.calculationResult.collectAsStateWithLifecycle()
    val targetExamScore by viewModel.targetExamScore.collectAsStateWithLifecycle()
    val savedSubjects by viewModel.savedSubjects.collectAsStateWithLifecycle()
    val customEssayMaxScore by viewModel.customEssayMaxScore.collectAsStateWithLifecycle()
    val customMcqMaxScore by viewModel.customMcqMaxScore.collectAsStateWithLifecycle()
    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
    val isArabic by viewModel.isArabic.collectAsStateWithLifecycle()
    val showPrivacyModal by viewModel.showPrivacyModal.collectAsStateWithLifecycle()
    val showGradebookSheet by viewModel.showGradebookSheet.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    JbMarkSvuTheme(darkTheme = isDarkTheme) {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                containerColor = MaterialTheme.colorScheme.background
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    // Top Header Banner
                    HeaderBanner(
                        savedCount = savedSubjects.size,
                        isDark = isDarkTheme,
                        isArabic = isArabic,
                        onToggleTheme = { viewModel.toggleTheme() },
                        onToggleLanguage = { viewModel.toggleLanguage() },
                        onOpenGradebook = { viewModel.setShowGradebookSheet(true) }
                    )

                    // Scrollable Body
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // 1. Faculty / Program Selector Card
                        ProgramSelectorCard(
                            selectedProgram = selectedProgram,
                            currentHwWeight = currentHwWeight,
                            isArabic = isArabic,
                            onProgramSelected = { viewModel.selectProgram(it) },
                            onHwWeightChanged = { viewModel.setHwWeight(it) }
                        )

                        // 2. Interactive Score Input Fields
                        ScoreInputFields(
                            program = selectedProgram,
                            hwValue = hwInput,
                            mcqValue = mcqInput,
                            essayValue = essayInput,
                            customEssayMaxScore = customEssayMaxScore,
                            customMcqMaxScore = customMcqMaxScore,
                            isArabic = isArabic,
                            onHwChange = { viewModel.onHwInputChange(it) },
                            onMcqChange = { viewModel.onMcqInputChange(it) },
                            onEssayChange = { viewModel.onEssayInputChange(it) },
                            onEssayMaxScoreChange = { viewModel.setCustomEssayMaxScore(it) },
                            onResetClicked = { viewModel.resetInputs() }
                        )

                        // 3. Target Exam Score Calculator (if only HW entered)
                        AnimatedVisibility(
                            visible = targetExamScore != null,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            targetExamScore?.let { target ->
                                TargetScoreCard(
                                    targetResult = target,
                                    isArabic = isArabic
                                )
                            }
                        }

                        // 4. Full Calculation Result Card
                        AnimatedVisibility(
                            visible = calculationResult != null,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            calculationResult?.let { result ->
                                ResultDisplayCard(
                                    result = result,
                                    isArabic = isArabic,
                                    onSaveSubject = { name -> viewModel.saveCurrentResultToGradebook(name) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // 5. Footer with Thuluth Developer Signature & Social Links
                        FooterSection(
                            isDark = isDarkTheme,
                            isArabic = isArabic,
                            onOpenPrivacyModal = { viewModel.setShowPrivacyModal(true) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Privacy Policy Modal Dialog
                if (showPrivacyModal) {
                    PrivacyPolicyModal(
                        isArabic = isArabic,
                        onDismiss = { viewModel.setShowPrivacyModal(false) }
                    )
                }

                // Gradebook Bottom Sheet
                if (showGradebookSheet) {
                    GradebookSheet(
                        savedSubjects = savedSubjects,
                        isArabic = isArabic,
                        sheetState = sheetState,
                        onDismiss = { viewModel.setShowGradebookSheet(false) },
                        onDeleteSubject = { id -> viewModel.deleteSavedSubject(id) },
                        onClearAll = { viewModel.clearAllSavedSubjects() }
                    )
                }
            }
        }
    }
}
