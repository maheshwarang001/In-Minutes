package com.example.finazo_mobile.route

import androidx.navigation.NavController
import kotlinx.serialization.Serializable


//Object for SignUpScreen
@Serializable
object SignUpScreen

//Object for SignInScreen
@Serializable
object SignIn

//Class for Otp screen
@Serializable
data class OtpScreenPin(val email : String)

@Serializable
object HomeScreen

@Serializable
object SubCategories

@Serializable
object ProductDetail

@Serializable
object Cart