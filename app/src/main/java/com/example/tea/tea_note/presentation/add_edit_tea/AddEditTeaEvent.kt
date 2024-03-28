package com.example.tea.tea_note.presentation.add_edit_tea

import androidx.compose.ui.focus.FocusState

sealed class AddEditTeaEvent{
    data class EnteredTitle(val value: String): AddEditTeaEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditTeaEvent()
    data class EnteredContent(val value: String): AddEditTeaEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditTeaEvent()
    data class ChangeColor(val color: Int): AddEditTeaEvent()
    object SaveTea: AddEditTeaEvent()
}

