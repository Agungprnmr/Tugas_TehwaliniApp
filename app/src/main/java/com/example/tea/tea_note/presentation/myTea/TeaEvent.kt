package com.example.tea.tea_note.presentation.myTea

import com.example.tea.tea_note.domain.model.Tea
import com.example.tea.tea_note.domain.util.TeaOrder

sealed class TeaEvent {
    data class Order(val teasOrder: TeaOrder): TeaEvent()
    data class DeleteTea(val recipe: Tea): TeaEvent()
    object RestoreTea: TeaEvent()
    object ToggleOrderSection: TeaEvent()
}
