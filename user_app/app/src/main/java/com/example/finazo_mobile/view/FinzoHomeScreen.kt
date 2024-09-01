package com.example.finazo_mobile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.finazo_mobile.view.component.CustomSearchBar
import com.example.finazo_mobile.view.component.GridListView
import com.example.finazo_mobile.view.component.TopBarFinzoV1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinzoHomeScreen() {

    var query by remember { mutableStateOf("") }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
//    val scrollBehaviorMagic = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

//            Column {
//                TopBarFinzoV1(scrollBehaviorMagic)

                TopAppBar(
                    title = {
                        CustomSearchBar(
                            modifier = Modifier,
                            query = query,
                            onQueryChange = { q -> query = q },
                            onSearch = {}
                        )
                    },
                    actions = {
                        IconButton(onClick = { /* Handle account icon click */ }) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "Account",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
//            }

        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(3) {
                        GridListView(Modifier, "Beauty & Care", "")
                    }
                }
//                Spacer(modifier = Modifier.height(16.dp))
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
@Preview
fun Preview() {
    FinzoHomeScreen()
}
