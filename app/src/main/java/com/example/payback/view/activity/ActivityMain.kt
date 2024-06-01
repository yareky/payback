package com.example.payback.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.payback.view.navigation.Navigation
import com.example.payback.view.screens.LayoutDetails
import com.example.payback.view.screens.LayoutSearch
import com.example.payback.view.theme.PayBackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PayBackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Navigation.Search.route
                    ) {
                        composable(Navigation.Search.route) { LayoutSearch(navController) }
                        composable(
                            Navigation.Details.route,
                            arguments = Navigation.Details.arguments
                        ) { backStackEntry ->
                            LayoutDetails(
                                navController,
                                hitId = Navigation.Details.getImageId(backStackEntry) ?: 0
                            )
                        }
                    }

                }
            }
        }
    }
}

