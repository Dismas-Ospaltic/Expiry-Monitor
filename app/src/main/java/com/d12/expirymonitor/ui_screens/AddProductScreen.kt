package com.d12.expirymonitor.ui_screens


import android.content.Intent
import android.net.Uri
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.d12.expirymonitor.R
import com.d12.expirymonitor.model.ItemEntity
import com.d12.expirymonitor.ui_screens.ui_components.DatePickerField
import com.d12.expirymonitor.utils.StatusBarDynamicColor
import com.d12.expirymonitor.viewmodel.ItemViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.AngleLeft
import compose.icons.fontawesomeicons.solid.Camera
import compose.icons.fontawesomeicons.solid.Cog
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.raisin_black)
    StatusBarDynamicColor(backgroundColor) // ✅ Keeps status bar consistent
    val context = LocalContext.current
    val itemViewModel: ItemViewModel = koinViewModel()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Product", color = Color.White) },
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
                    containerColor = backgroundColor
                )
            )
        },
        containerColor = colorResource(id = R.color.light_bg_color)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()) // ✅ Scrollable content
        ) {
            val context = LocalContext.current

            // Image picker (modern Photo Picker – no permissions required)
            var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

            val galleryLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia()
            ) { uri ->
                selectedImageUri = uri
            }

            val cameraLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.TakePicturePreview()
            ) { bitmap ->
                if (bitmap != null) {
                    Toast.makeText(context, "Photo captured!", Toast.LENGTH_SHORT).show()
                    // You can convert bitmap to Uri or save it if needed
                }
            }

            // State variables
            var productName by remember { mutableStateOf("") }
            var productCode by remember { mutableStateOf("") }
            var category by remember { mutableStateOf("Select Category") }
            var manufactureDate by remember { mutableStateOf("") }
            var expiryDate by remember { mutableStateOf("") }
            var quantity by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }

            val categories = listOf("Food", "Medicine", "Cosmetic", "Beverage")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
//                    .verticalScroll(rememberScrollState())
            ) {
//                Text(
//                    text = "Add Product",
//                    fontSize = 22.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = colorResource(id = R.color.raisin_black)
//                )

                Spacer(modifier = Modifier.height(16.dp))

                // Image picker box
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .align(Alignment.CenterHorizontally)
//                        .clip(CircleShape)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(id = R.color.feldgrau).copy(alpha = 0.1f))
                        .clickable {
                            galleryLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "Selected image",
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

                Spacer(modifier = Modifier.height(24.dp))

                // Category dropdown
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
//                        .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                            focusedContainerColor = Color.White.copy(alpha = 0.95f),
                            focusedBorderColor = backgroundColor,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = backgroundColor,
                            cursorColor = backgroundColor
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(Color.White) // ✅ White background for the dropdown menu
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

                // Other fields
//                CustomTextField("Product Name", productName) { productName = it }

                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName  = it },
                    label = { Text("Item name *") },
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
                )


                OutlinedTextField(
                    value = productCode,
                    onValueChange = { productCode  = it },
                    label = { Text("Item code") },
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
                )


                DatePickerField(
                    label = "Manufacture Date (YYYY-MM-DD)",
                    value = manufactureDate, // <-- This is from your parent state
                    onDateSelected = { date -> manufactureDate = date }
                )


                DatePickerField(
                    label = "Expiry Date (YYYY-MM-DD)",
                    value = expiryDate, // <-- This is from your parent state
                    onDateSelected = { date -> expiryDate = date }
                )


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
                    label = { Text("description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = 200.dp) // Adjust height for ~4 lines
                        .verticalScroll(rememberScrollState()),

                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = backgroundColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = backgroundColor,
                        cursorColor = backgroundColor
                    ),
                    singleLine = false,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {


                        if (category.isEmpty()) {
                            Toast.makeText(context, "Please enter item category", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        if (productName.isEmpty()) {
                            Toast.makeText(context, "Please enter item name", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }



                        if (productCode.isEmpty()) {
                            Toast.makeText(context, "Please enter item code", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        if (manufactureDate.isEmpty()) {
                            Toast.makeText(context, "Please enter item manufacture Date", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        if (expiryDate.isEmpty()) {
                            Toast.makeText(context, "Please enter item expiry Date", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                    itemViewModel.insertItem(
                        ItemEntity(
                           itemName = productName,
                            itemPhoto = selectedImageUri?.toString() ?: "",
                            itemCode = productCode,
                            itemCategory = category,
                            itemDescription = description,
                            itemQuantity = quantity.toInt(),
                            manufactureDate = manufactureDate,
                            expiryDate = expiryDate,
                            itemId = generateSixDigitRandomNumber().toString()
                        )
                    )




//                        Toast.makeText(context, "Product added!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.aquamarine)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Add Product",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
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

