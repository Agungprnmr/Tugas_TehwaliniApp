package com.example.tea.tea_note.presentation.myTea

import com.example.tea.tea_note.domain.model.Tea
import com.example.tea.tea_note.domain.util.OrderType
import com.example.tea.tea_note.domain.util.TeaOrder

data class TeaState(
    val teas: List<Tea> = emptyList(),
    val teasOrder: TeaOrder = TeaOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
