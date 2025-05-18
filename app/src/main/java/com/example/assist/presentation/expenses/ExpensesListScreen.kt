package com.example.assist.presentation.expenses

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.assist.presentation.models.ExpenseItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesScreenContent(state: ExpenseScreen.State, onAction: (ExpenseScreen.Action) -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Расходы",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                state = state.listState
            ) {
                state.expenses.forEach { (date, expenses) ->
                    stickyHeader(key = date, contentType = "header") {
                        DateHeader(date = date)
                    }

                    items(expenses, key = { it.id }, contentType = { "expense" }) { item ->
                        ExpenseItemCard(
                            modifier = Modifier.animateItem(),
                            expense = item,
                            onClick = { onAction(ExpenseScreen.Action.Edit(item)) }
                        )
                    }
                }
            }
        }

        var dialogVisible by remember { mutableStateOf(false) }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 24.dp),
            onClick = { dialogVisible = true }
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
        }

        AddExpenseDialog(
            onDismiss = { dialogVisible = false },
            onConfirm = {
                onAction(ExpenseScreen.Action.Create(it))
                dialogVisible = false
            },
            visible = dialogVisible
        )

        EditExpenseDialog(
            onDismiss = { onAction(ExpenseScreen.Action.Edit(null)) },
            onConfirm = { onAction(ExpenseScreen.Action.FinishEdit(it)) },
            editableItem = state.editableExpense
        )
    }
}

@Composable
fun ExpenseItemCard(
    modifier: Modifier,
    expense: ExpenseItem,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Сумма: ${expense.price}",
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = expense.target,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = expense.date,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = expense.comment,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DateHeader(date: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.4f))
        Text(
            text = date,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}