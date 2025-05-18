package com.example.assist.data.repository.expenses

import com.example.assist.data.database.dao.ExpenseDao
import com.example.assist.data.database.ExpenseEntity
import com.example.assist.data.database.toDb
import com.example.assist.domain.car.SelectedCar
import com.example.assist.domain.expense.Expense
import com.example.assist.domain.expense.ExpenseRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.assist.data.database.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ExpenseRepositoryImpl @Inject constructor(
    private val dao: ExpenseDao,
    private val selectedCar: SelectedCar
) : ExpenseRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observe() = selectedCar.flatMapLatest { car ->
        dao.observe(car?.id ?: 0).map { list -> list.map(ExpenseEntity::toDomain) }
    }

    override suspend fun add(expense: Expense) {
        //val car = selectedCar.value ?: return
        val entity = expense.toDb(
            0// FIXME car.id
        )
        dao.insert(entity)
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }

    override suspend fun edit(id: Long, price: Int, comment: String) {
        dao.edit(id, price, comment)
    }
}