package com.example.assist.domain.expense

import com.example.assist.domain.maintaince.Part

class Expense(
    val id: Long,
    val target: ExpenseTarget,
    val price: Int,
)

sealed interface ExpenseTarget {
    @JvmInline
    value class CarPart(val part: Part) : ExpenseTarget

    @JvmInline
    value class Custom(val value: String) : ExpenseTarget
}