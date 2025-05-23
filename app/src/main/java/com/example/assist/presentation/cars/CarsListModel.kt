package com.example.assist.presentation.cars

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.assist.domain.car.Car
import com.example.assist.domain.car.CarRepository
import com.example.assist.domain.car.SelectedCar
import com.example.assist.presentation.base.StateHolder
import com.example.assist.presentation.base.StateModel
import com.example.assist.presentation.base.io
import com.example.assist.presentation.main.MainScreen
import com.example.assist.presentation.maintaince.MaintainceScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarsListModel @Inject constructor(
    private val selectedCar: SelectedCar,
    private val repository: CarRepository
) : ViewModel(),
    StateModel<CarsListScreen.Action, CarsListScreen.State>,
    StateHolder<CarsListScreen.State> by StateHolder(CarsListScreen.State()) {

    init {
        // TODO remove
        screenModelScope.launch {
            val fakeCar = Car(
                id = 0,
                brand = "Toyota",
                model = "Corolla",
                year = 2003,
                mileage = 17003
            )
            repository.put(fakeCar)
        }

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
                action.navigator.push(MainScreen())
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