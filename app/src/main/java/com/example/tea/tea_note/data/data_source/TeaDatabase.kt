package com.example.tea.tea_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tea.tea_note.domain.model.Tea

@Database(
    entities = [Tea::class],
    version = 3
)
abstract class TeaDatabase: RoomDatabase() {

    abstract val teaDao: TeaDao

    companion object {
        const val DATABASE_NAME = "tea_db"
    }
}