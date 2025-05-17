package com.example.assist.presentation.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview



@Composable
fun ExpensesScreen(
    expensesByDate: Map<String, List<ExpenseItem>>,
    onAddExpenseClick: () -> Unit = {},
    onExpenseClick: (ExpenseItem) -> Unit = {},
    selectedPeriod: String = "Все",
    onPeriodChange: (String) -> Unit = {}
) {
    val periodOptions = listOf("Все", "Последние 30 дней", "Последние 6 месяцев")
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomNavBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExpenseClick) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Расходы",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                )
                Box {
                    TextButton(onClick = { expanded = true }) {
                        Text(text = selectedPeriod)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        periodOptions.forEach { period ->
                            DropdownMenuItem(
                                text = { Text(period) },
                                onClick = {
                                    expanded = false
                                    onPeriodChange(period)
                                }
                            )
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                expensesByDate.forEach { (date, items) ->
                    item {
                        DateHeader(date = date)
                    }
                    items(items) { expense ->
                        ExpenseItemCard(expense = expense, onClick = { onExpenseClick(expense) })
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseItemCard(expense: ExpenseItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Сумма: ${expense.amount}",
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
                Text(text = expense.type)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = expense.time)
                Text(text = expense.comment)
            }
        }
    }
}

@Composable
fun DateHeader(date: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Divider(thickness = 2.dp, color = Color.LightGray)
        Text(
            text = date,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun BottomNavBar(modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = Color(0xFF1B4AD0),
        modifier = modifier
    ) {
        NavigationBarItem(selected = true, onClick = {}, icon = {
            Icon(Icons.Default.Home, contentDescription = null, tint = Color.White)
        })
        NavigationBarItem(selected = false, onClick = {}, icon = {
            Icon(Icons.Default.Home, contentDescription = null, tint = Color.White)
        })
        NavigationBarItem(selected = false, onClick = {}, icon = {
            Icon(Icons.Default.Home, contentDescription = null, tint = Color.White)
        })
        NavigationBarItem(selected = false, onClick = {}, icon = {
            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
        })
    }
}

data class ExpenseItem(
    val amount: String,
    val type: String,
    val time: String,
    val comment: String
)

@Preview(showBackground = true)
@Composable
fun ExpensesScreenPreview() {
    var selectedPeriod by remember { mutableStateOf("Все") }

    val mockData = mapOf(
        "27.07.2025" to listOf(
            ExpenseItem("15000 ₸", "Топливо", "10:30", "Заправка АЗС")
        ),
        "26.07.2025" to listOf(
            ExpenseItem("8000 ₸", "Мойка", "14:10", "Автомойка")
        )
    )

    ExpensesScreen(
        expensesByDate = mockData,
        onAddExpenseClick = { /* навигация на экран добавления */ },
        onExpenseClick = { expense -> println("Клик по: $expense") },
        selectedPeriod = selectedPeriod,
        onPeriodChange = { selectedPeriod = it }
    )
}