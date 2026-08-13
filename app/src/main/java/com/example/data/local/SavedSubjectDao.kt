package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedSubjectDao {
    @Query("SELECT * FROM saved_subjects ORDER BY timestamp DESC")
    fun getAllSavedSubjects(): Flow<List<SavedSubjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SavedSubjectEntity): Long

    @Query("DELETE FROM saved_subjects WHERE id = :id")
    suspend fun deleteSubjectById(id: Long)

    @Query("DELETE FROM saved_subjects")
    suspend fun clearAllSubjects()
}
