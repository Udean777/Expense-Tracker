package com.ssajudn.expensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.data.local.entities.Saving

@Database(
    entities = [Expense::class, Saving::class],
    version = 2,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun savingDao(): SavingDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS savings (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, amount REAL NOT NULL, date TEXT NOT NULL, goal TEXT NOT NULL)")
            }
        }
    }
}