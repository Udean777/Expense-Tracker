package com.ssajudn.expensetracker.di

import com.ssajudn.expensetracker.data.repository.ExpenseRepositoryImpl
import com.ssajudn.expensetracker.data.repository.SavingRepositoryImpl
import com.ssajudn.expensetracker.domain.repository.ExpenseRepository
import com.ssajudn.expensetracker.domain.repository.SavingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExpenseRepository(
        expenseRepositoryImpl: ExpenseRepositoryImpl
    ): ExpenseRepository

    @Binds
    @Singleton
    abstract fun bindSavingRepository(
        savingRepositoryImpl: SavingRepositoryImpl
    ): SavingRepository
}