package com.example.tea.tea_note.domain.repository

import com.example.tea.tea_note.domain.model.Tea
import kotlinx.coroutines.flow.Flow

interface TeaRepository {

    fun getTeas(): Flow<List<Tea>>

    suspend fun getTeaById(id: Int): Tea?

    suspend fun insertTea(note: Tea)

    suspend fun deleteTea(note: Tea)
}