package com.example.tea.di

import android.app.Application
import androidx.room.Room
import com.example.tea.tea_note.data.data_source.TeaDatabase
import com.example.tea.tea_note.data.repository.TeaRepositoryImpl
import com.example.tea.tea_note.domain.repository.TeaRepository
import com.example.tea.tea_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): TeaDatabase {
        return Room.databaseBuilder(
            app,
            TeaDatabase::class.java,
            TeaDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: TeaDatabase): TeaRepository {
        return TeaRepositoryImpl(db.teaDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: TeaRepository): TeaUseCases {
        return TeaUseCases(
            getTeas = GetTeas(repository),
            deleteTea = DeleteTea(repository),
            addTea = AddTea(repository),
            getTea = GetTea(repository)
        )
    }
}