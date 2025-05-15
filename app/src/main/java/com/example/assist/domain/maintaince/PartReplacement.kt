package com.example.assist.domain.maintaince

class PartReplacement(
    val part: Part,
    val mileageReplacement: Int,
) {
    constructor(pair: Pair<Part, Int>): this(pair.first, pair.second)
}