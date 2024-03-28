package com.example.tea.tea_note.presentation.add_edit_tea

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tea.tea_note.domain.model.InvalidTeaException
import com.example.tea.tea_note.domain.model.Tea
import com.example.tea.tea_note.domain.use_case.TeaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTeaViewModel @Inject constructor(
    private val teaUseCases: TeaUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _teaTitle = mutableStateOf(TeaTextFieldState(
        hint = "Tea Title..."
    ))
    val teaTitle: State<TeaTextFieldState> = _teaTitle

    private val _teaContent = mutableStateOf(TeaTextFieldState(
        hint = "Tea detail description.."
    ))
    val teaContent: State<TeaTextFieldState> = _teaContent

    private val _teaColor = mutableStateOf(Tea.teaColor.random().toArgb())
    val teaColor: State<Int> = _teaColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTeaId: Int? = null

    init {
        savedStateHandle.get<Int>("teaId")?.let { teaId ->
            if(teaId != -1) {
                viewModelScope.launch {
                    teaUseCases.getTea(teaId)?.also { tea ->
                        currentTeaId = tea.id
                        _teaTitle.value = teaTitle.value.copy(
                            text = tea.teaTitle,
                            isHintVisible = false
                        )
                        _teaContent.value = _teaContent.value.copy(
                            text = tea.teaContent,
                            isHintVisible = false
                        )
                        _teaColor.value = tea.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTeaEvent) {
        when(event) {
            is AddEditTeaEvent.EnteredTitle -> {
                _teaTitle.value = teaTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditTeaEvent.ChangeTitleFocus -> {
                _teaTitle.value = teaTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            teaTitle.value.text.isBlank()
                )
            }
            is AddEditTeaEvent.EnteredContent -> {
                _teaContent.value = _teaContent.value.copy(
                    text = event.value
                )
            }
            is AddEditTeaEvent.ChangeContentFocus -> {
                _teaContent.value = _teaContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _teaContent.value.text.isBlank()
                )
            }
            is AddEditTeaEvent.ChangeColor -> {
                _teaColor.value = event.color
            }
            is AddEditTeaEvent.SaveTea -> {
                viewModelScope.launch {
                    try {
                        teaUseCases.addTea(
                            Tea(
                                teaTitle = teaTitle.value.text,
                                teaContent = teaContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = teaColor.value,
                                id = currentTeaId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTea)
                    } catch(e: InvalidTeaException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save tea"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveTea: UiEvent()
    }
}