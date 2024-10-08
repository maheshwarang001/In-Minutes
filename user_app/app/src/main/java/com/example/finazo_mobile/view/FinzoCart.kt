package com.example.finazo_mobile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finazo_mobile.view.component.InScreenAppBar
import com.example.finazo_mobile.view.component.LazyCartView


@Composable
fun FinzoCart(){

    Scaffold(

        topBar = {
            InScreenAppBar()
        },
        content = {paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    items(10){
                        LazyCartView()
                        Spacer(modifier =
                        Modifier.fillMaxWidth()
                            .height(1.dp).background(color = Color.LightGray)
                        )
                    }

                }

            }
        }
    )

}

@Composable
@Preview
fun Preview32(){
    FinzoCart()
}