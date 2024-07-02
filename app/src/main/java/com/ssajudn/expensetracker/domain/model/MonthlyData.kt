package com.ssajudn.expensetracker.domain.model

data class MonthlyData(
    val incomeData: List<Float>,
    val expenseData: List<Float>,
    val labels: List<String>
)
