package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SvuProgram
import com.example.ui.theme.SvuBluePrimary

@Composable
fun ScoreInputFields(
    program: SvuProgram,
    hwValue: String,
    mcqValue: String,
    essayValue: String,
    customEssayMaxScore: Double,
    customMcqMaxScore: Double,
    isArabic: Boolean,
    onHwChange: (String) -> Unit,
    onMcqChange: (String) -> Unit,
    onEssayChange: (String) -> Unit,
    onEssayMaxScoreChange: (Double) -> Unit,
    onResetClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    // Real-time validations
    val hwNum = hwValue.toDoubleOrNull()
    val isHwError = hwValue.isNotEmpty() && (hwNum == null || hwNum < 0 || hwNum > 100)

    val mcqNum = mcqValue.toDoubleOrNull()
    val isMcqError = mcqValue.isNotEmpty() && (mcqNum == null || mcqNum < 0 || mcqNum > customMcqMaxScore)

    val essayNum = essayValue.toDoubleOrNull()
    val isEssayError = essayValue.isNotEmpty() && (essayNum == null || essayNum < 0 || essayNum > customEssayMaxScore)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isArabic) "إدخال علامات الطالب المباشرة" else "Interactive Score Input",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                if (hwValue.isNotEmpty() || mcqValue.isNotEmpty() || essayValue.isNotEmpty()) {
                    IconButton(onClick = onResetClicked) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "مسح",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 1. Homework Score Input (out of 100)
            OutlinedTextField(
                value = hwValue,
                onValueChange = { onHwChange(it.filter { char -> char.isDigit() || char == '.' }) },
                label = {
                    Text(
                        if (isArabic) "1. علامة الوظيفة / العملي (من 100)" else "1. Homework Score (out of 100)"
                    )
                },
                trailingIcon = {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = "/ 100",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                },
                isError = isHwError,
                supportingText = {
                    if (isHwError) {
                        Text(
                            if (isArabic) "يرجى إدخال قيمة بين 0 و 100" else "Value must be 0-100",
                            color = MaterialTheme.colorScheme.error
                        )
                    } else if (hwNum != null && hwNum < 40.0) {
                        Text(
                            if (isArabic) "تنبيه: علامة الوظيفة أقل من 40" else "Alert: HW mark is below 40",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SvuBluePrimary
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 2. MCQ Score Input
            OutlinedTextField(
                value = mcqValue,
                onValueChange = { onMcqChange(it.filter { char -> char.isDigit() || char == '.' }) },
                label = {
                    Text(
                        if (isArabic) "2. ${program.mcqLabelAr} (من ${customMcqMaxScore.toInt()})"
                        else "2. ${program.mcqLabelEn} (out of ${customMcqMaxScore.toInt()})"
                    )
                },
                trailingIcon = {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = "/ ${customMcqMaxScore.toInt()}",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                },
                isError = isMcqError,
                supportingText = {
                    if (isMcqError) {
                        Text(
                            if (isArabic) "يرجى إدخال قيمة بين 0 و ${customMcqMaxScore.toInt()}"
                            else "Value must be 0-${customMcqMaxScore.toInt()}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SvuBluePrimary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 3. Custom Essay Max Score & Essay Score Input
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 6.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (isArabic) "تحديد درجة المقالي/المسائل لمادتك:" else "Custom Essay Max Score:",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Quick Chips selector for Custom Essay Max Score (0, 20, 30, 40, 50)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val presetOptionScores = listOf(0.0, 20.0, 30.0, 40.0, 50.0)
                        presetOptionScores.forEach { optionScore ->
                            val isSelected = (customEssayMaxScore == optionScore)
                            FilterChip(
                                selected = isSelected,
                                onClick = { onEssayMaxScoreChange(optionScore) },
                                label = {
                                    Text(
                                        text = if (optionScore == 0.0) {
                                            if (isArabic) "بدون مقالي" else "No Essay"
                                        } else {
                                            "${optionScore.toInt()}"
                                        },
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            fontSize = 11.sp
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }

                    if (customEssayMaxScore > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = essayValue,
                            onValueChange = { onEssayChange(it.filter { char -> char.isDigit() || char == '.' }) },
                            label = {
                                Text(
                                    if (isArabic) "3. علامة المقالي / المسائل (إن وجد)"
                                    else "3. Essay / Written Score (Optional)"
                                )
                            },
                            trailingIcon = {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text(
                                        text = "/ ${customEssayMaxScore.toInt()}",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    )
                                }
                            },
                            isError = isEssayError,
                            supportingText = {
                                if (isEssayError) {
                                    Text(
                                        if (isArabic) "يرجى إدخال قيمة بين 0 و ${customEssayMaxScore.toInt()}"
                                        else "Value must be 0-${customEssayMaxScore.toInt()}",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SvuBluePrimary
                            )
                        )
                    }
                }
            }
        }
    }
}
