//package com.st11.mydebts.screens
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.hoverable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import kotlinx.coroutines.launch
//import com.st11.mydebts.R  // Importing resources
//import com.st11.mydebts.model.DebtEntity
//import compose.icons.FontAwesomeIcons
//import compose.icons.fontawesomeicons.Regular
//import compose.icons.fontawesomeicons.Solid
//import com.st11.mydebts.screens.components.AddDebtPopUpScreen
//import com.st11.mydebts.screens.components.EditDebtPopUpScreen
//import com.st11.mydebts.screens.components.PayAllPopupScreen
//import com.st11.mydebts.screens.components.PaymentBox
//import com.st11.mydebts.utils.DynamicStatusBar
//import com.st11.mydebts.utils.ShimmerBox
//import com.st11.mydebts.viewmodel.CurrencyViewModel
//import com.st11.mydebts.viewmodel.DebtViewModel
//import com.st11.mydebts.viewmodel.PeopleViewModel
//import compose.icons.fontawesomeicons.solid.Clipboard
//import compose.icons.fontawesomeicons.solid.DollarSign
//import compose.icons.fontawesomeicons.solid.FileExcel
//import compose.icons.fontawesomeicons.solid.Inbox
//import compose.icons.fontawesomeicons.solid.PenSquare
//import compose.icons.fontawesomeicons.solid.PlusCircle
//import org.koin.androidx.compose.koinViewModel
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DetailScreen(navController: NavController, itemId: String?,peopleViewModel: PeopleViewModel = koinViewModel()) {
//
//    val backgroundColor = colorResource(id = R.color.dark)
//
//    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings
//
//
//    // Fetch user details if itemId is not null
//    LaunchedEffect(itemId) {
//        itemId?.let { peopleViewModel.getPersonDetails(it) }
//    }
//
//
//    val debtViewModel: DebtViewModel = koinViewModel()
//    val debts by debtViewModel.debts.collectAsState()
//
//    val currencyViewModel: CurrencyViewModel = koinViewModel()
//    val currency by currencyViewModel.userData.collectAsState()
//
//    val isLoading by debtViewModel.isLoading.collectAsState()
//    val isPersonDetailsLoading by peopleViewModel.isPersonDetailsLoading.collectAsState()
//
//
//
//    LaunchedEffect(Unit) {
//        if (itemId != null) {
//            debtViewModel.getAllDebts(userId = itemId.toString())
//        }  // Pass actual userId
//    }
//
//    // Observe the person state
//    val person = peopleViewModel.personState.collectAsState().value
//
//
//    val tabs = listOf("All", "Pending", "Partial", "Paid")
//    val pagerState = rememberPagerState(pageCount = { tabs.size })
//    val coroutineScope = rememberCoroutineScope()
//    val interactionSource = remember { MutableInteractionSource() }
//    var isHovered by remember { mutableStateOf(false) }
//
//    val buttonColor = colorResource(id = R.color.teal_700) // Button color
//    val textColor = colorResource(id = R.color.white) // Text color
//
//    var showDialog by remember { mutableStateOf(false) } // State to control popup visibility
//    var showAddDebtDialog by remember { mutableStateOf(false) } // State to control popup visibility
//    var showEditDialog by remember{ mutableStateOf(false) }
//
//
//    val paymentItems by remember {
//        derivedStateOf {
//            debts.map { debt ->
//                DebtEntity(
//                    date = debt.date,
//                    dueDate = debt.dueDate,
//                    amount = debt.amount,
//                    amountPaid = debt.amountPaid,
//                    amountRem = debt.amountRem,
//                    debtId = debt.debtId,
//                    description = debt.description,
//                    uid = debt.uid,
//                    timestamp = debt.timestamp,
//                    status = debt.status
//                )
//            }
//        }
//    }
//
//
//
//
//
//    val buttons = listOf(
//        ButtonItem(
//            label = "Add Debt",
//            icon = {
//                Icon(
//                    imageVector = FontAwesomeIcons.Solid.PlusCircle,
//                    contentDescription = "add icon",
//                    tint = Color.White,
//                    modifier = Modifier.size(24.dp)
//                )
//            },
//            onClick = { showAddDebtDialog = true }
//        ),
//        ButtonItem(
//            label = "Pay All",
//            icon = {
//                Icon(
//                    imageVector = FontAwesomeIcons.Solid.DollarSign,
//                    contentDescription = "dollar icon",
//                    tint = Color.White,
//                    modifier = Modifier.size(24.dp)
//                )
//            },
//            onClick = { showDialog = true  }
//        )
//
////        ButtonItem("Add Debt", R.drawable.ic_money) { showAddDebtDialog = true },
////        ButtonItem("Pay All", R.drawable.ic_money) { showDialog = true  },
//////        ButtonItem("Show History", R.drawable.ic_clipboard) { navController.navigate(Screen.DebtHistDetail.createRoute(itemId.toString())) },
//    )
//    val columns = if (LocalConfiguration.current.screenWidthDp < 600) 2 else 3 // Responsive grid
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text("Client Details", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) }, // - Item $itemId
//                navigationIcon = {
//                    IconButton(
//                        onClick = { navController.popBackStack() },
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(50))
//                            .background(if (isHovered) Color.Gray.copy(alpha = 0.3f) else Color.Transparent)
//                            .hoverable(interactionSource = interactionSource, enabled = true)
//                            .pointerInput(Unit) {
//                                detectTapGestures(
//                                    onPress = {
//                                        isHovered = true
//                                        tryAwaitRelease()
//                                        isHovered = false
//                                    }
//                                )
//                            }
//                    ) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color.White
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = colorResource(id = R.color.dark),
//                    navigationIconContentColor = Color.White,
//                    titleContentColor = Color.White
//                )
//            )
//        }
//    ) { paddingValues ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .verticalScroll(rememberScrollState()) // ✅ Enable scrolling
//                .background(colorResource(id = R.color.white)) // ✅ Apply background color
//        ) {
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(250.dp)
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.oblicationdebt),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.matchParentSize()
//                )
//
//                Box(
//                    modifier = Modifier
//                        .matchParentSize()
//                        .background(
//                            Brush.verticalGradient(
//                                colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent),
//                                startY = 0f
//                            )
//                        )
//                )
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp)
//                        .align(Alignment.BottomStart)
//                ) {
//
//                    if(isPersonDetailsLoading){
//                        LazyColumn(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(horizontal = 12.dp, vertical = 8.dp)
//                        ) {
//                            items(6) {
//                                ShimmerBox(
//                                    modifier = Modifier
//                                        .padding(5.dp)
//                                        .fillMaxWidth()
//                                        .height(12.dp) // Set a fixed height or use another layout ,
//                                )
//                            }
//                        }
//                    }else {
//                        if (person != null) {
//                            Text(
//                                text = person.firstName + " " + person.lastName,
//                                fontSize = 24.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.White
//                            )
//                            Text(
//                                text = person.email,
//                                fontSize = 16.sp,
//                                color = Color.White.copy(alpha = 0.8f)
//                            )
//                            Text(
//                                text = person.phone,
//                                fontSize = 16.sp,
//                                color = Color.White.copy(alpha = 0.8f)
//                            )
//                            Text(
//                                text = person.address.takeIf { it.isNotBlank() } ?: "address...",
//                                fontSize = 16.sp,
//                                color = Color.White.copy(alpha = 0.8f)
//                            )
//                            Text(
//                                text = person.businessName.takeIf { it.isNotBlank() }
//                                    ?: "Business...",
//                                fontSize = 16.sp,
//                                color = Color.White.copy(alpha = 0.8f)
//                            )
//
//                            Row(
//                                modifier = Modifier
////                                    .fillMaxWidth()
//                                    .padding(16.dp),
//                                horizontalArrangement = Arrangement.Start
//                            ) {
//                                Button(
//                                    onClick = { showEditDialog = true },
//                                    modifier = Modifier
//                                        .padding(horizontal = 8.dp),
//                                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_200)),
//                                    shape = RoundedCornerShape(12.dp)
//                                ) {
////
//                                    Icon(
//                                        imageVector = FontAwesomeIcons.Solid.PenSquare,
//                                        contentDescription ="edit icon",
//                                        tint = colorResource(id = R.color.white),
//                                        modifier = Modifier.size(24.dp)
//                                    )
//                                    Spacer(modifier = Modifier.width(8.dp))
//                                    Text(text = "Edit Details", color = colorResource(id = R.color.white))
//
//
//                                }
//
////                                OutlinedButton(
////                                    onClick = {},
////                                    modifier = Modifier
////                                        .padding(horizontal = 8.dp),
////                                    shape = RoundedCornerShape(12.dp),
////                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
////                                ) {
////                                    Column(
////                                        horizontalAlignment = Alignment.CenterHorizontally,
////                                        verticalArrangement = Arrangement.Center
////                                    ) {
////                                    Icon(
////                                        imageVector = FontAwesomeIcons.Solid.Inbox,
////                                        contentDescription = "archive Icon",
////                                        tint = colorResource(id = R.color.dark),
////                                        modifier = Modifier.size(24.dp)
////                                    )
////                                    Spacer(modifier = Modifier.width(8.dp))
////                                    Text("Archive")
////                                }
////                                    }
//                            }
//
//                        } else {
////                         Show a loading indicator or message if data is null
//                            Box(
//                                modifier = Modifier.fillMaxSize(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = "Loading user details...",
//                                    color = Color.Gray,
//                                    fontSize = 16.sp
//                                )
//
//                            }
//                        }
//                    }
//
//
//                    ///
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(0.dp)
//                    .background(colorResource(id = R.color.dark), shape = RoundedCornerShape(4.dp)) // Use your background color
//            ) {
//
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between rows
//                ) {
//                    Text(text = "Manage Debts",
//                        color = colorResource(id = R.color.white),
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .padding(8.dp)
//                    )
//
//                    buttons.chunked(columns).forEach { rowButtons ->
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between buttons
//                        ) {
//                            rowButtons.forEach { button ->
//                                Box(
//                                    modifier = Modifier
//                                        .weight(1f) // Distribute buttons evenly
//                                        .padding(8.dp)
//                                ) {
////                                    CustomButton(button, buttonColor, textColor)// Pass your button data
//                                    CustomOutlinedButton(button, buttonColor, textColor)
//                                }
//                            }
//
//                            // Fill remaining space if the last row has fewer buttons
//                            repeat(columns - rowButtons.size) {
//                                Spacer(modifier = Modifier.weight(1f))
//                            }
//                        }
//                    }
//                }
//            }
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(700.dp) // Fixed height for the entire view
//                    .padding(16.dp)
//            ) {
//                Text(text = "All Debt History",
//                    color = colorResource(id = R.color.dark),
//                    fontWeight = FontWeight.Bold
//                )
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
//                }
//
//
//                if (isLoading) {
////                    LazyVerticalGrid(
////                        columns = GridCells.Fixed(columns),
////                        modifier = Modifier
////                            .fillMaxSize()
////                            .padding(horizontal = 12.dp, vertical = 8.dp)
////                    ) {
////                        items(6) {
////                            ShimmerBox(
////                                modifier = Modifier
////                                    .padding(8.dp)
////                                    .fillMaxWidth()
////                                    .aspectRatio(1f)
////                            )
////                        }
////                    }
//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(horizontal = 12.dp, vertical = 8.dp)
//                    ) {
//                        items(6) {
//                            ShimmerBox(
//                                modifier = Modifier
//                                    .padding(8.dp)
//                                    .fillMaxWidth()
//                                    .height(100.dp) // Set a fixed height or use another layout
//                            )
//                        }
//                    }
//                } else {
//                    // Scrollable content
//                    Box(
//                        modifier = Modifier
//                            .weight(1f) // Makes this section take the available space
//                            .fillMaxSize()
//                    ) {
//                        HorizontalPager(
//                            state = pagerState,
//                            modifier = Modifier.fillMaxSize()
//                        ) { page ->
//                            val filteredItems = when (page) {
//                                0 -> paymentItems // "All" tab shows all payments
//                                1 -> paymentItems.filter {
//                                    it.status.equals(
//                                        "Pending",
//                                        ignoreCase = true
//                                    )
//                                }
//
//                                2 -> paymentItems.filter {
//                                    it.status.equals(
//                                        "Partial",
//                                        ignoreCase = true
//                                    )
//                                }
//
//                                3 -> paymentItems.filter {
//                                    it.status.equals(
//                                        "Paid",
//                                        ignoreCase = true
//                                    )
//                                }
//
//                                else -> emptyList()
//                            }
//
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .verticalScroll(rememberScrollState()) // Enables scrolling
//                            ) {
//                                if (filteredItems.isEmpty()) {
//                                    // Centered text when no data is found
////                                    Box(
////                                        modifier = Modifier.fillMaxSize(),
////                                        contentAlignment = Alignment.Center
////                                    ) {
////                                        Text(
////                                            text = "No data found",
////                                            color = Color.Gray,
////                                            fontSize = 18.sp,
////                                            fontWeight = FontWeight.Medium
////                                        )
////                                    }
//
//                                    Box(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .heightIn(min = 200.dp), // Guarantees at least 200dp height
//                                        contentAlignment = Alignment.Center
//                                    ) {
//                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                                            Icon(
//                                                imageVector = FontAwesomeIcons.Solid.Clipboard,
//                                                contentDescription = "No data",
//                                                tint = Color.Gray,
//                                                modifier = Modifier.size(64.dp)
//                                            )
//                                            Spacer(modifier = Modifier.height(8.dp))
//                                            Text(
//                                                text = "No data found",
//                                                color = Color.Gray,
//                                                fontSize = 18.sp,
//                                                fontWeight = FontWeight.Medium
//                                            )
//                                        }
//                                    }
//                                } else {
//                                    filteredItems.forEach { item ->
//                                        PaymentBox(
//                                            navController = navController,
//                                            debt = item
//                                        ) // Display each payment dynamically
//                                    }
//
//                                }
//                            }
//                        }
//                    }
//                }
//
//
//                ////debts
//            }
//
//        }
//    }
//
//    // Show the Payment Popup if showDialog is true
//    if (showDialog) {
//        PayAllPopupScreen(onDismiss = { showDialog = false } , itemId.toString() )
//    }
//
//    // Show the Payment Popup if showDialog is true
//    if (showAddDebtDialog) {
////        AddDebtPopUpScreen(onDismiss = { showAddDebtDialog = false })
//        AddDebtPopUpScreen(itemId = itemId) { showAddDebtDialog = false }
//    }
//
//    if (showEditDialog) {
////        AddDebtPopUpScreen(onDismiss = { showAddDebtDialog = false })
//        EditDebtPopUpScreen(itemId = itemId, onDismiss = { showEditDialog = false })
//    }
//
//
//}
//
//
//@Composable
//fun CustomOutlinedButton(
//    button: ButtonItem,
//    buttonColor: Color,
//    textColor: Color
//) {
//    OutlinedButton(
//        onClick = button.onClick,
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth()
//            .wrapContentHeight(),
//        shape = RoundedCornerShape(16.dp),
//        border = BorderStroke(1.5.dp, buttonColor),
//        colors = ButtonDefaults.outlinedButtonColors(
//            containerColor = Color.Transparent,
//            contentColor = textColor
//        )
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.padding(vertical = 12.dp)
//        ) {
//            button.icon() // 👈 directly invoke the composable icon
//            Spacer(modifier = Modifier.height(6.dp))
//            Text(
//                text = button.label,
//                color = textColor,
//                fontWeight = FontWeight.SemiBold,
//                style = MaterialTheme.typography.labelLarge
//            )
//        }
//    }
//}
//
////@Composable
////fun CustomButton(button: ButtonItem, buttonColor: Color, textColor: Color) {
////    Button(
////        onClick = button.onClick,
////        modifier = Modifier
////            .padding(4.dp)
////            .fillMaxWidth()
////            .wrapContentHeight(),
////        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
////        shape = RoundedCornerShape(4.dp)
////    ) {
////        Column(
////            horizontalAlignment = Alignment.CenterHorizontally,
////            verticalArrangement = Arrangement.Center
////        ) {
////            Icon(
////                painter = painterResource(id = button.icon),
////                contentDescription = button.label,
////                tint = Color.White,
////                modifier = Modifier.size(32.dp)
////            )
////            Spacer(modifier = Modifier.height(8.dp))
////            Text(text = button.label, color = textColor, fontWeight = FontWeight.Bold)
////        }
////    }
////}
//
////data class ButtonItem(val label: String, val icon: Int, val onClick: () -> Unit)
//data class ButtonItem(
//    val label: String,
//    val icon: @Composable () -> Unit,
//    val onClick: () -> Unit
//)
//
//
//
//@Preview(showBackground = true)
//@Composable
//fun DetailScreenPreview() {
//    DetailScreen(navController = rememberNavController(), itemId = "2")
//}