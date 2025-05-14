package com.example.assist.domain.car.repository

import com.example.assist.domain.car.Car
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    fun observe(): Flow<List<Car>>

    suspend fun add(car: Car)

    suspend fun select(id: Long)

    suspend fun delete()
}