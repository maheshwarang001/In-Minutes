package com.example.rider_app.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rider_app.R
import com.example.rider_app.view.component.OrderSearchCard
import com.example.rider_app.view.component.PermissionDialog
import com.example.rider_app.view.component.TopAppBarHomeScreen
import com.example.rider_app.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

// Permissions needed



@SuppressLint("DefaultLocale")
@Composable
fun HomeMainScreen(navController: NavController) {

    val viewModel: HomeViewModel = viewModel()
    val dialogQueue = viewModel.visiblePermissionQueue

    val checked by viewModel.isChecked
    val timeLeft by viewModel.timeLeft.observeAsState("00:00")


    // Permission launcher
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            viewModel.permissionsToRequest.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )

    LaunchedEffect(Unit) {
        multiplePermissionResultLauncher.launch(viewModel.permissionsToRequest)
    }






    Scaffold(
        topBar = { TopAppBarHomeScreen(navController, checked , viewModel) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                // Order Search Card
                Card(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth()
                        .size(200.dp),
                    elevation = CardDefaults.cardElevation(20.dp)
                ) {
                    OrderSearchCard(checked)
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Profile Links",
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // First Row of Cards
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_wallet_24),
                                contentDescription = "Wallet",
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "£ 15",
                                fontSize = 24.sp

                            )
                            Spacer(modifier = Modifier.height(1.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Today's Earnings",
                                    fontSize = 12.sp
                                )
                                IconButton(onClick = { /* TODO: Handle arrow click */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowRight,
                                        contentDescription = "Arrow"
                                    )
                                }
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_wallet_24),
                                contentDescription = "Wallet",
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "£ 30",
                                fontSize = 24.sp

                            )
                            Spacer(modifier = Modifier.height(1.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Week's Earnings",
                                    fontSize = 12.sp
                                )
                                IconButton(onClick = { /* TODO: Handle arrow click */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowRight,
                                        contentDescription = "Arrow"
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Second Row of Cards
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_wallet_24),
                                contentDescription = "Wallet",
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "£ 30",
                                fontSize = 24.sp

                            )
                            Spacer(modifier = Modifier.height(1.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Cash out",
                                    fontSize = 12.sp
                                )
                                IconButton(onClick = { /* TODO: Handle arrow click */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowRight,
                                        contentDescription = "Arrow"
                                    )
                                }
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_access_time_24),
                                contentDescription = "Time",
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = timeLeft,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.height(1.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Login duration",
                                    fontSize = 12.sp
                                )
                                IconButton(onClick = { /* TODO: Handle arrow click */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowRight,
                                        contentDescription = "Arrow"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    // Permission Dialogs
    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = "Permission for $permission is required.",
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(navController.context as Activity, permission),
                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(arrayOf(permission))
                },
                onGoToAppSettingClick = { openAppSettings(navController.context as Activity) }
            )
        }
}

// Function to open app settings
fun openAppSettings(activity: Activity) {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", activity.packageName, null)
    ).also { activity.startActivity(it) }
}

@Composable
@Preview
fun Preview2() {
    HomeMainScreen(navController = rememberNavController())
}
