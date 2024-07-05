package com.ssajudn.expensetracker.di

import android.app.Application
import androidx.room.Room
import com.ssajudn.expensetracker.data.local.AppDB
import com.ssajudn.expensetracker.data.local.ExpenseDao
import com.ssajudn.expensetracker.data.local.SavingDao
import com.ssajudn.expensetracker.data.repository.SavingRepositoryImpl
import com.ssajudn.expensetracker.domain.repository.SavingRepository
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
    fun provideDB(
        application: Application
    ): AppDB {
        return Room.databaseBuilder(
            application,
            AppDB::class.java,
            "expense_db"
        )
            .addMigrations(AppDB.MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideExpenseDao(appDB: AppDB): ExpenseDao {
        return appDB.expenseDao()
    }

    @Provides
    @Singleton
    fun provideSavingDao(appDB: AppDB): SavingDao {
        return appDB.savingDao()
    }
}