package com.example.tea.tea_note.presentation.myTea

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tea.tea_note.domain.model.Tea
import com.example.tea.tea_note.domain.use_case.TeaUseCases
import com.example.tea.tea_note.domain.util.OrderType
import com.example.tea.tea_note.domain.util.TeaOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeaViewModel @Inject constructor(
    private val teaUseCases: TeaUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TeaState())
    val state: State<TeaState> = _state

    private var recentlyDeletedTea: Tea? = null

    private var getTeasJob: Job? = null

    init {
        getTeas(TeaOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: TeaEvent) {
        when (event) {
            is TeaEvent.Order -> {
                if (state.value.teasOrder::class == event.teasOrder::class &&
                    state.value.teasOrder.orderType == event.teasOrder.orderType
                ) {
                    return
                }
                getTeas(event.teasOrder)
            }
            is TeaEvent.DeleteTea -> {
                viewModelScope.launch {
                    teaUseCases.deleteTea(event.recipe)
                    recentlyDeletedTea = event.recipe
                }
            }
            is TeaEvent.RestoreTea -> {
                viewModelScope.launch {
                    teaUseCases.addTea(recentlyDeletedTea ?: return@launch)
                    recentlyDeletedTea = null
                }
            }
            is TeaEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getTeas(teaOrder: TeaOrder) {
        getTeasJob?.cancel()
        getTeasJob = teaUseCases.getTeas(teaOrder)
            .onEach { tea ->
                _state.value = state.value.copy(
                    teas = tea,
                    teasOrder = teaOrder
                )
            }
            .launchIn(viewModelScope)
    }
}