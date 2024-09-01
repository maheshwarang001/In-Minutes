package com.example.finazo_mobile.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.finazo_mobile.R

@Composable
fun LazyCartView() {
    Column(
        modifier = Modifier.padding(16.dp) // Padding around the entire Column
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth() // Ensure Row takes full width
        ) {
            AsyncImage(
                model = "https://www.bigbasket.com/media/uploads/p/l/40092247_3-britannia-bread-healthy-slice.jpg", // Replace with actual image URL or placeholder
                contentDescription = "Product Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(60.dp) // Adjust size as needed
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Britannia Bread".uppercase(),
                    // style = MaterialTheme.typography.body1
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "£30",
                        // style = MaterialTheme.typography.body2
                    )
                    Text(text = "£40",
                        textDecoration = TextDecoration.LineThrough
                        //style = MaterialTheme.typography.body2
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Push icon to the end

            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close",
                modifier = Modifier.size(24.dp) // Adjust size as needed
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Space between rows

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth() // Ensure Row takes full width
        ) {
            Text(text = "500g",
                //style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.weight(1f)) // Push icon to the end

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_24),
                    contentDescription = "Decrease",
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 0.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .clickable { /* Handle decrement */ }
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.White)
                ) {
                    Text(
                        text = "1",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                } // Example quantity
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Increase",
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 8.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 8.dp
                            )
                        )
                        .clickable { /* Handle increment */ }
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewLazyCartView() {
    LazyCartView()
}
