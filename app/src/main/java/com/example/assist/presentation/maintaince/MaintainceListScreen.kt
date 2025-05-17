package com.example.assist.presentation.maintaince

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
import com.example.assist.data.repository.car.CurrentCar
import com.example.assist.domain.maintaince.PartReplacement

@Composable
fun MaintenanceScreen(
    mileageInput: String,
    onMileageInputChange: (String) -> Unit,
    onSaveMileageClick: () -> Unit,
    parts: List<PartReplacement>,
    onResetPart: (CurrentCar) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Заголовок
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
                value = mileageInput,
                onValueChange = { onMileageInputChange(it.filter { c -> c.isDigit() }) },
                label = { Text("Пробег") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            IconButton(
                onClick = onSaveMileageClick,
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(Icons.Default.Check, contentDescription = "Сохранить", tint = Color.White)
            }
        }

        Spacer(Modifier.height(24.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//            items(parts) { part ->
//                PartItem(
//                    part = part,
//                    onUpdateClick = { onResetPart(part) }
//                )
//            }
        }
    }
}


//@Composable
//fun PartItem(
//    part: CarPartRemaining,
//    onUpdateClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(8.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text(text = part.name, color = Color(0xFF4CAF50)) // Зелёный текст
//                Text(text = "Оставшийся пробег: ${part.remainingMileage} км")
//            }
//
//            Button(onClick = onUpdateClick) {
//                Text("Обновить")
//            }
//        }
//    }
//}

@Preview
@Composable
fun MaintenanceScreenPreview() {
//    val parts = listOf(
//        CarPartRemaining(1, "Масло", 3200),
//        CarPartRemaining(2, "Фильтр", 1200),
//        CarPartRemaining(3, "Тормозные колодки", 8000)
//    )
//
//    var mileage by remember { mutableStateOf("125000") }
//
//    MaintenanceScreen(
//        mileageInput = mileage,
//        onMileageInputChange = { mileage = it },
//        onSaveMileageClick = { println("Пробег сохранён: $mileage") },
//        parts = parts,
//        onResetPart = { part -> println("Сброс пробега для: ${part.name}") }
//    )
}
