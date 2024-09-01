package com.example.finazo_mobile.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProductViewDetailComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Ensure to specify a valid model or URL
        AsyncImage(
            model = "https://www.bigbasket.com/media/uploads/p/l/40092247_3-britannia-bread-healthy-slice.jpg",
            contentDescription = "Product",
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .background(color = Color.White)
            ,
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            ,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Britannia Bread".uppercase(),
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(2.dp))

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "1 x unit".uppercase(),
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "1 kg".uppercase(),
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.SemiBold

                )

            }

            Spacer(modifier = Modifier.height(2.dp))



            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Column for the prices

                Box {

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "£30",
                            fontSize = 20.sp,
                            color = Color.Magenta,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "£34",
                            fontSize = 15.sp,
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "20 % OFF",
                            fontSize = 15.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Normal
                        )
                    }

                }


                // Button
                Button(
                    onClick = { /* TODO: Handle button click */ },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                ) {
                    Text(text = "Add to Cart")
                }

            }
            Spacer(modifier = Modifier.height(5.dp))


            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .height(1.dp)

            )

            Spacer(modifier = Modifier.height(5.dp))




            ProductInfoDetails()




        }
    }
}


@Composable
fun ProductInfoDetails(){

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){


        Text(
            text = "Product Information".uppercase(),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Country of Origin".uppercase(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()

        )

        Text(
            text = "United Kingdom".uppercase(),
            fontSize = 14.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Normal,


            )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Manufacturer Name".uppercase(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "Britannia Limited".uppercase(),
            fontSize = 14.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Manufacturer Address".uppercase(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "Street London, United Kingdom".uppercase(),
            fontSize = 14.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Normal,
        )

    }

    
}

@Composable
@Preview
fun Preview34() {
    ProductViewDetailComponent()
}
