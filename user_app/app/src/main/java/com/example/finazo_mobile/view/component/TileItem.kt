package com.example.finazo_mobile.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun TileItem() {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .height(300.dp)
        ,
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(70.dp)
                    .background(Color.Red, shape = RoundedCornerShape(
                        topStart = CornerSize(0.dp),
                        topEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(8.dp)
                     )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "4 % off", color = Color.White)
            }
            
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = "https://www.bigbasket.com/media/uploads/p/l/40092247_3-britannia-bread-healthy-slice.jpg",
                    contentDescription = "item image",
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentScale = ContentScale.Fit
                )
                Text(text = "Item Name")
                Text(text = "Item Units")
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "£10", textDecoration = TextDecoration.LineThrough)
                        Text(text = "£8.5")
                    }
                    Box(
                        modifier = Modifier
                            .clickable {  }
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray)
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Add to Cart",
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LazyGridView(modifier: Modifier = Modifier) {
    val list = (1..10).map { it.toString() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(list) {
            TileItem()
        }
    }
}

@Composable
@Preview
fun Preview3() {
    LazyGridView()
}
