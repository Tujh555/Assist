package com.example.assist.presentation.maintaince

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.assist.domain.maintaince.Part
import com.example.assist.presentation.expenses.numberKeyboardOptions
import com.example.assist.presentation.models.Replacement

@Composable
fun MaintenanceScreenContent(
    state: MaintainceScreen.State,
    onAction: (MaintainceScreen.Action) -> Unit
) {
    Box {
        var updatingPart by remember { mutableStateOf<Part?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .systemBarsPadding()
        ) {
            Text(
                text = "Экран ТО",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(16.dp))

            // Ввод пробега + кнопка сохранения
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.mileage,
                    onValueChange = { onAction(MaintainceScreen.Action.Mileage(it.filter { c -> c.isDigit() })) },
                    label = { Text("Пробег") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(8.dp))

                IconButton(
                    onClick = { onAction(MaintainceScreen.Action.UpdateMileage) },
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                }
            }

            Spacer(Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                state = state.listState
            ) {
                items(state.replacements, key = { it.part }) { item ->
                    PartItem(item) { updatingPart = item.part }
                }
            }
        }

        AnimatedVisibility(
            visible = updatingPart != null,
            enter = fadeIn(tween(200)) + scaleIn(tween(200)),
            exit = fadeOut(tween(200)) + scaleOut(tween(200))
        ) {
            var partPrice by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { updatingPart = null },
                title = {
                    Text(
                        text = "Редактирование расхода",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    OutlinedTextField(
                        value = partPrice,
                        onValueChange = {
                            partPrice = it.filter { c -> c.isDigit() }
                        },
                        label = { Text("Стоимость") },
                        trailingIcon = {
                            if (partPrice.isNotEmpty()) {
                                IconButton(onClick = { partPrice = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = null)
                                }
                            }
                        },
                        keyboardOptions = numberKeyboardOptions,
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            updatingPart?.let {
                                onAction(MaintainceScreen.Action.UpdatePart(it, partPrice))
                            }
                            updatingPart = null
                        }
                    ) {
                        Text("Ок")
                    }
                },
            )
        }
    }
}


@Composable
fun PartItem(
    replacement: Replacement,
    onUpdateClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = replacement.part.name, color = Color(0xFF4CAF50))

                when (replacement.remaining) {
                    -1 -> Text(text = "Нет данных о замене")
                    0 -> Text(
                        text = "Требуется замена",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                    else -> Text(text = "Оставшийся пробег: ${replacement.remaining} км")
                }
            }

            Button(onClick = onUpdateClick) {
                Text("Обновить")
            }
        }
    }
}