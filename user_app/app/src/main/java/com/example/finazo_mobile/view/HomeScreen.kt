//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.outlined.AccountCircle
//import androidx.compose.material.icons.outlined.KeyboardArrowDown
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.*
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.nestedscroll.nestedScroll
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun FinzoHomeScreen() {
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
//    var query by remember { mutableStateOf("") }
//
//    Scaffold(
//        modifier = Modifier
//            .nestedScroll(scrollBehavior.nestedScrollConnection),
//    ) { paddingValues ->
//        Box(modifier = Modifier.fillMaxSize()) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//                    //.verticalScroll(rememberScrollState())
//            ) {
//                item {
//                    TopBarFinzoV1(scrollBehavior = scrollBehavior)
//                    CustomSearchBar(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 10.dp, end = 10.dp),
//                        query = query,
//                        onQueryChange = { newQuery -> query = newQuery },
//                        onSearch = {
//                            // Handle search action
//                        }
//                    )
//                    CategoryPoster(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
//                        "",
//                        ""
//                    )
//                    CategoryPoster(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
//                        "",
//                        ""
//                    )
//                    CategoryPoster(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
//                        "",
//                        ""
//                    )
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CustomSearchBar(
//    modifier: Modifier = Modifier,
//    query: String,
//    onQueryChange: (String) -> Unit,
//    onSearch: () -> Unit
//) {
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
//
//    var isFocused by remember { mutableStateOf(false) }
//    val focusRequester = remember { FocusRequester() }
//
//    TopAppBar(
//        title = { /* Add a title if needed, or leave it empty */ },
//        actions ={
//
//            Box(
//                modifier = modifier
//                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15.dp))
//                    .height(50.dp)
//                    .padding(10.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.Search,
//                        contentDescription = "Search",
//                        modifier = Modifier
//                            .size(30.dp)
//                            .padding(start = 8.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    BasicTextField(
//                        singleLine = true,
//                        value = query,
//                        onValueChange = onQueryChange,
//                        keyboardOptions = KeyboardOptions.Default.copy(
//                            imeAction = ImeAction.Search
//                        ),
//                        keyboardActions = KeyboardActions(
//                            onSearch = {
//                                onSearch()
//                                // Request focus when search action is triggered
//                                focusRequester.requestFocus()
//                            }
//                        ),
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(vertical = 2.dp)
//                            .focusRequester(focusRequester)
//                            .background(Color.Transparent),
//                        textStyle = MaterialTheme.typography.bodyMedium.copy(
//                            color = Color.Black,
//                            fontSize = 16.sp
//                        )
//                    )
//                }
//            }
//        },
//        scrollBehavior = scrollBehavior,
//    )
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopBarFinzoV1(scrollBehavior: TopAppBarScrollBehavior) {
//    TopAppBar(
//        title = {
//            Column(
//                modifier = Modifier,
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.Start
//            ) {
//                Row(
//                    modifier = Modifier
//                        .height(25.dp)
//                        .width(200.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Delivery in",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier
//                            .padding(end = 5.dp)
//                    )
//                    Text(
//                        text = "10 Mins",
//                        fontSize = 18.sp,
//                        color = Color(0xFFB729CF),
//                        fontWeight = FontWeight.SemiBold
//                    )
//                }
//
//                Row(
//                    modifier = Modifier
//                        .height(25.dp)
//                        .width(200.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Home -",
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier
//                            .padding(end = 5.dp),
//                        textAlign = TextAlign.Center
//                    )
//                    Text(
//                        text = "Ganesh Nagar, Bhandup",
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        overflow = TextOverflow.Ellipsis,
//                        modifier = Modifier.weight(1f),
//                    )
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(
//                            imageVector = Icons.Outlined.KeyboardArrowDown,
//                            contentDescription = "Account"
//                        )
//                    }
//                }
//            }
//        },
//        navigationIcon = {},
//        actions = {
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    imageVector = Icons.Outlined.AccountCircle,
//                    tint = Color.LightGray,
//                    contentDescription = "Account",
//                    modifier = Modifier
//                        .size(40.dp)
//                )
//            }
//        },
//        scrollBehavior = scrollBehavior,
//    )
//}
//
//@Composable
//fun TopBar() {
//    // Implementation of your TopBar
//}
//
//@Composable
//fun CategoryPoster(modifier: Modifier = Modifier, header: String, url: String) {
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.Start
//    ) {
//        Text(
//            text = "Beauty & Personal Care",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.SemiBold,
//            modifier = Modifier
//                .padding(start = 10.dp)
//        )
//
//        //Spacer(modifier = modifier.height(1.dp))
//        val list = (1..10).map { it.toString() }
//
//
//        //Box(modifier = Modifier) {
//            LazyVerticalGrid(
//                userScrollEnabled = false,
//                columns = GridCells.Adaptive(100.dp),
//                modifier =
//                Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//
//                ,
////                contentPadding = PaddingValues(0.dp),
////                verticalArrangement = Arrangement.spacedBy(0.dp),
////                horizontalArrangement = Arrangement.spacedBy(0.dp)
//
//                content = {
//
//                        items(list.size) { index ->
//                            ItemDisplaySubCategory(modifier)
//                        }
//
//
//                }
//
//            )
//
//        }
//    //}
//}
//
//@Composable
//fun ItemDisplaySubCategory(modifier: Modifier = Modifier) {
//    Box(
//        modifier = modifier
//            .height(100.dp)
//            .clip(RoundedCornerShape(10.dp))
//            .width(80.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Card(
//                shape = RoundedCornerShape(10.dp),
//                elevation = CardDefaults.cardElevation(2.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(80.dp)
//            ) {
//                AsyncImage(
//                    model = "https://i.pinimg.com/736x/97/29/ba/9729ba4cdd70c69539f3aec7f6dc7331.jpg",
//                    contentDescription = "Category Poster",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//
//            Text(
//                modifier = Modifier
//                    .height(30.dp)
//                    .fillMaxWidth(),
//                maxLines = 1,
//                fontSize = 9.sp,
//                fontWeight = FontWeight.SemiBold,
//                text = "Apparel & Kitchen",
//                overflow = TextOverflow.Ellipsis,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}
//
//@Composable
//@Preview
//fun Preview() {
//    FinzoHomeScreen()
//}
