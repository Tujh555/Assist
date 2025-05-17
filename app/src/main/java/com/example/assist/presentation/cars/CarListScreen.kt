package com.example.assist.presentation.cars

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
fun CarListScreen(
    cars: List<String>, // Список машин, можешь заменить на модель `Car`
    onAddCarClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFEAF1FF), Color(0xFFD8E2FF))
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar()
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(cars) { car ->
                    CarCard(carName = car)
                }
                item {
                    AddCarCard(onClick = onAddCarClicked)
                }
            }
        }
        BottomNavBar(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = Color(0xFF1B4AD0),
            modifier = Modifier.size(28.dp)
        )
        Box {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color(0xFF1B4AD0),
                modifier = Modifier.size(28.dp)
            )
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(Color.Red, CircleShape)
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = "12",
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CarCard(carName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
            Text(
                text = carName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B4AD0),
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}

@Composable
fun AddCarCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFDCE8FF))
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add car",
                    tint = Color(0xFF1B4AD0),
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = "Mashina qo’shish",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1B4AD0)
                )
            }
        }
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

@Preview
@Composable
fun CarListScreenPreview() {
    val cars = remember { mutableStateListOf("Toyota Camry 2020", "Chevrolet Spark 2021") }

    CarListScreen(
        cars = cars,
        onAddCarClicked = {
            // Пример добавления новой машины
            cars.add(0, "Yangi mashina")
        }
    )
}