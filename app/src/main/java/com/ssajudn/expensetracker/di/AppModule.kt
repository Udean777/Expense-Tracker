package com.ssajudn.expensetracker.di

import android.app.Application
import androidx.room.Room
import com.ssajudn.expensetracker.data.local.AppDB
import com.ssajudn.expensetracker.data.local.ChatDao
import com.ssajudn.expensetracker.data.local.ExpenseDao
import com.ssajudn.expensetracker.data.local.SavingsDao
import com.ssajudn.expensetracker.data.local.migration.MIGRATION_1_2
import com.ssajudn.expensetracker.data.repository.ChatRepositoryImpl
import com.ssajudn.expensetracker.data.repository.SavingsRepositoryImpl
import com.ssajudn.expensetracker.domain.repository.ChatRepository
import com.ssajudn.expensetracker.domain.repository.SavingsRepository
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
    fun provideDB(application: Application): AppDB {
        return AppDB.getDatabase(application)
    }

    @Provides
    @Singleton
    fun provideExpenseDao(appDB: AppDB): ExpenseDao {
        return appDB.expenseDao()
    }

    @Provides
    @Singleton
    fun provideSavingsDao(appDB: AppDB): SavingsDao {
        return appDB.savingsDao()
    }

    @Provides
    @Singleton
    fun provideSavingsRepository(savingsDao: SavingsDao): SavingsRepository {
        return SavingsRepositoryImpl(savingsDao)
    }

    @Provides
    @Singleton
    fun provideChatDao(appDB: AppDB): ChatDao {
        return appDB.chatDao()
    }

    @Provides
    @Singleton
    fun provideChatRepository(chatDao: ChatDao): ChatRepository {
        return ChatRepositoryImpl(chatDao)
    }
}