package com.example.finazo_mobile.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.finazo_mobile.SignUpScreen
import com.example.finazo_mobile.route.OtpScreenPin
import com.example.finazo_mobile.route.SignIn
import com.example.finazo_mobile.viewmodel.SignUpViewModel
import kotlin.math.sin


@Composable
fun SignUpScreenUI(navController: NavHostController, viewModel: SignUpViewModel) {

    Log.d("I CAME HERE too", "SetupNavGraph: ")
    val email by viewModel.email.observeAsState("")
    val otpRequest by viewModel.request.observeAsState(false)

    val emoji = String(Character.toChars(0x1F600))


    if (otpRequest) {
        otpScreen(navController,viewModel,email)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
        ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {


        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(40.dp)
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clickable { navController.popBackStack() }
            ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, // Arrow icon
                contentDescription = "Arrow",
                modifier = Modifier.size(24.dp) // Icon size adjusted
            )
        }


        Spacer(modifier = Modifier.height(20.dp))


        Text(
            text = "Create An Account $emoji",
            modifier = Modifier
                //.height(20.dp)
                .padding(top = 20.dp),
            fontFamily = FontFamily.Serif,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Enter your email address to get the \npassword reset link",
            modifier = Modifier
                //.height(20.dp)
                .padding(top = 15.dp),
            fontFamily = FontFamily.Serif,
            fontSize = 15.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
        )


        Spacer(modifier = Modifier
            .height(100.dp))



            Log.d("SignUpScreenUI", "TextField initialized")

            OutlinedTextField(
                value = email,
                onValueChange = {
                    viewModel.onEmailChange(it)
                },
                label = {
                    Text(
                        text = "Email"
                    )
                },

                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                   // .background(Color.Transparent)

            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .background(Color(0xFF1313F3), shape = RoundedCornerShape(20.dp))
            ) {
                Button(
                    enabled = viewModel.isCheck(),
                    onClick = { viewModel.onSignUpClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp), // Padding should be zero to avoid extra spacing
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent // Make button background transparent
                    )
                ) {
                    Text(
                        "OTP",
                        color = Color.White,
                        fontSize = 15.sp,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

        }



    }

}

//fun arrowClicked(navController: NavController, viewModel: SignUpViewModel) {
//    Log.d("Navigate", "arrowClicked: ")
//    viewModel.clearData()
//    navController.navigate(SignIn) {
//        popUpTo(navController.graph.startDestinationId) { inclusive = true }
//    }
//}

fun otpScreen(navController: NavController, viewModel: SignUpViewModel, email: String) {
    Log.d("Navigate", "arrowClicked: ")
    viewModel.clearData()
    Log.d("Email", email)

    if(email.isBlank())return
    navController.navigate(OtpScreenPin(email))
//    {
//        popUpTo(navController.graph.startDestinationId) { inclusive = true }
//    }

}

@Composable
@Preview
fun preview(){
    SignUpScreenUI(navController = rememberNavController(), viewModel = SignUpViewModel() )
}