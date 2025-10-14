package com.d12.expirymonitor.ui_screens.ui_components


import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.d12.expirymonitor.R

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProductCard(
//    photoUrl: String?,
//    name: String,
//    category: String,
//    quantity: Int,
//    manufactureDate: String,
//    expiryDate: String,
//    description: String,
//    daysRemaining: Int,
//    isExpired: Boolean,
//    onClick: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 12.dp, vertical = 8.dp)
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { onClick() },
//            shape = RoundedCornerShape(12.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//            colors = CardDefaults.cardColors(containerColor = Color.White)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(12.dp)
//            ) {
//                Row(modifier = Modifier.fillMaxWidth()) {
//
//                    // Product Image
////                    AsyncImage(
////                        model = photoUrl,
////                        contentDescription = name,
////                        contentScale = ContentScale.Crop,
////                        modifier = Modifier
////                            .size(90.dp)
////                            .clip(RoundedCornerShape(10.dp))
////                    )
//
//                    AsyncImage(
//                        model = photoUrl ?: "",
////                        model = photoUrl,
//                        contentDescription = "Product Image",
//                        modifier = Modifier
//                            .size(80.dp)
//                            .clip(RoundedCornerShape(12.dp))
//                            .background(Color.LightGray.copy(alpha = 0.2f)),
//                        contentScale = ContentScale.Crop,
//                        error = painterResource(id = R.drawable.image_pa_24),
//                        placeholder = painterResource(id = R.drawable.image_pa_24)
//                    )
//
//
//                    Spacer(modifier = Modifier.width(12.dp))
//
//                    Column(modifier = Modifier.weight(1f)) {
//                        // Product Name
//                        Text(
//                            text = name + photoUrl,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = colorResource(id = R.color.raisin_black)
//                        )
//
//                        // Category
//                        Text(
//                            text = category.uppercase(),
//                            fontSize = 12.sp,
//                            color = colorResource(id = R.color.gray01)
//                        )
//
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                        // Quantity
//                        Text(
//                            text = "Quantity: $quantity",
//                            fontSize = 14.sp,
//                            color = colorResource(id = R.color.feldgrau)
//                        )
//
//                        Spacer(modifier = Modifier.height(6.dp))
//
//                        // Manufacture & Expiry Dates
//                        Row(
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Column {
//                                Text(
//                                    text = "Manufactured: $manufactureDate",
//                                    fontSize = 12.sp,
//                                    color = Color.Gray
//                                )
//                                Text(
//                                    text = "Expires: $expiryDate",
//                                    fontSize = 12.sp,
//                                    color = colorResource(id = R.color.aquamarine),
//                                    fontWeight = FontWeight.SemiBold
//                                )
//                            }
//
//                            // Days remaining badge
//                            Box(
//                                modifier = Modifier
//                                    .clip(RoundedCornerShape(20.dp))
//                                    .background(colorResource(id = R.color.mint))
//                                    .padding(horizontal = 10.dp, vertical = 4.dp)
//                                    .align(Alignment.CenterVertically)
//                            ) {
//                                Text(
//                                    text = "$daysRemaining days left",
//                                    fontSize = 12.sp,
//                                    color = Color.White,
//                                    fontWeight = FontWeight.Medium
//                                )
//                            }
//                        }
//                    }
//                }
//
//                // Description Section
//                if (description.isNotEmpty()) {
//                    Divider(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 8.dp, vertical = 8.dp),
//                        color = colorResource(id = R.color.gray01).copy(alpha = 0.2f)
//                    )
//                    Text(
//                        text = description,
//                        fontSize = 14.sp,
//                        color = colorResource(id = R.color.gray01),
//                        maxLines = 3,
//                        overflow = TextOverflow.Ellipsis,
//                        modifier = Modifier.padding(horizontal = 8.dp)
//                    )
//                }
//            }
//        }
//
//        // Top-right status dot (expired indicator)
//        Box(
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .offset(x = (-4).dp, y = 4.dp)
//                .size(14.dp)
//                .clip(CircleShape)
//                .background(
//                    if (isExpired) Color.Red
//                    else Color(0xFF4CAF50) // green
//                )
//                .border(
//                    width = 1.dp,
//                    color = Color.White,
//                    shape = CircleShape
//                )
//        )
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    photoUrl: String?,
    name: String,
    category: String,
    quantity: Int,
    manufactureDate: String,
    expiryDate: String,
    description: String,
    daysRemaining: Int,
    isExpired: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    // 🧠 Convert the stored string (Room) into a usable Uri if needed
    val imageModel = remember(photoUrl) {
        when {
            photoUrl.isNullOrEmpty() -> null
            photoUrl.startsWith("content://") -> Uri.parse(photoUrl)
            else -> photoUrl // network URL
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {

                    // 🖼 Product Image with placeholder fallback
                    AsyncImage(
                        model = imageModel,
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray.copy(alpha = 0.2f)),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.image_pa_24),
                        error = painterResource(id = R.drawable.image_pa_24)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.raisin_black)
                        )

                        Text(
                            text = category.uppercase(),
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.gray01)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Quantity: $quantity",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.feldgrau)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = "Manufactured: $manufactureDate",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Expires: $expiryDate",
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.aquamarine),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(colorResource(id = R.color.mint))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = "$daysRemaining days left",
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                if (description.isNotEmpty()) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        color = colorResource(id = R.color.gray01).copy(alpha = 0.2f)
                    )
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.gray01),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }

        // 🟢 or 🔴 status dot
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-4).dp, y = 4.dp)
                .size(14.dp)
                .clip(CircleShape)
                .background(if (isExpired) Color.Red else Color(0xFF4CAF50))
                .border(1.dp, Color.White, CircleShape)
        )
    }
}

