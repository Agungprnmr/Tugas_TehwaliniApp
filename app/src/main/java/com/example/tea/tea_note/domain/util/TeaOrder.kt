package com.example.tea.tea_note.domain.util

sealed class TeaOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): TeaOrder(orderType)
    class Date(orderType: OrderType): TeaOrder(orderType)
    class Color(orderType: OrderType): TeaOrder(orderType)

    fun copy(orderType: OrderType): TeaOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
