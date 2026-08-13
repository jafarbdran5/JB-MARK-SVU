package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_subjects")
data class SavedSubjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectName: String,
    val programCode: String,
    val programNameAr: String,
    val hwScore: Double,
    val mcqScore: Double,
    val essayScore: Double,
    val examTotal: Double,
    val finalGrade: Double,
    val displayedGrade: Double,
    val statusName: String, // PASS, CONDITIONAL_FAIL, TOTAL_FAIL
    val hwWeight: Double,
    val timestamp: Long = System.currentTimeMillis()
)
