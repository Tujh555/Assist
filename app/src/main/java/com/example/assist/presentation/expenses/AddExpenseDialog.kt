package com.example.assist.presentation.expenses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val expenseTypesDefault = listOf("Топливо", "Мойка", "ТО", "Парковка", "Штраф", "Налоги", "Страхование")

data class ExpenseInputState(
    val
)

@Composable
fun AddExpenseDialog(
    state: ExpenseInputState,
    onStateChange: (ExpenseInputState) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    expenseTypes: List<String> = expenseTypesDefault
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Добавление расхода",
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = state.amount,
                    onValueChange = { onStateChange(state.copy(amount = it.filter { c -> c.isDigit() })) },
                    label = { Text("Сумма") },
                    trailingIcon = {
                        if (state.amount.isNotEmpty()) {
                            IconButton(onClick = { onStateChange(state.copy(amount = "")) }) {
                                Icon(Icons.Default.Clear, contentDescription = "Очистить")
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Box {
                    OutlinedTextField(
                        value = state.type,
                        onValueChange = {},
                        label = { Text("Тип расхода") },
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier.clickable { dropdownExpanded = true }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        expenseTypes.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    onStateChange(state.copy(type = item))
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = state.comment,
                    onValueChange = { onStateChange(state.copy(comment = it)) },
                    label = { Text("Комментарий") },
                    trailingIcon = {
                        if (state.comment.isNotEmpty()) {
                            IconButton(onClick = { onStateChange(state.copy(comment = "")) }) {
                                Icon(Icons.Default.Clear, contentDescription = "Очистить")
                            }
                        }
                    },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Добавить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

@Preview
@Composable
fun ExpensesScreenWithDialogPreview() {
    val mockExpenses = mapOf("27.07.2025" to listOf(/*...*/))

    ExpensesScreen(
        expensesByDate = mockExpenses,
        onExpenseAdded = { println("Добавлено: $it") }
    )
}