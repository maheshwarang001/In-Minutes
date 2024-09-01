package com.example.rider_app.view

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rider_app.R
import com.example.rider_app.view.component.Map
import com.example.rider_app.view.component.TopAppBarMap


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(navController: NavController){


    var expandedOrderDetails by remember {
        mutableStateOf(false)
    }

    var showBottomSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(

        skipPartiallyExpanded = false,
    )

    Scaffold(


        topBar = {

            TopAppBarMap(navController)
        },
        bottomBar = {

            if(showBottomSheet) {


                ModalBottomSheet(
                    modifier = Modifier.fillMaxHeight(),
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false }) {


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                            .padding(10.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                                .animateContentSize()
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Top

                            ) {


                                Row(

                                ) {

                                    Icon(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = "Customer",
                                        modifier = Modifier.padding(end = 2.dp)
                                    )

                                    Text(
                                        text = "Customer: Shawn Murfy",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }


                                Row(

                                ) {

                                    Icon(
                                        imageVector = Icons.Outlined.LocationOn,
                                        contentDescription = "Location",
                                        modifier = Modifier.padding(end = 2.dp)
                                    )

                                    Text(
                                        text = "2/4 Raglan street, Raglan house, coventry, CV1 5QF",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                }

                                Spacer(modifier = Modifier.height(10.dp))


                                Row(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Button(
                                        modifier = Modifier
                                            .weight(1f),
                                        onClick = { /*TODO*/ }

                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_call_24),
                                            contentDescription = "Call customer",
                                            tint = Color.Blue
                                        )
                                        Text(text = "Call", color = Color.Blue)
                                    }
                                    Button(
                                        colors = ButtonDefaults.buttonColors(Color.Blue),
                                        modifier = Modifier
                                            .weight(1f),
                                        onClick = { /*TODO*/ }
                                    ) {
                                        Icon(
                                            tint = Color.White,
                                            painter = painterResource(id = R.drawable.baseline_subdirectory_arrow_right_24),
                                            contentDescription = "Customer direction open google maps"
                                        )
                                        Text(text = "Go To Map", color = Color.White)
                                    }
                                }

                            }
                        }



                        Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_electric_bolt_24),
                                contentDescription = "Order id"
                            )
                            Text(
                                text = "ORDER ID: 2233231",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                                .fillMaxWidth(),

                            ) {
                            Row {
                                Text(
                                    text = "Store Details",
                                    modifier = Modifier
                                        .weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "Navigate"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        if (sheetState.currentValue == SheetValue.Expanded) {

                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                            ) {
                                Row {
                                    Text(
                                        text = "Drop Details",
                                        modifier = Modifier
                                            .weight(1f)
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowRight,
                                        contentDescription = "Navigate"
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                                    .animateContentSize()
                                    .fillMaxWidth(),
                            ) {
                                Column {


                                    Row(
                                        modifier = Modifier.clickable {
                                            expandedOrderDetails = !expandedOrderDetails
                                        }
                                    ) {

                                        Text(
                                            text = "Order Details",
                                            modifier = Modifier
                                                .weight(1f)
                                        )

                                        Icon(
                                            imageVector =
                                            if (!expandedOrderDetails) Icons.Filled.KeyboardArrowRight else Icons.Filled.KeyboardArrowLeft,
                                            contentDescription = "Navigate",

                                            )

                                    }

                                    if (expandedOrderDetails) {

                                        Column(
                                            modifier = Modifier
                                                .padding(start = 5.dp)
                                        ) {

                                            Column(
                                                modifier = Modifier
                                                    .padding(top = 5.dp, bottom = 5.dp),
                                            ) {
                                                Text(
                                                    text = "1 x Pizza base",
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.padding(top = 2.dp)
                                                )
                                                Text(
                                                    text = "4 x Ear Plugs",
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.padding(top = 2.dp)
                                                )
                                                Text(
                                                    text = "1 x Blue Jeans",
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.padding(top = 2.dp)
                                                )
                                            }

                                            Text(
                                                text = "Total: £48.98",
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 12.sp,
                                                modifier = Modifier.padding(top = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }


                            Row(modifier = Modifier
                                .height(100.dp)
                                .padding(top = 50.dp)
                                .fillMaxWidth(),

                            ){

                                Button(modifier = Modifier
                                    .padding(start = 5.dp, end = 5.dp)
                                    .fillMaxHeight()
                                    .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                                    .weight(1f),
                                    onClick = { /*TODO*/ },
                                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                                    shape = RoundedCornerShape(0.dp)
                                ) {
                                    Icon(painter =  painterResource(id = R.drawable.baseline_question_mark_24), contentDescription = "FAQ", tint = Color.Black)
                                    Text(text = "FAQ", color = Color.Black)
                                }
                                Button(modifier = Modifier
                                    .padding(start = 5.dp, end = 5.dp)
                                    .fillMaxHeight()
                                    .border(1.dp, Color.Red, RoundedCornerShape(8.dp))
                                    .weight(1f),
                                    onClick = { /*TODO*/ },
                                    colors = ButtonDefaults.buttonColors(Color.Red),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(imageVector = Icons.Outlined.Clear, contentDescription = "Cancel ride")
                                    Text(text = "Cancel")
                                }

                            }


                        }
                    }


                }
            }else{
                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(200.dp)
                           .clickable { showBottomSheet = true }
                   )
                   {

                       Card(
                           modifier = Modifier
                               .fillMaxWidth()
                               .height(200.dp),
                           elevation = CardDefaults.elevatedCardElevation(20.dp),
                           colors = CardDefaults.cardColors(containerColor = Color.Black ),
                           shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                       ) {

                           Column(
                               modifier = Modifier
                                   .padding(start = 10.dp, end = 10.dp)
                                   .fillMaxSize(),
                               verticalArrangement = Arrangement.Top,
                           ) {

                               Box(
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(top = 10.dp, bottom = 10.dp)
                                   ,
                                   contentAlignment = Alignment.TopCenter
                               ){
                                   Icon(
                                       imageVector = Icons.Filled.KeyboardArrowUp,
                                       contentDescription = "Scroll",
                                       modifier = Modifier
                                           .size(24.dp)
                                   )
                               }



                               Row(
                                   modifier = Modifier
                                       .fillMaxWidth(),
                                   horizontalArrangement = Arrangement.SpaceBetween
                               ) {

                                   Box(
                                       modifier = Modifier
                                           .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                           .padding(8.dp)
                                           .width(250.dp)
                                   ) {
                                       Column {
                                           Row {
                                               Text(
                                                   text = "ORDER ID",
                                                   color = Color.White,
                                                   fontSize = 12.sp)
                                               Spacer(modifier = Modifier.width(8.dp))
                                               Text(
                                                   text = "1234232",
                                                   color = Color.White,
                                                   fontSize = 12.sp
                                                   )
                                           }
                                           Spacer(modifier = Modifier.height(4.dp))
                                           Row {
                                               Text(
                                                   text = "CUSTOMER",
                                                   color = Color.White,
                                                   fontSize = 12.sp)
                                               Spacer(modifier = Modifier.width(8.dp))
                                               Text(
                                                   text = "Shawn Murphy",
                                                   color = Color.White,
                                                   fontSize = 12.sp
                                               )
                                           }
                                       }
                                   }

                                   Box(
                                       modifier = Modifier
                                           .padding(start = 20.dp)
                                       ,
                                       contentAlignment = Alignment.TopStart

                                   ) {
                                       Column {

                                               Text(
                                                   text = "10:20",
                                                   color = Color.White,
                                                   fontSize = 12.sp
                                               )
                                       }

                                   }


                               }




                           }

                       }
                   }


            }

        },


        content = {

            Column(
                modifier = Modifier
                    .padding()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(800.dp)
                ) {
                    Map(false);
                }

            }



        }

    )

}


@Composable
@Preview
fun Preview(){
    OrderScreen(navController = rememberNavController())
}
enum class SheetValue { Collapsed, PartiallyExpanded, Expanded }
