package com.ssajudn.expensetracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.data.local.entities.Savings
import com.ssajudn.expensetracker.data.local.migration.MIGRATION_1_2

@Database(
    entities = [Expense::class, Savings::class],
    version = 2,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun savingsDao(): SavingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getDatabase(context: Context): AppDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "expense_db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}