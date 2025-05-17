package com.example.assist.data.converter

import androidx.room.TypeConverter
import com.example.assist.domain.maintaince.PartReplacement
import java.time.Instant

class Converters {
    @TypeConverter
    fun toSerialized(replacements: List<PartReplacement>) = replacements.serialize()

    @TypeConverter
    fun fromSerialized(serialized: String) = serialized.deserialize()

    @TypeConverter
    fun fromInstant(time: Instant) = time.toEpochMilli()

    @TypeConverter
    fun toInstant(milli: Long) = Instant.ofEpochMilli(milli)
}