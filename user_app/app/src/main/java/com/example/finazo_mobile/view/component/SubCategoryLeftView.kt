import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class Item(val imageUrl: String, val name: String)

@Composable
fun ItemCard(item: Item) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top=8.dp,bottom=8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)  // Maintain the aspect ratio
                    //.padding(bottom = 8.dp)
                ,
                contentScale = ContentScale.Fit
            )
            Text(
                text = item.name,
                //lineHeight = 0.dp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ItemList(items: List<Item>) {
    LazyColumn {
        items(items) { item ->
            ItemCard(item = item)
        }
    }
}

@Preview
@Composable
fun Preview() {
    val sampleItems = listOf(
        Item(imageUrl = "https://www.shutterstock.com/image-photo/chisinau-moldova-december-152015photo-cocacola-600nw-1393110701.jpg", name = "Item 1"),
        Item(imageUrl = "https://www.shutterstock.com/image-photo/chisinau-moldova-december-152015photo-cocacola-600nw-1393110701.jpg", name = "Item 2")
    )

    ItemList(items = sampleItems);

}
