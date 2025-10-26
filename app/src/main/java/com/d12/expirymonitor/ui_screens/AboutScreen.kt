package com.d12.expirymonitor.ui_screens



import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.d12.expirymonitor.R
import com.d12.expirymonitor.viewmodel.ItemViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.AngleLeft
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    val itemViewModel: ItemViewModel = koinViewModel()
    val backgroundColor = colorResource(id = R.color.raisin_black)


    val versionName = try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        // This should not happen, but it's good practice to have a fallback
        e.printStackTrace()
        "1.0.0"
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("About", color = Color.White) },
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


            // 🔹 App Title & Version
            Text(
                text = "Expiry Monitor",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.raisin_black)
            )

            Text(
                text = "Version $versionName",
                fontSize = 14.sp,
                color = Color.Gray
            )
//            Text(
//                text = "Version 1.0.0",
//                fontSize = 14.sp,
//                color = Color.Gray
//            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Description Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(2.dp, colorResource(id = R.color.raisin_black)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Expiry Monitor allows you to monitor and manage product expiry dates efficiently, notifying you when products expire to help reduce wastage.",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // 🔹 Developer Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(2.dp, colorResource(id = R.color.raisin_black)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Developed by",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "D12 Labs",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.raisin_black)
                    )

                    Text(
                        text = "support@d12labs.com",
                        color = colorResource(id = R.color.feldgrau),
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:support@d12labs.com")
                            }
                            context.startActivity(Intent.createChooser(intent, "Send Email"))
                        }
                    )
//                    Text(
//                        text = "support@d12labs.com",
//                        color = colorResource(id = R.color.feldgrau),
//                        fontSize = 14.sp
//                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Legal Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(onClick = { /* TODO: Navigate to Privacy Policy */ }) {
                    Text(
                        text = "Privacy Policy",
                        color = colorResource(id = R.color.raisin_black),
                        fontWeight = FontWeight.Medium
                    )
                }

                TextButton(onClick = { /* TODO: Navigate to Terms & Conditions */ }) {
                    Text(
                        text = "Terms & Conditions",
                        color = colorResource(id = R.color.raisin_black),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // 🔹 Intellectual Property & Attribution

            Text(
                text = "Intellectual Property Notice",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = """

Third-party assets (such as icons and animations) are the property of their respective owners and are used in accordance with their free to use licenses.

D12 Labs makes no claim of ownership over any third-party assets utilized within this application. All trademarks, logos, and brand names are the property of their respective owners.
            """.trimIndent(),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )



        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenScreenPreview() {
    AboutScreen(navController = rememberNavController())
}




