package com.example.tea.tea_note.data.data_source

import androidx.room.*
import com.example.tea.tea_note.domain.model.Tea
import kotlinx.coroutines.flow.Flow

@Dao
interface TeaDao {

    @Query("SELECT * FROM tea")
    fun getTeas(): Flow<List<Tea>>

    @Query("SELECT * FROM tea WHERE id = :id")
    suspend fun getTeaById(id: Int): Tea?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTea(note: Tea)

    @Delete
    suspend fun deleteTea(note: Tea)
}