package com.example.assist.data.repository.maintance

import com.example.assist.data.database.MaintainanceEntity
import com.example.assist.data.database.MaintainceDao
import com.example.assist.data.database.resolve
import com.example.assist.data.database.toDomain
import com.example.assist.domain.car.SelectedCar
import com.example.assist.domain.maintaince.MaintainceRepository
import com.example.assist.domain.maintaince.Part
import com.example.assist.domain.maintaince.PartReplacement
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MaintainedRepositoryImpl @Inject constructor(
    private val selectedCar: SelectedCar,
    private val dao: MaintainceDao
) : MaintainceRepository {
    override fun observe() = selectedCar.filterNotNull().flatMapLatest { car ->
        dao.observe(car.id).filterNotNull().map(MaintainanceEntity::toDomain)
    }

    override suspend fun replace(part: Part) {
        val car = selectedCar.value ?: return
        val replacements = dao.resolve(car.id)?.replacements ?: return

        val map = LinkedHashMap<Part, Int>()
        replacements.forEach { replacement ->
            map[replacement.part] = replacement.mileageReplacement
        }
        map[part] = car.mileage

        val maintaince = MaintainanceEntity(
            carId = car.id,
            replacements = map.toList().map(::PartReplacement)
        )
        dao.insert(maintaince)
    }
}