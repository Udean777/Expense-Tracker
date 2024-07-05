package com.ssajudn.expensetracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "savings")
data class Savings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0
)
