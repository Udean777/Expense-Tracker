package com.ssajudn.expensetracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ssajudn.expensetracker.data.local.entities.ChatEntity
import com.ssajudn.expensetracker.data.local.entities.ChatSessionEntity
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.data.local.entities.Savings
import com.ssajudn.expensetracker.data.local.migration.MIGRATION_1_2
import com.ssajudn.expensetracker.data.local.migration.MIGRATION_2_3

@Database(
    entities = [Expense::class, Savings::class, ChatSessionEntity::class, ChatEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun savingsDao(): SavingsDao
    abstract fun chatDao(): ChatDao

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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}