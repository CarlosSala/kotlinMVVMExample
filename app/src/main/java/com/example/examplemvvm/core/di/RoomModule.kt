package com.example.examplemvvm.core.di

import android.content.Context
import androidx.room.Room
import com.example.examplemvvm.data.database.QuoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val QUOTE_DATABASE_NAME = "quote_database"

    // database
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, QuoteDataBase::class.java, QUOTE_DATABASE_NAME).build()

    // dao
    @Singleton
    @Provides
    fun provideQuoteDao(db: QuoteDataBase) = db.getQuoteDao()
}