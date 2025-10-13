//package com.example.app.ui.screens
//
//import android.Manifest
//import android.net.Uri
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.rememberAsyncImagePainter
//import com.example.app.R
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddProductScreen() {
//    val context = LocalContext.current
//
//    // Image picker (modern Photo Picker – no permissions required)
//    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
//
//    val galleryLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia()
//    ) { uri ->
//        selectedImageUri = uri
//    }
//
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicturePreview()
//    ) { bitmap ->
//        if (bitmap != null) {
//            Toast.makeText(context, "Photo captured!", Toast.LENGTH_SHORT).show()
//            // You can convert bitmap to Uri or save it if needed
//        }
//    }
//
//    // State variables
//    var productName by remember { mutableStateOf(TextFieldValue("")) }
//    var category by remember { mutableStateOf("Select Category") }
//    var manufactureDate by remember { mutableStateOf(TextFieldValue("")) }
//    var expiryDate by remember { mutableStateOf(TextFieldValue("")) }
//    var quantity by remember { mutableStateOf(TextFieldValue("")) }
//    var description by remember { mutableStateOf(TextFieldValue("")) }
//
//    val categories = listOf("Food", "Medicine", "Cosmetic", "Beverage")
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//        Text(
//            text = "Add Product",
//            fontSize = 22.sp,
//            fontWeight = FontWeight.Bold,
//            color = colorResource(id = R.color.raisin_black)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Image picker box
//        Box(
//            modifier = Modifier
//                .size(140.dp)
//                .align(Alignment.CenterHorizontally)
//                .clip(CircleShape)
//                .background(colorResource(id = R.color.feldgrau).copy(alpha = 0.1f))
//                .clickable {
//                    galleryLauncher.launch(
//                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//                    )
//                },
//            contentAlignment = Alignment.Center
//        ) {
//            if (selectedImageUri != null) {
//                Image(
//                    painter = rememberAsyncImagePainter(selectedImageUri),
//                    contentDescription = "Selected image",
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop
//                )
//            } else {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_camera),
//                    contentDescription = "Pick image",
//                    tint = colorResource(id = R.color.aquamarine),
//                    modifier = Modifier.size(48.dp)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Category dropdown
//        var expanded by remember { mutableStateOf(false) }
//
//        ExposedDropdownMenuBox(
//            expanded = expanded,
//            onExpandedChange = { expanded = !expanded }
//        ) {
//            TextField(
//                value = category,
//                onValueChange = {},
//                readOnly = true,
//                label = { Text("Category") },
//                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                colors = TextFieldDefaults.textFieldColors(
//                    containerColor = Color.White
//                ),
//                modifier = Modifier
//                    .menuAnchor() // For backward compatibility (safe)
//                    .fillMaxWidth()
//                    .exposedDropdownSize() // ✅ new method to size dropdown properly
//            )
//
//            ExposedDropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false }
//            ) {
//                categories.forEach { item ->
//                    DropdownMenuItem(
//                        text = { Text(item) },
//                        onClick = {
//                            category = item
//                            expanded = false
//                        }
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // Other fields
//        CustomTextField("Product Name", productName) { productName = it }
//        CustomTextField("Manufacture Date (YYYY-MM-DD)", manufactureDate) { manufactureDate = it }
//        CustomTextField("Expiry Date (YYYY-MM-DD)", expiryDate) { expiryDate = it }
//        CustomTextField("Quantity", quantity) { quantity = it }
//        CustomTextField("Description", description, singleLine = false) { description = it }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = {
//                Toast.makeText(context, "Product added!", Toast.LENGTH_SHORT).show()
//            },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = colorResource(id = R.color.aquamarine)
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Text(
//                text = "Add Product",
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }
//}
//
//@Composable
//fun CustomTextField(
//    label: String,
//    value: TextFieldValue,
//    singleLine: Boolean = true,
//    onValueChange: (TextFieldValue) -> Unit
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label) },
//        singleLine = singleLine,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 6.dp)
//    )
//}
