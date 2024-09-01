package com.example.rider_app.view.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.rider_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMap(navController: NavController)
{

    TopAppBar(
        title = {
            Text(
                text = "",
//                maxLines = 2,
//                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )

                },
        navigationIcon = {
            IconButton(onClick = {navController.navigateUp() }) {

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Navigate Back"
                )

            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {

                Icon(
                    painter = painterResource(id = R.drawable.baseline_question_mark_24),
                    contentDescription = "Navigate Back"
                )

            }
        }

    )
}