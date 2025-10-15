package com.d12.expirymonitor.ui_screens.ui_components


import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
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

    // Convert string (from Room) to usable Uri
    val imageModel = remember(photoUrl) {
        when {
            photoUrl.isNullOrEmpty() -> null
            photoUrl.startsWith("content://") -> Uri.parse(photoUrl)
            else -> photoUrl
        }
    }

    // 🧮 Adjust message: If expired, show "X days passed"
    val daysMessage = if (isExpired) {
        val daysPassed = kotlin.math.abs(daysRemaining)
        "$daysPassed days passed since expiry"
    } else {
        "$daysRemaining days left"
    }

    // 🎨 Card color changes if expired
    val cardBackgroundColor = if (isExpired) Color(0xFFFFEBEE) else Color.White
    val tagColor = if (isExpired) Color(0xFFD32F2F) else colorResource(id = R.color.mint)

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
            colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    // 🖼 Product Image
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

                        Column {
                            Text(
                                text = "Manufactured: $manufactureDate",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "Expires: $expiryDate",
                                fontSize = 12.sp,
                                color = if (isExpired) Color.Red else colorResource(id = R.color.aquamarine),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // 🏷 Days remaining or passed tag
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(tagColor)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                                .align(Alignment.End)
                        ) {
                            Text(
                                text = daysMessage,
                                fontSize = 12.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                if (description.isNotEmpty()) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        thickness = DividerDefaults.Thickness,
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

        // 🔴 or 🟢 status indicator
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
//    val context = LocalContext.current
//
//    // 🧠 Convert the stored string (Room) into a usable Uri if needed
//    val imageModel = remember(photoUrl) {
//        when {
//            photoUrl.isNullOrEmpty() -> null
//            photoUrl.startsWith("content://") -> Uri.parse(photoUrl)
//            else -> photoUrl // network URL
//        }
//    }
//
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
//                    // 🖼 Product Image with placeholder fallback
//                    AsyncImage(
//                        model = imageModel,
//                        contentDescription = "Product Image",
//                        modifier = Modifier
//                            .size(80.dp)
//                            .clip(RoundedCornerShape(12.dp))
//                            .background(Color.LightGray.copy(alpha = 0.2f)),
//                        contentScale = ContentScale.Crop,
//                        placeholder = painterResource(id = R.drawable.image_pa_24),
//                        error = painterResource(id = R.drawable.image_pa_24)
//                    )
//
//                    Spacer(modifier = Modifier.width(12.dp))
//
//                    Column(modifier = Modifier.weight(1f)) {
//                        Text(
//                            text = name,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = colorResource(id = R.color.raisin_black)
//                        )
//
//                        Text(
//                            text = category.uppercase(),
//                            fontSize = 12.sp,
//                            color = colorResource(id = R.color.gray01)
//                        )
//
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                        Text(
//                            text = "Quantity: $quantity",
//                            fontSize = 14.sp,
//                            color = colorResource(id = R.color.feldgrau)
//                        )
//
//                        Spacer(modifier = Modifier.height(6.dp))
//
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
//
//                        }
//                        Spacer(modifier = Modifier.height(4.dp))
//
//                        Box(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(20.dp))
//                                .background(colorResource(id = R.color.mint))
//                                .padding(horizontal = 10.dp, vertical = 4.dp)
//                                .align(Alignment.End)
//                        ) {
//                            Text(
//                                text = "$daysRemaining days left",
//                                fontSize = 12.sp,
//                                color = Color.White,
//                                fontWeight = FontWeight.Medium
//                            )
//                        }
//                    }
//                }
//
//                if (description.isNotEmpty()) {
//                    HorizontalDivider(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 8.dp, vertical = 8.dp),
//                        thickness = DividerDefaults.Thickness, color = colorResource(id = R.color.gray01).copy(alpha = 0.2f)
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
//        // 🟢 or 🔴 status dot
//        Box(
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .offset(x = (-4).dp, y = 4.dp)
//                .size(14.dp)
//                .clip(CircleShape)
//                .background(if (isExpired) Color.Red else Color(0xFF4CAF50))
//                .border(1.dp, Color.White, CircleShape)
//        )
//    }
//}

