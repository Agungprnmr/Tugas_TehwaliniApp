package com.example.tea.tea_note.domain.use_case

import com.example.tea.tea_note.domain.model.Tea
import com.example.tea.tea_note.domain.repository.TeaRepository

class GetTea(
    private val repository: TeaRepository
) {

    suspend operator fun invoke(id: Int): Tea? {
        return repository.getTeaById(id)
    }
}