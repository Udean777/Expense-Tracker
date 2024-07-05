package com.ssajudn.expensetracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savings")
data class Saving(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val amount: Double,
    val date: String,
    val goal: String
)

@Entity(tableName = "savings_target")
data class SavingsTarget(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val targetAmount: Double,
    val targetDate: String
)