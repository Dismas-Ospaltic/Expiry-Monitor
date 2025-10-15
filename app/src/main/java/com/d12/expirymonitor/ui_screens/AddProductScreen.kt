package com.d12.expirymonitor.ui_screens


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.d12.expirymonitor.R
import com.d12.expirymonitor.model.ItemEntity
import com.d12.expirymonitor.ui_screens.ui_components.DatePickerField
import com.d12.expirymonitor.utils.StatusBarDynamicColor
import com.d12.expirymonitor.utils.copyImageToInternalStorage
import com.d12.expirymonitor.utils.formatTimeFromTimestamp
import com.d12.expirymonitor.utils.outlinedFieldColors
import com.d12.expirymonitor.utils.saveBitmapToInternalStorage
import com.d12.expirymonitor.viewmodel.ItemViewModel
import com.d12.expirymonitor.viewmodel.LocalNotificationPrefsViewModel
import com.d12.expirymonitor.viewmodel.NotificationViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.AngleLeft
import compose.icons.fontawesomeicons.solid.Camera
import compose.icons.fontawesomeicons.solid.Cog
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.time.LocalDateTime
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController) {
    val context = LocalContext.current
    val itemViewModel: ItemViewModel = koinViewModel()
    val backgroundColor = colorResource(id = R.color.raisin_black)


    val timestamp: Long = System.currentTimeMillis()
    val formattedTime = formatTimeFromTimestamp(timestamp)


    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var savedImagePath by remember { mutableStateOf<String?>(null) }

    val localNotificationPrefsViewModel: LocalNotificationPrefsViewModel = koinViewModel()
    val userNotificationData by localNotificationPrefsViewModel.userNotificationData.collectAsState()
    val viewModel: NotificationViewModel = koinViewModel()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            selectedImageUri = it
            savedImagePath = copyImageToInternalStorage(context, it)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = saveBitmapToInternalStorage(context, it)
            savedImagePath = uri
            Toast.makeText(context, "Photo captured!", Toast.LENGTH_SHORT).show()
        }
    }

    var productName by remember { mutableStateOf("") }
    var productCode by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Select Category") }
    var manufactureDate by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val categories = listOf("Food", "Medicine", "Cosmetic", "Beverage")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Product/Item", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.AngleLeft,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.raisin_black)
                )
            )
        },
        containerColor = colorResource(id = R.color.light_bg_color)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // 🖼 Image Picker
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(id = R.color.feldgrau).copy(alpha = 0.1f))
                    .clickable {
                        galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                contentAlignment = Alignment.Center
            ) {
                if (savedImagePath != null) {
                    Image(
                        painter = rememberAsyncImagePainter(File(savedImagePath!!)),
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {

                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Camera,
                        contentDescription = "Pick image",
                        tint = colorResource(id = R.color.aquamarine),
                        modifier = Modifier.size(48.dp)
                    )

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🧭 Category Dropdown
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    singleLine = true,
                    colors = outlinedFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                category = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🧾 Other Fields
            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Item name *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = outlinedFieldColors()
            )

            OutlinedTextField(
                value = productCode,
                onValueChange = { productCode = it },
                label = { Text("Item code") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = outlinedFieldColors()
            )

            DatePickerField("Manufacture Date (YYYY-MM-DD)", manufactureDate) {
                manufactureDate = it
            }

            DatePickerField("Expiry Date (YYYY-MM-DD)", expiryDate) {
                expiryDate = it
            }


            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("quantity") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                    focusedContainerColor = Color.White.copy(alpha = 0.95f),
                    focusedBorderColor = backgroundColor,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = backgroundColor,
                    cursorColor = backgroundColor
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
                    .verticalScroll(rememberScrollState()),
                singleLine = false,
                maxLines = 4,
                colors = outlinedFieldColors()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (productName.isEmpty() || category == "Select Category") {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    itemViewModel.insertItem(
                        ItemEntity(
                            itemName = productName,
                            itemPhoto = savedImagePath ?: "",
                            itemCode = productCode,
                            itemCategory = category,
                            itemDescription = description,
                            itemQuantity = quantity.toIntOrNull() ?: 0,
                            manufactureDate = manufactureDate,
                            expiryDate = expiryDate,
                            itemId = generateSixDigitRandomNumber().toString()
                        )
                    )


                    if(userNotificationData.isNotificationEnabled){
                        // 2. 🛡️ Now, attempt the risky notification scheduling inside a try-catch block.
                        try {
                            val (year, month, day) = expiryDate.split("-").map { it.toInt() }
                            val (hour, minute) = formattedTime.split(":").map { it.toInt() }

                            val dateTime = LocalDateTime.of(year, month, day, hour, minute)

                            val granted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) == PackageManager.PERMISSION_GRANTED
                            } else true

                            if (granted) {
                                viewModel.scheduleUserNotification(
                                    context = context,
                                    title = "Expired Product! reminder",
                                    message = "Your $productName category $category expires on $expiryDate please consider removing it from Your Storage! ",
                                    year = year,
                                    month = month,
                                    day = day,
                                    hour = hour,
                                    minute = minute
                                )


                            }

                        } catch (e: Exception) {
                            // If parsing fails, log the error instead of crashing!
                            Log.e("AddProductScreen", "Failed to parse date/time and schedule notification.", e)
                            // Optionally, inform the user with a Toast
                            Toast.makeText(context, "Product saved, but failed to set reminder. please Contact app developer to report a bug", Toast.LENGTH_LONG).show()
                        }
                    }


                    Toast.makeText(context, "Product added successfully!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.aquamarine)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Add Item", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductScreenPreview() {
    AddProductScreen(navController = rememberNavController())
}


fun generateSixDigitRandomNumber(): Int {
    return Random.nextInt(1000000, 1000000000)  // Generates a random number between 100000 (inclusive) and 1000000 (exclusive)
}

