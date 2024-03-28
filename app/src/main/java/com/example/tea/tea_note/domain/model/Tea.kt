package com.example.tea.tea_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tea.ui.theme.*

@Entity
data class Tea(
    val teaTitle: String,
    val teaContent: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val teaColor = listOf(GreenPale, Green, GreenSea, Sand, SandLight)
    }
}

class InvalidTeaException(message: String): Exception(message)