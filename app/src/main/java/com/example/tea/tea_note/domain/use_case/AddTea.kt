package com.example.tea.tea_note.domain.use_case

import com.example.tea.tea_note.domain.model.InvalidTeaException
import com.example.tea.tea_note.domain.model.Tea
import com.example.tea.tea_note.domain.repository.TeaRepository

class AddTea(
    private val repository: TeaRepository
) {

    @Throws(InvalidTeaException::class)
    suspend operator fun invoke(tea: Tea) {
        if(tea.teaTitle.isBlank()) {
            throw InvalidTeaException("The title of the tea can't be empty.")
        }
        if(tea.teaContent.isBlank()) {
            throw InvalidTeaException("The content of the tea can't be empty.")
        }
        repository.insertTea(tea)
    }
}