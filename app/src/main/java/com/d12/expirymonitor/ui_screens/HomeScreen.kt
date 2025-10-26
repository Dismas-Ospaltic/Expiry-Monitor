package com.d12.expirymonitor.ui_screens


import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalLayoutDirection
import com.d12.expirymonitor.utils.StatusBarDynamicColor
import com.d12.expirymonitor.R
import com.d12.expirymonitor.ui_screens.ui_components.MoreActionPop
import com.d12.expirymonitor.ui_screens.ui_components.ProductCard
import com.d12.expirymonitor.utils.calculateDaysRemaining
import com.d12.expirymonitor.utils.requestNotificationPermission
import com.d12.expirymonitor.viewmodel.ItemViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Search
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen(navController: NavController) {
//
//    val backgroundColor = colorResource(id = R.color.raisin_black)
//    StatusBarDynamicColor(backgroundColor)
//
//    val context = LocalContext.current
//    val activity = context as? Activity
//
//    val itemViewModel: ItemViewModel = koinViewModel()
//    val items by itemViewModel.items.collectAsState()
//
//    var searchQuery by remember { mutableStateOf("") }
//
//    val tabs = listOf("All", "Unexpired", "Expired")
//    val pagerState = rememberPagerState(pageCount = { tabs.size })
//    val coroutineScope = rememberCoroutineScope()
//    val interactionSource = remember { MutableInteractionSource() }
//    var isHovered by remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (activity != null) {
//                requestNotificationPermission(activity)
//            }
//        }
//
//
//    }
//
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("My Products/Items", color = Color.White) },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = backgroundColor,
//                    titleContentColor = Color.White,
//                    navigationIconContentColor = Color.White
//                )
//            )
//        }
//    ) { paddingValues ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(
//                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
//                    top = paddingValues.calculateTopPadding(),
//                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
//                    bottom = paddingValues.calculateBottomPadding() + 80.dp
//                )
//                .verticalScroll(rememberScrollState())
//                .background(colorResource(id = R.color.light_bg_color))
//        ) {
//            Spacer(modifier = Modifier.height(10.dp))
//
//
//            // Search Field
//            TextField(
//                value = searchQuery,
//                onValueChange = { searchQuery = it },
//                placeholder = { Text(text = "Search...") },
//                leadingIcon = {
//
//                    Icon(
//                        imageVector = FontAwesomeIcons.Solid.Search,
//                        contentDescription = "Search Icon",
//                        tint = Color.Gray,
//                        modifier = Modifier.size(24.dp)
//                    )
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .background(Color.White),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    disabledContainerColor = Color.Transparent,
//                    cursorColor = Color.Black,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                singleLine = true
//            )
////            // 🔍 Search Field
////            TextField(
////                value = searchQuery,
////                onValueChange = { searchQuery = it },
////                placeholder = { Text("Search by name or category...") },
////                leadingIcon = {
////                    Icon(
////                        imageVector = FontAwesomeIcons.Solid.Search,
////                        contentDescription = "Search Icon",
////                        tint = Color.Gray
////                    )
////                },
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .padding(horizontal = 16.dp)
////                    .clip(RoundedCornerShape(12.dp))
////                    .background(Color.White),
////                colors = TextFieldDefaults.colors(
////                    focusedContainerColor = Color.Transparent,
////                    unfocusedContainerColor = Color.Transparent,
////                    disabledContainerColor = Color.Transparent,
////                    cursorColor = Color.Black,
////                    focusedIndicatorColor = Color.Transparent,
////                    unfocusedIndicatorColor = Color.Transparent
////                ),
////                singleLine = true
////            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(700.dp) // Fixed height for the entire view
//                    .padding(16.dp)
//            ) {
//                // Tabs should remain fixed at the top
//                ScrollableTabRow(
//                    selectedTabIndex = pagerState.currentPage,
//                    edgePadding = 12.dp,
//                    modifier = Modifier
//                        .padding(vertical = 6.dp)
//                        .clip(RoundedCornerShape(20.dp)),
//                    contentColor = Color.White,
//                    divider = {},
//                    indicator = {}
//                ) {
//
//                    tabs.forEachIndexed { index, title ->
//                        Tab(
//                            selected = pagerState.currentPage == index,
//                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
//                            modifier = Modifier
//                                .padding(horizontal = 4.dp)
//                                .clip(RoundedCornerShape(12.dp))
//                                .background(
//                                    if (pagerState.currentPage == index)
//                                        colorResource(id = R.color.tabSelected)
//                                    else
//                                        colorResource(id = R.color.tabUnselected)
//                                )
//                        ) {
//                            Text(
//                                text = title,
//                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
//                                color = if (pagerState.currentPage == index) Color.White else Color.Gray
//                            )
//                        }
//                    }
//
//
//
//                }
//
//                // Scrollable content
//                Box(
//                    modifier = Modifier
//                        .weight(1f) // Makes this section take the available space
//                        .fillMaxSize()
//                ) {
//                    HorizontalPager(
//                        state = pagerState,
//                        modifier = Modifier.fillMaxSize()
//                    ) { page ->
//                        val filteredItems = when (page) {
//                            0 -> paymentItems // "All" tab shows items
//                            1 -> paymentItems.filter {
//                                it.status.equals(
//                                    "Unexpired",
//                                    ignoreCase = true
//                                )
//                            }
//
//                            2 -> paymentItems.filter {
//                                it.status.equals(
//                                    "Expired",
//                                    ignoreCase = true
//                                )
//                            }
//
//
//                            else -> emptyList()
//                        }
//
//
//
//
//                    }
//
//
//                }
//
//
//
//
//
//
//            }
//
//
//
//
//            // 🔎 Filter items based on search query
//            val filteredItems = items.filter {
//                it.itemName.contains(searchQuery, ignoreCase = true) ||
//                        it.itemCategory.contains(searchQuery, ignoreCase = true)
//            }
//
//            if (filteredItems.isEmpty()) {
//                // 🕳️ Show message when no data
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(top = 100.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "No data available",
//                        color = colorResource(id = R.color.gray01),
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//            } else {
//                // 🧾 Show Product Cards from Room data
//                filteredItems.forEach { item ->
//
//                    // Calculate expiry status
//                    val daysRemaining = calculateDaysRemaining(item.expiryDate)
//                    val isExpired = daysRemaining <= 0
//
//                    ProductCard(
//                        photoUrl = item.itemPhoto,
//                        name = item.itemName,
//                        category = item.itemCategory,
//                        quantity = item.itemQuantity,
//                        manufactureDate = item.manufactureDate,
//                        expiryDate = item.expiryDate,
//                        description = item.itemDescription ?: "No description available",
//                        daysRemaining = daysRemaining,
//                        isExpired = isExpired,
//                        onClick = {
//                            // Open detail or edit
//
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
//
//
//
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(navController = rememberNavController())
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.raisin_black)
    StatusBarDynamicColor(backgroundColor)

    val context = LocalContext.current
    val activity = context as? Activity

    val itemViewModel: ItemViewModel = koinViewModel()
    val items by itemViewModel.items.collectAsState()
    var showActionDialog by remember { mutableStateOf(false) }
    var selectedItemId by remember { mutableStateOf<String?>(null) }
    var selectedItemImagePath by remember { mutableStateOf<String?>(null) }

    var searchQuery by remember { mutableStateOf("") }

    val tabs = listOf("All", "Unexpired", "Expired")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    // Request notification permission (Android 13+)
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity?.let { requestNotificationPermission(it) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Products / Items", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding() + 80.dp
                )
                .background(colorResource(id = R.color.light_bg_color))
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // 🔍 Search Field
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(text = "Search by name or category...") },
                leadingIcon = {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🗂 Tabs Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 12.dp,
                    containerColor = Color.Transparent,
                    divider = {},
                    indicator = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        val selected = pagerState.currentPage == index
                        Tab(
                            selected = selected,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (selected)
                                        colorResource(id = R.color.tabSelected)
                                    else
                                        colorResource(id = R.color.tabUnselected)
                                )
                        ) {
                            Text(
                                text = title,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                color = if (selected) Color.White else Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // 📜 Horizontal Pager for Tab Content
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->

                    val filteredItems = when (page) {
                        0 -> items // All
                        1 -> items.filter { calculateDaysRemaining(it.expiryDate) > 0 } // Unexpired
                        2 -> items.filter { calculateDaysRemaining(it.expiryDate) <= 0 } // Expired
                        else -> emptyList()
                    }.filter {
                        it.itemName.contains(searchQuery, ignoreCase = true) ||
                                it.itemCategory.contains(searchQuery, ignoreCase = true)
                    }

                    if (filteredItems.isEmpty()) {
                        // 🕳 No items found
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No items available",
                                color = colorResource(id = R.color.gray01),
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        // 🧾 Show cards
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = 80.dp)
                        ) {
                            filteredItems.forEach { item ->
                                val daysRemaining = calculateDaysRemaining(item.expiryDate)
                                val isExpired = daysRemaining <= 0

                                ProductCard(
                                    photoUrl = item.itemPhoto,
                                    name = item.itemName,
                                    category = item.itemCategory,
                                    quantity = item.itemQuantity,
                                    manufactureDate = item.manufactureDate,
                                    expiryDate = item.expiryDate,
                                    description = item.itemDescription ?: "No description available",
                                    daysRemaining = daysRemaining,
                                    isExpired = isExpired,
                                    onClick = { /* Navigate to details */
                                    selectedItemId = item.itemId
                                     selectedItemImagePath = item.itemPhoto
                                      showActionDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showActionDialog) {
            MoreActionPop(
                           navController,
                onDismiss = {  showActionDialog = false },
                            itemId = selectedItemId.toString(),
                            itemPhotoPath = selectedItemImagePath

            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}

