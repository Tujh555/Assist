package com.example.assist.presentation.cars

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.assist.domain.car.Car

@Composable
fun CarsListScreenContent(state: CarsListScreen.State, onAction: (CarsListScreen.Action) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        val navigator = LocalNavigator.currentOrThrow
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state.listState,
            reverseLayout = false,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(state.cars, key = { it.id }) { item ->
                CarItem(
                    modifier = Modifier.fillMaxWidth().animateItem(),
                    car = item,
                    isSelected = item.id == state.selected?.id,
                    delete = { onAction(CarsListScreen.Action.Delete(item.id)) },
                    edit = {
                        // TODO navigator.push(EditScreen())
                    },
                    onClick = {
                        onAction(CarsListScreen.Action.Select(item.id, navigator))
                    },
                )
            }
        }


    }
}

private val border = BorderStroke(1.dp, Color.Black)

@Composable
private fun CarItem(
    modifier: Modifier,
    car: Car,
    isSelected: Boolean,
    delete: () -> Unit,
    edit: () -> Unit,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier.padding(16.dp).selectable(
            selected = isSelected,
            interactionSource = remember { MutableInteractionSource() },
            indication = remember { ripple() },
            enabled = true,
            onClick = onClick
        ),
        border = border,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = car.model)
            Text(text = car.brand)
            Text(text = car.year.toString())
        }
    }
}

