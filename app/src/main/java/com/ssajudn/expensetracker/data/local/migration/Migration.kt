package com.ssajudn.expensetracker.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Buat tabel baru dengan skema yang benar
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS savings_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                targetAmount REAL NOT NULL,
                currentAmount REAL NOT NULL DEFAULT 0.0
            )
        """)

        // Pindahkan data dari tabel lama ke tabel baru
        database.execSQL("""
            INSERT INTO savings_new (id, title, targetAmount, currentAmount)
            SELECT id, title, amount, 0.0 FROM savings
        """)

        // Hapus tabel lama
        database.execSQL("DROP TABLE savings")

        // Rename tabel baru menjadi savings
        database.execSQL("ALTER TABLE savings_new RENAME TO savings")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `ChatSessionEntity` (
                `id` TEXT NOT NULL, 
                `name` TEXT NOT NULL, 
                `lastUpdated` INTEGER NOT NULL, 
                `lastMessage` TEXT NOT NULL, 
                PRIMARY KEY(`id`)
            )
        """.trimIndent())

        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `ChatEntity` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                `sessionId` TEXT NOT NULL, 
                `prompt` TEXT NOT NULL, 
                `isFromUser` INTEGER NOT NULL, 
                `timestamp` INTEGER NOT NULL,
                FOREIGN KEY(`sessionId`) REFERENCES `ChatSessionEntity`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """.trimIndent())

        database.execSQL("CREATE INDEX IF NOT EXISTS `index_ChatEntity_sessionId` ON `ChatEntity` (`sessionId`)")
    }
}