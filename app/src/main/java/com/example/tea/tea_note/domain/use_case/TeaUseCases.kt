package com.example.tea.tea_note.domain.use_case

data class TeaUseCases(
    val getTeas: GetTeas,
    val deleteTea: DeleteTea,
    val addTea: AddTea,
    val getTea: GetTea
)
