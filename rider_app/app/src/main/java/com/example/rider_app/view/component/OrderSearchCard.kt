package com.example.rider_app.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rider_app.R

@Composable
fun OrderSearchCard(checked: Boolean){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Main text
        Text(
            text = if (checked) "Searching for orders..." else "Not Active",
            fontSize = 24.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp)) // Add space between texts

        Text(text = "Today so far", fontSize = 18.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp)) // Add space between sections

        // Row with icons and text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Outlined.Check,
                contentDescription = "Orders",
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "25 orders", fontSize = 18.sp)

            Spacer(modifier = Modifier.width(16.dp)) // Add space between the two items

            Icon(
                painter = painterResource(id = R.drawable.baseline_card_travel_24),
                contentDescription = "Distance",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "10 kms", fontSize = 18.sp)
        }
    }
}