package com.example.assist.data.converter

import com.example.assist.domain.part.Part
import com.example.assist.domain.part.PartReplacement
import okio.Buffer

private const val separator = " "

fun List<PartReplacement>.serialize() = joinToString(separator) { replacement ->
    "${replacement.part.ordinal},${replacement.mileageReplacement}"
}

fun String.deserialize() = split(separator).map { replacementString ->
    val (partOrdinal, mileageReplacement) = replacementString
        .split(",")
        .map(String::toInt)

    PartReplacement(Part.entries[partOrdinal], mileageReplacement)
}