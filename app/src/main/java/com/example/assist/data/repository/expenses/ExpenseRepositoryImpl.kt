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
import com.example.assist.domain.car.Car
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ExpenseRepositoryImpl @AssistedInject constructor(
    @Assisted private val car: Car,
    private val dao: ExpenseDao,
) : ExpenseRepository {
    @AssistedFactory
    interface Factory : ExpenseRepository.Factory {
        override fun invoke(p1: Car): ExpenseRepositoryImpl
    }

    override fun observe() = dao.observe(car.id).map { list -> list.map(ExpenseEntity::toDomain) }

    override suspend fun add(expense: Expense) {
        val entity = expense.toDb(car.id)
        dao.insert(entity)
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }

    override suspend fun edit(id: Long, price: Int, comment: String) {
        dao.edit(id, price, comment)
    }
}