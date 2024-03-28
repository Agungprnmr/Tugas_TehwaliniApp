package com.example.tea.tea_note.domain.use_case

import com.example.tea.tea_note.domain.model.Tea
import com.example.tea.tea_note.domain.repository.TeaRepository
import com.example.tea.tea_note.domain.util.OrderType
import com.example.tea.tea_note.domain.util.TeaOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTeas(
    private val repository: TeaRepository
) {

    operator fun invoke(
        teasOrder: TeaOrder = TeaOrder.Date(OrderType.Descending)
    ): Flow<List<Tea>> {
        return repository.getTeas().map { recipess ->
            when(teasOrder.orderType) {
                is OrderType.Ascending -> {
                    when(teasOrder) {
                        is TeaOrder.Title -> recipess.sortedBy { it.teaTitle.lowercase() }
                        is TeaOrder.Date -> recipess.sortedBy { it.timestamp }
                        is TeaOrder.Color -> recipess.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(teasOrder) {
                        is TeaOrder.Title -> recipess.sortedByDescending { it.teaTitle.lowercase() }
                        is TeaOrder.Date -> recipess.sortedByDescending { it.timestamp }
                        is TeaOrder.Color -> recipess.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}