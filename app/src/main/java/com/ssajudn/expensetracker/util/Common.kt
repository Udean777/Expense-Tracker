package com.ssajudn.expensetracker.util

object Common {
    val listProvider = listOf(
        "E-Wallet",
        "Salary",
        "Debit Card",
        "Visa Card",
        "Master Card",
        "Cash"
    )

    val listTypeTransaction = listOf("Income", "Expense")
}

fun getMonthName(month: Int): String {
    return when (month) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> ""
    }
}