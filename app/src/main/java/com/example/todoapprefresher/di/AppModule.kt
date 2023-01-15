package com.example.todoapprefresher.di

import android.app.Application
import androidx.room.Room
import com.example.todoapprefresher.data.TodoDatabase
import com.example.todoapprefresher.data.TodoRepository
import com.example.todoapprefresher.data.TodoRepositoryImpl
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
    fun provideDatabase(app : Application) : TodoDatabase = Room.databaseBuilder(app, TodoDatabase::class.java, "todoDatabase").build()

    @Provides
    @Singleton
    fun provideRepository(database : TodoDatabase) : TodoRepository = TodoRepositoryImpl(dao = database.todoDao)

}