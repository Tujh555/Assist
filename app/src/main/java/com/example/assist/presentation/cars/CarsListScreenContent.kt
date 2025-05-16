package com.example.assist.presentation.cars

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
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
            .background(color = MaterialTheme.colorScheme.background)
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
                    modifier = Modifier.fillMaxWidth(),
                    car = item,
                    isSelected = item.id == state.selected?.id,
                    delete = { onAction(CarsListScreen.Action.Delete(item.id)) },
                    edit = {
                        // TODO navigator.push(EditScreen())
                    },
                    onClick = {
                        onAction(CarsListScreen.Action.Select(item.id))
                    },
                )
            }
        }
    }
}

private val border = BorderStroke(1.dp, Color.Black),

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
        modifier = modifier.padding(16.dp),
        border = border,
        onClick = onClick
    ) {

    }
}