package com.example.tea.tea_note.data.repository

import com.example.tea.tea_note.data.data_source.TeaDao
import com.example.tea.tea_note.domain.model.Tea
import com.example.tea.tea_note.domain.repository.TeaRepository
import kotlinx.coroutines.flow.Flow

class TeaRepositoryImpl(
    private val dao: TeaDao
) : TeaRepository {

    override fun getTeas(): Flow<List<Tea>> {
        return dao.getTeas()
    }

    override suspend fun getTeaById(id: Int): Tea? {
        return dao.getTeaById(id)
    }

    override suspend fun insertTea(note: Tea) {
        dao.insertTea(note)
    }

    override suspend fun deleteTea(note: Tea) {
        dao.deleteTea(note)
    }
}