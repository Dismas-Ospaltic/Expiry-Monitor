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
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CalendarDay
import compose.icons.fontawesomeicons.solid.Search


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.raisin_black)
    StatusBarDynamicColor(backgroundColor)

    // 🧺 Sample list of products
    val productList = listOf(
        Product(
            photoUrl = "https://images.unsplash.com/photo-1585238342029-3b4b1a3f1d4d",
            name = "Canned Tuna",
            category = "Food",
            quantity = 10,
            manufactureDate = "2024-02-01",
            expiryDate = "2025-02-01",
            description = "Premium canned tuna rich in protein and omega-3.",
            daysRemaining = 120,
            isExpired = false
        ),
        Product(
            photoUrl = "https://images.unsplash.com/photo-1600093463592-3b5de9d3ec1a",
            name = "Pain Relief Tablets",
            category = "Medicine",
            quantity = 2,
            manufactureDate = "2023-12-01",
            expiryDate = "2024-09-01",
            description = "Fast-acting tablets for pain relief and fever reduction.",
            daysRemaining = 0,
            isExpired = true
        ),
        Product(
            photoUrl = "https://images.unsplash.com/photo-1576402187873-66e4b6fcaf4e",
            name = "Chocolate Cookies",
            category = "Snacks",
            quantity = 5,
            manufactureDate = "2024-05-15",
            expiryDate = "2025-01-15",
            description = "Crispy chocolate chip cookies made with real butter.",
            daysRemaining = 90,
            isExpired = false
        )
    )

    val searchQuery = remember { mutableStateOf("") }

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
//                 paddingValues
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding() + 80.dp
                )
                .verticalScroll(rememberScrollState())
                .background(colorResource(id = R.color.light_bg_color))
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {


            }

            // Search Field
            TextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
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

            Spacer(modifier = Modifier.height(16.dp))

//                    repeat(10) { index ->
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 12.dp, vertical = 8.dp)
//                            .clickable { /* Handle click */ },
//                        shape = RoundedCornerShape(4.dp),
//                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//                        colors = CardDefaults.cardColors(containerColor = Color.White)
//                    ) {
//                        Column(modifier = Modifier.padding(16.dp)) {
//
//                            // Top Row: Amount & Category
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Column {
//                                    Text(
//                                        text = "category: food",
//                                        fontSize = 14.sp,
//                                        fontWeight = FontWeight.SemiBold,
//                                        color = colorResource(id = R.color.gray01)
//                                    )
//                                    Spacer(modifier = Modifier.height(4.dp))
//                                    Text(
//                                        text =  "2024-01-01",
//                                        fontSize = 12.sp,
//                                        color = Color.Gray
//                                    )
//                                }
//
//                                Text(
//                                    text = "20",
//                                    fontSize = 22.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = colorResource(id = R.color.aquamarine)
//                                )
//                            }
//
//                            Spacer(modifier = Modifier.height(12.dp))
//
//                            // Description
//                            Text(
//                                text = "grinded meat can",
//                                fontSize = 16.sp,
//                                color = colorResource(id = R.color.raisin_black),
//                                maxLines = 2,
//                                overflow = TextOverflow.Ellipsis
//                            )
//
//                            Spacer(modifier = Modifier.height(8.dp))
//
//                            // Bottom Row: Payment mode with icon
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//                                Icon(
//                                    imageVector = FontAwesomeIcons.Solid.CalendarDay,
//                                    contentDescription = "calendar",
//                                    tint = colorResource(id = R.color.aquamarine),
//                                    modifier = Modifier.size(18.dp)
//                                )
//                                Spacer(modifier = Modifier.width(6.dp))
//                                Text(
//                                    text = "days remaining: 20",
//                                    fontSize = 14.sp,
//                                    color = colorResource(id = R.color.gray01)
//                                )
//                            }
//                        }
//                    }
//
//                }

            // 🧾 List of Product Cards
            for (product in productList) {
                ProductCard(
                    photoUrl = product.photoUrl,
                    name = product.name,
                    category = product.category,
                    quantity = product.quantity,
                    manufactureDate = product.manufactureDate,
                    expiryDate = product.expiryDate,
                    description = product.description,
                    daysRemaining = product.daysRemaining,
                    isExpired = product.isExpired,
                    onClick = {
                        // handle click
                    }
                )
            }


        }

    }



}






@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}


data class Product(
    val photoUrl: String,
    val name: String,
    val category: String,
    val quantity: Int,
    val manufactureDate: String,
    val expiryDate: String,
    val description: String,
    val daysRemaining: Int,
    val isExpired: Boolean
)