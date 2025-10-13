package com.d12.expirymonitor.ui_screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextOverflow
import com.d12.expirymonitor.utils.StatusBarDynamicColor
import com.d12.expirymonitor.R
import com.d12.expirymonitor.ui_screens.ui_components.ProductCard
import com.d12.expirymonitor.utils.calculateDaysRemaining
import com.d12.expirymonitor.viewmodel.ItemViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CalendarDay
import compose.icons.fontawesomeicons.solid.Search
import org.koin.androidx.compose.koinViewModel


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen(navController: NavController) {
//
//    val backgroundColor = colorResource(id = R.color.raisin_black)
//    StatusBarDynamicColor(backgroundColor)
//
//    val itemViewModel: ItemViewModel = koinViewModel()
//    val items by itemViewModel.items.collectAsState()
//
//
//
//
//    // 🧺 Sample list of products
//    val productList = listOf(
//        Product(
//            photoUrl = "https://images.unsplash.com/photo-1585238342029-3b4b1a3f1d4d",
//            name = "Canned Tuna",
//            category = "Food",
//            quantity = 10,
//            manufactureDate = "2024-02-01",
//            expiryDate = "2025-02-01",
//            description = "Premium canned tuna rich in protein and omega-3.",
//            daysRemaining = 120,
//            isExpired = false
//        ),
//        Product(
//            photoUrl = "https://images.unsplash.com/photo-1600093463592-3b5de9d3ec1a",
//            name = "Pain Relief Tablets",
//            category = "Medicine",
//            quantity = 2,
//            manufactureDate = "2023-12-01",
//            expiryDate = "2024-09-01",
//            description = "Fast-acting tablets for pain relief and fever reduction.",
//            daysRemaining = 0,
//            isExpired = true
//        ),
//        Product(
//            photoUrl = "https://images.unsplash.com/photo-1576402187873-66e4b6fcaf4e",
//            name = "Chocolate Cookies",
//            category = "Snacks",
//            quantity = 5,
//            manufactureDate = "2024-05-15",
//            expiryDate = "2025-01-15",
//            description = "Crispy chocolate chip cookies made with real butter.",
//            daysRemaining = 90,
//            isExpired = false
//        )
//    )
//
//    val searchQuery = remember { mutableStateOf("") }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("My Products", color = Color.White) },
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
////                 paddingValues
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
//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                contentAlignment = Alignment.Center
//            ) {
//
//
//            }
//
//            // Search Field
//            TextField(
//                value = searchQuery.value,
//                onValueChange = { searchQuery.value = it },
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
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//
//            // 🧾 List of Product Cards
//            for (product in productList) {
//                ProductCard(
//                    photoUrl = product.photoUrl,
//                    name = product.name,
//                    category = product.category,
//                    quantity = product.quantity,
//                    manufactureDate = product.manufactureDate,
//                    expiryDate = product.expiryDate,
//                    description = product.description,
//                    daysRemaining = product.daysRemaining,
//                    isExpired = product.isExpired,
//                    onClick = {
//                        // handle click
//                    }
//                )
//            }
//
//
//        }
//
//    }
//
//
//
//}
//
//
//data class Product(
//    val photoUrl: String,
//    val name: String,
//    val category: String,
//    val quantity: Int,
//    val manufactureDate: String,
//    val expiryDate: String,
//    val description: String,
//    val daysRemaining: Int,
//    val isExpired: Boolean
//)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.raisin_black)
    StatusBarDynamicColor(backgroundColor)

    val itemViewModel: ItemViewModel = koinViewModel()
    val items by itemViewModel.items.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Products", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
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
                .verticalScroll(rememberScrollState())
                .background(colorResource(id = R.color.light_bg_color))
        ) {
            Spacer(modifier = Modifier.height(10.dp))


            // Search Field
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(text = "Search...") },
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
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )
//            // 🔍 Search Field
//            TextField(
//                value = searchQuery,
//                onValueChange = { searchQuery = it },
//                placeholder = { Text("Search by name or category...") },
//                leadingIcon = {
//                    Icon(
//                        imageVector = FontAwesomeIcons.Solid.Search,
//                        contentDescription = "Search Icon",
//                        tint = Color.Gray
//                    )
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
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

            Spacer(modifier = Modifier.height(16.dp))

            // 🔎 Filter items based on search query
            val filteredItems = items.filter {
                it.itemName.contains(searchQuery, ignoreCase = true) ||
                        it.itemCategory.contains(searchQuery, ignoreCase = true)
            }

            if (filteredItems.isEmpty()) {
                // 🕳️ Show message when no data
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No data available",
                        color = colorResource(id = R.color.gray01),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                // 🧾 Show Product Cards from Room data
                filteredItems.forEach { item ->

                    // Calculate expiry status
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
                        onClick = {
                            // Open detail or edit
                            navController.navigate("edit/${item.itemId}")
                        }
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}


