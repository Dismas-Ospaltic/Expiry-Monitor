package com.d12.expirymonitor.navigationGraph



import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.*
import androidx.compose.ui.Modifier
import com.d12.expirymonitor.ui_screens.AddProductScreen
import com.d12.expirymonitor.ui_screens.HomeScreen
import com.d12.expirymonitor.ui_screens.ProductOverViewScreen
import com.d12.expirymonitor.ui_screens.SettingScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings")

//    object EditProduct : Screen("editProduct/{itemId}") {
//        fun createRoute(itemId: String) = "editProduct/$itemId"
//    }

    object AddProduct : Screen("addProduct")


    object  OverView : Screen("overView")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier) {


    AnimatedNavHost(
        navController,
        startDestination = Screen.Home.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() }
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Settings.route) { SettingScreen(navController) }
        composable(Screen.AddProduct.route) { AddProductScreen(navController) }
        composable(Screen.OverView.route) { ProductOverViewScreen(navController) }

//        composable(Screen.EditProduct.route) { backStackEntry ->
//            val itemId = backStackEntry.arguments?.getString("itemId") ?: "Unknown"
//            EditProductScreen(navController, itemId)
//        }







    }
}