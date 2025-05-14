package com.example.assist.data.converter

import androidx.room.TypeConverter
import com.example.assist.domain.part.PartReplacement

class Converters {
    @TypeConverter
    fun toSerialized(replacements: List<PartReplacement>) = replacements.serialize()

    @TypeConverter
    fun fromSerialized(serialized: String) = serialized.deserialize()
}