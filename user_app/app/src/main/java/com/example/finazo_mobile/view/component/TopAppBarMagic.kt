package com.example.finazo_mobile.view.component
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarFinzoV1(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .height(25.dp)
                        .width(200.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Delivery in",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(end = 5.dp)
                    )
                    Text(
                        text = "10 Mins",
                        fontSize = 18.sp,
                        color = Color(0xFFB729CF),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(
                    modifier = Modifier
                        .height(25.dp)
                        .width(200.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Home -",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(end = 5.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Ganesh Nagar, Bhandup",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = "Account"
                        )
                    }
                }
            }
        },
        navigationIcon = {},
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    tint = Color.LightGray,
                    contentDescription = "Account",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}