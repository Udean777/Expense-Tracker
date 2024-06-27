package com.ssajudn.expensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssajudn.expensetracker.data.local.entities.Expense

@Database(
    entities = [Expense::class],
    version = 1,
    exportSchema = false
)
abstract class AppDB : RoomDatabase(){
    abstract fun expenseDao(): ExpenseDao
}