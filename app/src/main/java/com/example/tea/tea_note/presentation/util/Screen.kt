package com.example.tea.tea_note.presentation.util

sealed class Screen(val route: String) {
    object TeaScreen: Screen("tea_screen")
    object AddEditTeaScreen: Screen("add_edit_tea_screen")
}
