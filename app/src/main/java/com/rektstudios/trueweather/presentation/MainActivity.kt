package com.rektstudios.trueweather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.rektstudios.trueweather.presentation.ui.CitiesScreen
import com.rektstudios.trueweather.presentation.ui.HomeScreen
import com.rektstudios.trueweather.presentation.ui.theme.TrueWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = remember { mutableStateListOf<TopLevelRoute>(TopLevelRoute.Home) }
            TrueWeatherTheme {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = { key ->
                        when (key) {
                            is TopLevelRoute.Home -> {
                                NavEntry(key) {
                                    HomeScreen({ backStack.add(TopLevelRoute.Cities) })
                                }
                            }

                            is TopLevelRoute.Cities -> {
                                NavEntry(key) {
                                    CitiesScreen({ backStack.removeLastOrNull() })
                                }
                            }
                        }
                    },
                )
            }
        }
    }
}

private sealed interface TopLevelRoute {
    data object Home : TopLevelRoute

    data object Cities : TopLevelRoute
}
