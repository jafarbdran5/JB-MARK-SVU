package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProgramCategory
import com.example.data.model.SvuProgram
import com.example.data.model.SvuPresets
import com.example.ui.theme.SvuBluePrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProgramSelectorCard(
    selectedProgram: SvuProgram,
    currentHwWeight: Double,
    isArabic: Boolean,
    onProgramSelected: (SvuProgram) -> Unit,
    onHwWeightChanged: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategoryFilter by remember { mutableStateOf<ProgramCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    var isCustomWeightSelected by remember(currentHwWeight) {
        mutableStateOf(currentHwWeight != 0.20 && currentHwWeight != 0.30)
    }
    var customHwInput by remember(currentHwWeight) {
        mutableStateOf((currentHwWeight * 100).toInt().toString())
    }

    val filteredPrograms = remember(selectedCategoryFilter, searchQuery) {
        SvuPresets.allPrograms.filter { program ->
            val matchesCategory = selectedCategoryFilter == null || program.category == selectedCategoryFilter
            val matchesQuery = searchQuery.isBlank() ||
                    program.nameAr.contains(searchQuery, ignoreCase = true) ||
                    program.nameEn.contains(searchQuery, ignoreCase = true) ||
                    program.code.contains(searchQuery, ignoreCase = true) ||
                    program.descriptionAr.contains(searchQuery, ignoreCase = true) ||
                    program.descriptionEn.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

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
            // Title Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isArabic) "اختيار البرامج والكليات (SVU Presets)" else "Select Faculty / Program",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Search Bar Feature
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = if (isArabic) "بحث عن كلية أو برنامج (حقوق، BAIT، MBA...)" else "Search faculty or code (BL, BAIT...)",
                        fontSize = 12.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SvuBluePrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Category Filter Chips
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FilterChip(
                    selected = selectedCategoryFilter == null,
                    onClick = { selectedCategoryFilter = null },
                    label = { Text(if (isArabic) "الكل" else "All", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                ProgramCategory.values().forEach { category ->
                    FilterChip(
                        selected = selectedCategoryFilter == category,
                        onClick = {
                            selectedCategoryFilter = if (selectedCategoryFilter == category) null else category
                        },
                        label = {
                            Text(if (isArabic) category.labelAr else category.labelEn, fontSize = 12.sp)
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Selected Program Dropdown Box
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { dropdownExpanded = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isArabic) selectedProgram.nameAr else selectedProgram.nameEn,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = if (isArabic) selectedProgram.descriptionAr else selectedProgram.descriptionEn,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.5.sp
                                )
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SvuBluePrimary
                        ) {
                            Text(
                                text = selectedProgram.code,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    if (filteredPrograms.isEmpty()) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    if (isArabic) "لا يوجد نتائج مطابقة للبحث" else "No matching programs found",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            onClick = { dropdownExpanded = false }
                        )
                    } else {
                        filteredPrograms.forEach { program ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text = if (isArabic) program.nameAr else program.nameEn,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = if (isArabic) program.descriptionAr else program.descriptionEn,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                },
                                onClick = {
                                    onProgramSelected(program)
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Weight Toggle section with 3 options (20/80, 30/70, Custom)
            if (selectedProgram.hasWeightToggle) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 8.dp)
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
                                text = if (isArabic) "نسبة توزيع الوظيفة والامتحان:" else "Weight Distribution:",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // 3 Options: 20/80, 30/70, Custom
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // 1. Option 20%/80%
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable {
                                    isCustomWeightSelected = false
                                    onHwWeightChanged(0.20)
                                }
                            ) {
                                RadioButton(
                                    selected = !isCustomWeightSelected && currentHwWeight == 0.20,
                                    onClick = {
                                        isCustomWeightSelected = false
                                        onHwWeightChanged(0.20)
                                    }
                                )
                                Text(
                                    text = "20%/80%",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.5.sp
                                    )
                                )
                            }

                            // 2. Option 30%/70%
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable {
                                    isCustomWeightSelected = false
                                    onHwWeightChanged(0.30)
                                }
                            ) {
                                RadioButton(
                                    selected = !isCustomWeightSelected && currentHwWeight == 0.30,
                                    onClick = {
                                        isCustomWeightSelected = false
                                        onHwWeightChanged(0.30)
                                    }
                                )
                                Text(
                                    text = "30%/70%",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.5.sp
                                    )
                                )
                            }

                            // 3. Custom Option
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable {
                                    isCustomWeightSelected = true
                                    val customWeight = customHwInput.toDoubleOrNull() ?: 25.0
                                    onHwWeightChanged(customWeight / 100.0)
                                }
                            ) {
                                RadioButton(
                                    selected = isCustomWeightSelected,
                                    onClick = {
                                        isCustomWeightSelected = true
                                        val customWeight = customHwInput.toDoubleOrNull() ?: 25.0
                                        onHwWeightChanged(customWeight / 100.0)
                                    }
                                )
                                Text(
                                    text = if (isArabic) "مخصص %" else "Custom %",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.5.sp
                                    )
                                )
                            }
                        }

                        // Input field for Custom Weight if selected
                        if (isCustomWeightSelected) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = customHwInput,
                                    onValueChange = { input ->
                                        val clean = input.filter { it.isDigit() || it == '.' }
                                        customHwInput = clean
                                        val valNum = clean.toDoubleOrNull()
                                        if (valNum != null && valNum in 0.0..100.0) {
                                            onHwWeightChanged(valNum / 100.0)
                                        }
                                    },
                                    label = {
                                        Text(
                                            if (isArabic) "نسبة الوظيفة (%)" else "HW Weight (%)",
                                            fontSize = 11.sp
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                val hwPct = customHwInput.toDoubleOrNull() ?: (currentHwWeight * 100)
                                val examPct = (100.0 - hwPct).coerceIn(0.0, 100.0)

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.padding(top = 6.dp)
                                ) {
                                    Text(
                                        text = if (isArabic) "وظيفة: ${hwPct.toInt()}% | امتحان: ${examPct.toInt()}%"
                                        else "HW: ${hwPct.toInt()}% | Exam: ${examPct.toInt()}%",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

