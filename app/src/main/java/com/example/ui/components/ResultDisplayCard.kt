package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CalculationResult
import com.example.data.model.PassStatus
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.CrimsonFail
import com.example.ui.theme.EmeraldPass
import com.example.ui.theme.SvuBluePrimary

@Composable
fun ResultDisplayCard(
    result: CalculationResult,
    isArabic: Boolean,
    onSaveSubject: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var subjectNameInput by remember { mutableStateOf("") }
    var isSaved by remember { mutableStateOf(false) }

    val statusColor = when (result.status) {
        PassStatus.PASS -> EmeraldPass
        PassStatus.CONDITIONAL_FAIL -> AmberWarning
        PassStatus.TOTAL_FAIL -> CrimsonFail
    }

    val statusIcon = when (result.status) {
        PassStatus.PASS -> Icons.Default.CheckCircle
        PassStatus.CONDITIONAL_FAIL -> Icons.Default.Warning
        PassStatus.TOTAL_FAIL -> Icons.Default.Error
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            // Header Result Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = statusColor.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, statusColor)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = statusIcon,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isArabic) result.status.titleAr else result.status.titleEn,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = statusColor
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Grade,
                    contentDescription = null,
                    tint = SvuBluePrimary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Large Score Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                statusColor.copy(alpha = 0.12f),
                                statusColor.copy(alpha = 0.04f)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = statusColor.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isArabic) "درجة المحصلة المسجلة" else "Registered Final Grade",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = String.format("%.2f", result.displayedGrade),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = statusColor,
                                fontSize = 38.sp
                            )
                        )
                        Text(
                            text = " / 100",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                        )
                    }

                    if (result.status == PassStatus.CONDITIONAL_FAIL) {
                        Text(
                            text = if (isArabic) "(سجلت درجة الامتحان فقط دون الوظيفة بسبب الرسوب الشرطي)"
                            else "(Recorded exam mark only due to failing exam threshold)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = AmberWarning,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Explanation Message
            Text(
                text = if (isArabic) result.statusMessageAr else result.statusMessageEn,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            // HW Warning if < 40
            result.hwWarningMessageAr?.let { warning ->
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = CrimsonFail.copy(alpha = 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CrimsonFail.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = CrimsonFail,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = warning,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = CrimsonFail,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(12.dp))

            // Detailed Breakdown
            Text(
                text = if (isArabic) "تفاصيل الحساب والمعاملات:" else "Detailed Calculation Breakdown:",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            val hwPercentLabel = (result.hwWeightUsed * 100).toInt()
            val examPercentLabel = ((1.0 - result.hwWeightUsed) * 100).toInt()

            BreakdownRow(
                label = if (isArabic) "علامة الوظيفة ($hwPercentLabel%)" else "Homework ($hwPercentLabel%)",
                rawScore = "${result.hwInput} / 100",
                weightedScore = String.format("%.2f", result.hwWeighted)
            )

            BreakdownRow(
                label = if (isArabic) "مجموع الامتحان الأتمتة والمقالي" else "Exam Total (MCQ + Essay)",
                rawScore = "${result.mcqInput} + ${result.essayInput} = ${result.examTotal}",
                weightedScore = "${result.examTotal} / 100"
            )

            BreakdownRow(
                label = if (isArabic) "إسهام الامتحان النهائي ($examPercentLabel%)" else "Exam Contribution ($examPercentLabel%)",
                rawScore = "${result.examTotal} × ${1.0 - result.hwWeightUsed}",
                weightedScore = String.format("%.2f", result.examWeighted)
            )

            BreakdownRow(
                label = if (isArabic) "المحصلة النظرية قبل الشروط" else "Theoretical Total Grade",
                rawScore = "${String.format("%.2f", result.hwWeighted)} + ${String.format("%.2f", result.examWeighted)}",
                weightedScore = String.format("%.2f", result.finalGrade),
                isHighlight = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Save to Gradebook Section
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = if (isArabic) "حفظ المادة في سجل درجاتك المحفوظة:" else "Save Course to Local Gradebook:",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = subjectNameInput,
                            onValueChange = { subjectNameInput = it },
                            placeholder = {
                                Text(
                                    if (isArabic) "اسم المادة (مثال: برمجيات 1)" else "Subject Name (e.g. CS101)",
                                    fontSize = 13.sp
                                )
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                val name = subjectNameInput.ifBlank { if (isArabic) "مادة مجهولة" else "Subject" }
                                onSaveSubject(name)
                                isSaved = true
                            },
                            enabled = !isSaved,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSaved) EmeraldPass else SvuBluePrimary
                            )
                        ) {
                            Icon(
                                imageVector = if (isSaved) Icons.Default.CheckCircle else Icons.Default.BookmarkAdd,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isSaved) (if (isArabic) "تم الحفظ" else "Saved") else (if (isArabic) "حفظ" else "Save"),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BreakdownRow(
    label: String,
    rawScore: String,
    weightedScore: String,
    isHighlight: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Normal,
                    color = if (isHighlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = rawScore,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 10.5.sp
                )
            )
        }

        Text(
            text = weightedScore,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = if (isHighlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        )
    }
}
