package com.example.assist.presentation.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assist.domain.expense.Expense
import com.example.assist.domain.expense.ExpenseRepository
import com.example.assist.domain.expense.ExpenseTarget
import com.example.assist.presentation.base.StateHolder
import com.example.assist.presentation.base.StateModel
import com.example.assist.presentation.base.io
import com.example.assist.presentation.models.ExpenseItem
import com.example.assist.presentation.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class ExpenseModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel(),
    StateModel<ExpenseScreen.Action, ExpenseScreen.State>,
    StateHolder<ExpenseScreen.State> by StateHolder(ExpenseScreen.State()) {

    init {
        observeList()
    }

    override fun onAction(action: ExpenseScreen.Action) {
        when (action) {
            is ExpenseScreen.Action.Create -> add(action.inputState)
        }
    }

    private fun observeList() {
        repository
            .observe()
            .onEach { expenses ->
                val items = expenses.map(Expense::toUi).groupBy(ExpenseItem::date)
                update { it.copy(expenses = items) }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun add(inputState: ExpenseInputState) {
        val expense = Expense(
            id = 0,
            target = ExpenseTarget.Custom(inputState.type),
            price = inputState.sum.toInt(),
            date = Instant.now(),
            comment = inputState.comment
        )

        viewModelScope.io {
            repository.add(expense)
        }
    }
}