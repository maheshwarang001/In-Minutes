package com.example.finazo_mobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finazo_mobile.route.SetupNavGraph
import com.example.finazo_mobile.route.SignUpScreen
import com.example.finazo_mobile.view.SignUpScreenUI
import com.example.finazo_mobile.viewmodel.SignUpViewModel
import com.example.finazo_mobile.ui.theme.Finazo_mobileTheme
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Finazo_mobileTheme {

                SetupNavGraph( rememberNavController() )

            }
        }
    }
}
@Serializable
object SignUpScreen




