package com.example.assist.domain.expense

import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun observe(): Flow<List<Expense>>

    suspend fun add(expense: Expense)

    suspend fun delete(id: Long)
}