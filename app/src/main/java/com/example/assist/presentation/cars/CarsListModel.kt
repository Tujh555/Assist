package com.example.assist.presentation.cars

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.assist.domain.car.CarRepository
import com.example.assist.domain.car.SelectedCar
import com.example.assist.presentation.base.StateHolder
import com.example.assist.presentation.base.StateModel
import com.example.assist.presentation.base.io
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CarsListModel @Inject constructor(
    private val selectedCar: SelectedCar,
    private val repository: CarRepository
) : ViewModel(),
    StateModel<CarsListScreen.Action, CarsListScreen.State>,
    StateHolder<CarsListScreen.State> by StateHolder(CarsListScreen.State()) {

    init {
        observeCars()
        observeSelected()
    }

    override fun onAction(action: CarsListScreen.Action) {
        when (action) {
            is CarsListScreen.Action.Delete -> screenModelScope.io {
                repository.delete(action.id)
            }

            is CarsListScreen.Action.Select -> screenModelScope.io {
                repository.select(action.id)
            }
        }
    }

    private fun observeCars() {
        repository
            .observe()
            .onEach { cars -> update { it.copy(cars = cars) } }
            .flowOn(Dispatchers.IO)
            .launchIn(screenModelScope)
    }

    private fun observeSelected() {
        selectedCar
            .filterNotNull()
            .onEach { selected -> update { it.copy(selected = selected) } }
            .flowOn(Dispatchers.IO)
            .launchIn(screenModelScope)
    }

}