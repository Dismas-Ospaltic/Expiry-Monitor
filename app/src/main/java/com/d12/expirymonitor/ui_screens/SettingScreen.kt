package com.d12.expirymonitor.ui_screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalLayoutDirection
import com.d12.expirymonitor.utils.StatusBarDynamicColor
import com.d12.expirymonitor.R
import com.d12.expirymonitor.ui_screens.ui_components.SettingLink
import com.d12.expirymonitor.viewmodel.LocalNotificationPrefsViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Bell
import compose.icons.fontawesomeicons.solid.InfoCircle
import org.koin.androidx.compose.koinViewModel
import androidx.core.net.toUri
import com.d12.expirymonitor.navigationGraph.Screen
import compose.icons.fontawesomeicons.solid.Lock


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.raisin_black)
//    StatusBarDynamicColor(backgroundColor)

    val context = LocalContext.current

    val localNotificationPrefsViewModel: LocalNotificationPrefsViewModel = koinViewModel()
    val userNotificationData by localNotificationPrefsViewModel.userNotificationData.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Setting", color = Color.White) },
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
            var notificationsEnabled by remember { mutableStateOf(userNotificationData.isNotificationEnabled) }

            LaunchedEffect(userNotificationData.isNotificationEnabled) {
                notificationsEnabled = userNotificationData.isNotificationEnabled
            }



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current) + 12.dp,
                        end = paddingValues.calculateEndPadding(LocalLayoutDirection.current) + 12.dp,

                    )
            ) {

                SettingLink(
                    icon = FontAwesomeIcons.Solid.InfoCircle,
                    title = "About",
                    iconColor = colorResource(id = R.color.raisin_black),
                    onClick = { /* Navigate */

                       navController.navigate(Screen.AboutApp.route)
                    }
                )


                SettingLink(
                    icon = FontAwesomeIcons.Solid.Lock,
                    title = "Privacy policy",
                    iconColor = colorResource(id = R.color.dark_purple),
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            "https://policy/privacy-policy.html".toUri()
                        )
                        context.startActivity(intent)
                        /* Navigate */
                    }
                )

            SettingLink(
                icon = FontAwesomeIcons.Solid.Bell,
                title = "Enable Notifications",
                iconColor = colorResource(id = R.color.aquamarine),
                trailing = {
                    Switch(
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colorResource(id = R.color.mint),
                            checkedTrackColor = colorResource(id = R.color.mint).copy(alpha = 0.5f),
                            uncheckedThumbColor = Color.LightGray,
                            uncheckedTrackColor = Color.Gray
                        ),
                        checked = notificationsEnabled,
                        onCheckedChange = { enabled ->
                            notificationsEnabled = enabled
                            if (enabled) {
                                localNotificationPrefsViewModel.saveUserData()
                            } else {
                                localNotificationPrefsViewModel.clearUserData()
                            }
                        }

                    )
                },
                onClick = {
                    if (notificationsEnabled) {
                        // Currently enabled → turn OFF and clear data
                        notificationsEnabled = false
                        localNotificationPrefsViewModel.clearUserData()
                    } else {
                        // Currently disabled → turn ON and save data
                        notificationsEnabled = true
                        localNotificationPrefsViewModel.saveUserData()
                    }
                }

            )



        }

    }
}


}






@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen(navController = rememberNavController())
}