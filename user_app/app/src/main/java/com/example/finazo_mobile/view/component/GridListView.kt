package com.example.finazo_mobile.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun GridListView(modifier: Modifier = Modifier, header: String, url: String) {

    val array = (1..9).map { it.toString() }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = header,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)
        )

        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .height(115.dp * (array.size / 3)),
            userScrollEnabled = false,
            columns = GridCells.Adaptive(minSize = 100.dp),
            //contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(array.size) {
                ItemDisplaySubCategory(Modifier)
            }
        }
    }

}


@Composable
fun ItemDisplaySubCategory(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(110.dp)
            .clip(RoundedCornerShape(10.dp))
            .width(80.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                AsyncImage(
                    model = "https://i.pinimg.com/736x/97/29/ba/9729ba4cdd70c69539f3aec7f6dc7331.jpg",
                    contentDescription = "Category Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                text = "Apparel & Kitchen",
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun preview(){
    GridListView(Modifier,"Beauty & Care","")
}