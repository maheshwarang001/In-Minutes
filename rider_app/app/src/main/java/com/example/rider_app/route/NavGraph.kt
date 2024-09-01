package com.example.rider_app.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.rider_app.view.HomeMainScreen
import com.example.rider_app.view.OrderScreen
import kotlinx.serialization.Serializable


@Composable
fun SetupNavGraph(navHostController : NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = RiderMainScree
    ) {

        composable<RiderMainScree> {
            HomeMainScreen(navHostController)
        }

        composable<HomeScreen> {
            OrderScreen(navHostController)
        }

    }
}

