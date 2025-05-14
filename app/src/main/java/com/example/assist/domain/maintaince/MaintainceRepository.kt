package com.example.assist.domain.maintaince

import kotlinx.coroutines.flow.Flow

interface MaintainceRepository {
    fun observe(): Flow<List<PartReplacement>>

    suspend fun replace(part: Part)
}