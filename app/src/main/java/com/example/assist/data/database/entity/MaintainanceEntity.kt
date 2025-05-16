package com.example.assist.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.assist.data.database.CarEntity
import com.example.assist.domain.maintaince.PartReplacement

@Entity(
    tableName = "maintainance",
    foreignKeys = [
        ForeignKey(
            entity = CarEntity::class,
            parentColumns = ["id"],
            childColumns = ["car_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["car_id"])]
)

class MaintainanceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "car_id")
    val carId: Long,
    @ColumnInfo(name = "replacements")
    val replacements: List<PartReplacement>
)

fun MaintainanceEntity.toDomain() = replacements

fun List<PartReplacement>.toDb(carId: Long) = MaintainanceEntity(
    carId = carId,
    replacements = this
)

