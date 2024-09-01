package com.example.finazo_mobile.view

import Item
import ItemList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finazo_mobile.view.component.InScreenAppBar
import com.example.finazo_mobile.view.component.LazyGridView

@Composable
fun FinzoSubCategoryScreen() {
    Scaffold(

        topBar = {
            InScreenAppBar()
        },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    ScreenRow(
                        modifier = Modifier
                            .weight(0.2f)
                            .fillMaxHeight()
                    )
                    LazyGridView(
                        modifier = Modifier
                            .weight(0.8f)
                            .fillMaxHeight()
                            .background(Color.LightGray)
                    )
                }

                Box(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Column {
                            Text(text = "Get Free Delivery")
                            Text(text = "on shopping products worth £30")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ScreenRow(modifier: Modifier = Modifier) {
    val sampleItems = listOf(
        Item(
            imageUrl = "https://www.shutterstock.com/image-photo/chisinau-moldova-december-152015photo-cocacola-600nw-1393110701.jpg",
            name = "Item 1"
        ),
        Item(
            imageUrl = "https://www.shutterstock.com/image-photo/chisinau-moldova-december-152015photo-cocacola-600nw-1393110701.jpg",
            name = "Item 2"
        ),
        Item(
            imageUrl = "https://www.shutterstock.com/image-photo/chisinau-moldova-december-152015photo-cocacola-600nw-1393110701.jpg",
            name = "Item 3"
        ),
        Item(
            imageUrl = "https://www.shutterstock.com/image-photo/chisinau-moldova-december-152015photo-cocacola-600nw-1393110701.jpg",
            name = "Item 4"
        ),
        Item(
            imageUrl = "https://www.shutterstock.com/image-photo/chisinau-moldova-december-152015photo-cocacola-600nw-1393110701.jpg",
            name = "Item 5"
        ),
        Item(
            imageUrl = "https://www.shutterstock.com/image-photo/chisinau-moldova-december-152015photo-cocacola-600nw-1393110701.jpg",
            name = "Item 6"
        ),
        Item(
            imageUrl = "https://www.shutterstock.com/image-photo/chisinau-moldova-december-152015photo-cocacola-600nw-1393110701.jpg",
            name = "Item 7"
        ),
        Item(
            imageUrl = "https://www.shutterstock.com/image-photo/chisinau-moldova-december-152015photo-cocacola-600nw-1393110701.jpg",
            name = "Item 8"
        )
    )
    Row(modifier = modifier) {
        ItemList(items = sampleItems)
    }
}

@Preview
@Composable
fun Preview2() {
    FinzoSubCategoryScreen()
}
