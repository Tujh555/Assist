package com.example.assist.data.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.assist.domain.expense.Expense
import com.example.assist.domain.expense.ExpenseTarget
import com.example.assist.domain.maintaince.Part

class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "part")
    val part: Part?,
    @ColumnInfo(name = "part")
    val customTarget: String?,
    @ColumnInfo(name = "part")
    val price: Int
)

fun ExpenseEntity.toDomain(): Expense {
    val target = if (part != null) {
        ExpenseTarget.CarPart(part)
    } else {
        ExpenseTarget.Custom(customTarget.orEmpty())
    }

    return Expense(
        id = id,
        target = target,
        price = price
    )
}

fun Expense.toDb(): ExpenseEntity {
    var part: Part? = null
    var customTarget: String? = null

    when (target) {
        is ExpenseTarget.CarPart -> part = target.part
        is ExpenseTarget.Custom -> customTarget = target.value
    }

    return ExpenseEntity(
        id = id,
        part = part,
        customTarget = customTarget,
        price = price
    )
}