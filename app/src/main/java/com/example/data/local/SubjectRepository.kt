package com.example.data.local

import kotlinx.coroutines.flow.Flow

class SubjectRepository(private val dao: SavedSubjectDao) {
    val allSavedSubjects: Flow<List<SavedSubjectEntity>> = dao.getAllSavedSubjects()

    suspend fun saveSubject(subject: SavedSubjectEntity): Long {
        return dao.insertSubject(subject)
    }

    suspend fun deleteSubject(id: Long) {
        dao.deleteSubjectById(id)
    }

    suspend fun clearAll() {
        dao.clearAllSubjects()
    }
}
