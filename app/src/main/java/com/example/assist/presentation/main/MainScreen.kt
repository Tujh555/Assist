package com.example.assist.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastForEach
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.assist.presentation.expenses.ExpenseScreen

class MainScreen : Screen {
    // TODO add another
    private val tabs = listOf(ExpenseScreen())

    @Composable
    override fun Content() {
        TabNavigator(tabs.first()) {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = Color(0xFF1B4AD0),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val tabNavigator = LocalTabNavigator.current

                        tabs.fastForEach { tab ->
                            key(tab.key) {
                                val options = tab.options
                                NavigationBarItem(
                                    selected = tabNavigator.current.key == tab.key,
                                    onClick = { tabNavigator.current = tab },
                                    icon = {
                                        Icon(
                                            painter = options.icon!!,
                                            contentDescription = options.title
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    CurrentTab()
                }
            }

        }
    }
}