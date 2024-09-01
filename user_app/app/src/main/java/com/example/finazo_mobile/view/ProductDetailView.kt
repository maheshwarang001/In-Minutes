package com.example.finazo_mobile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.finazo_mobile.view.component.InScreenAppBar
import com.example.finazo_mobile.view.component.ProductViewDetailComponent


@Composable
fun ProductViewDetail(){

    val scrollState = rememberScrollState()

    Scaffold(

        topBar = {
            InScreenAppBar()
        },

        content = {paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {

                ProductViewDetailComponent()

            }
        }
    )

}

@Composable
@Preview
fun Preview4(){

    ProductViewDetail()
}