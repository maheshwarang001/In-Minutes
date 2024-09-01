package com.example.rider_app.view.component

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rider_app.R
import com.example.rider_app.network.foreground.LocationService
import com.example.rider_app.network.foreground.WebSocketService
import com.example.rider_app.route.HomeScreen
import com.example.rider_app.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHomeScreen( navController: NavController, checked: Boolean , viewModel : HomeViewModel = viewModel() ){


    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current


    TopAppBar(
        title = { Text("" ) },

        navigationIcon = {

            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }

        },

        actions = {



            IconButton(onClick = { /*TODO*/ }){
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Transparent, CircleShape)
                        .border(2.dp, Color.Red, CircleShape)
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_sos_24), // Your SOS icon
                        contentDescription = "SOS",
                        tint = Color.Red,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }

            IconButton(onClick = { navController.navigate(HomeScreen) }) {

                Icon(
                    painter = painterResource(id = R.drawable.baseline_map_24),
                    contentDescription = "Map",
                )

            }

            IconButton(onClick = { /*TODO*/ }){
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                )
            }

            Switch(
                checked = checked ,
                onCheckedChange = {

                   showDialog = true
                                  },
                thumbContent = if (checked) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    null
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )

        }

    )

    if(showDialog){
        AlertBoxActiveTracker(

            onDismissRequest = {
                showDialog = false
                viewModel.onCheckedChange(checked)
            },

            onConfirmation = {



                viewModel.onCheckedChange(!checked)

                if(!checked){

                    try {

                        Intent(context, WebSocketService::class.java).apply {
                            action = WebSocketService.ACTION_START
                            context.startService(this)
                        }

                        Intent(context, LocationService::class.java).apply {
                            action = LocationService.ACTION_START
                            context.startService(this)
                        }
                    }catch (e : Exception){
                        Toast.makeText(context, "Failed to start services", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    try {

                        Intent(context, WebSocketService::class.java).apply {
                            action = WebSocketService.ACTION_STOP
                            context.startService(this)
                        }

                        Intent(context, LocationService::class.java).apply {
                            action = LocationService.ACTION_STOP
                            context.startService(this)
                        }
                    }
                    catch (e : Exception) {
                        Toast.makeText(context, "Failed to Stop services", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                showDialog = false
            },

            dialogTitle = "Work Activates",
            dialogText = "Do you want to change the active tracker",
            icon = Icons.Outlined.Warning
        )
    }

}