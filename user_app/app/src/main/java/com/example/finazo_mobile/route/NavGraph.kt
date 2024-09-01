package com.example.finazo_mobile.route
import Item
import ItemList
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.finazo_mobile.view.FinzoCart
import com.example.finazo_mobile.view.FinzoHomeScreen
import com.example.finazo_mobile.view.FinzoSubCategoryScreen
import com.example.finazo_mobile.view.OtpScreenPinScreen
import com.example.finazo_mobile.view.ProductViewDetail
import com.example.finazo_mobile.view.SignUpScreenUI
import com.example.finazo_mobile.viewmodel.OtpScreenPinViewModel
import com.example.finazo_mobile.viewmodel.SignUpViewModel


@Composable
fun SetupNavGraph(navHostController : NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = HomeScreen
    ) {

        composable<Cart> {
            FinzoCart()
        }
        composable<ProductDetail> {
            ProductViewDetail()
        }
        composable<SubCategories> {

            FinzoSubCategoryScreen()

        }
        composable<HomeScreen> {
            FinzoHomeScreen()
        }
        composable<SignUpScreen> {
            Log.d("NavGraph", "SignUpScreen: ")
            SignUpScreenUI(navController = navHostController, viewModel = SignUpViewModel() )
        }
        composable<SignIn>{
            Log.d("NavGraph","SignIn")
        }
        composable<OtpScreenPin> {
            val args = it.toRoute<OtpScreenPin>()
            Log.d("NavGraph","OtpScreenPin" )
            OtpScreenPinScreen(args = args, navController = navHostController, viewModel = OtpScreenPinViewModel())
        }

        // Add more routes here as needed
    }
}