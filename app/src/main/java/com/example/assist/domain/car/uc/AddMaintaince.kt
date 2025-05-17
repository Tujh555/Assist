package com.example.assist.domain.car.uc

import com.example.assist.domain.expense.Expense
import com.example.assist.domain.expense.ExpenseRepository
import com.example.assist.domain.expense.ExpenseTarget
import com.example.assist.domain.maintaince.MaintainceRepository
import com.example.assist.domain.maintaince.Part
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

class AddMaintaince @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val maintainceRepository: MaintainceRepository
) {
    suspend operator fun invoke(part: Part, price: Int) = coroutineScope {
        launch { maintainceRepository.replace(part) }
        launch {
            val expense = Expense(0, ExpenseTarget.CarPart(part), price, Instant.now(), "")
            expenseRepository.add(expense)
        }
    }
}