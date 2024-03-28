package com.example.tea.tea_note.presentation.myTea.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tea.tea_note.domain.util.OrderType
import com.example.tea.tea_note.domain.util.TeaOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    teaOrder: TeaOrder = TeaOrder.Date(OrderType.Descending),
    onOrderChange: (TeaOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = teaOrder is TeaOrder.Title,
                onSelect = { onOrderChange(TeaOrder.Title(teaOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = teaOrder is TeaOrder.Date,
                onSelect = { onOrderChange(TeaOrder.Date(teaOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = teaOrder is TeaOrder.Color,
                onSelect = { onOrderChange(TeaOrder.Color(teaOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = teaOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(teaOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = teaOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(teaOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}