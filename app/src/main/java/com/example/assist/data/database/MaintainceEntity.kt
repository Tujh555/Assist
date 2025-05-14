package com.example.assist.data.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.assist.domain.part.Part
import com.example.assist.domain.part.PartReplacement

class MaintainceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "car_id")
    val carId: Long, // Foreign key + unique index
    @ColumnInfo(name = "replacements")
    val replacements: List<PartReplacement>
)

