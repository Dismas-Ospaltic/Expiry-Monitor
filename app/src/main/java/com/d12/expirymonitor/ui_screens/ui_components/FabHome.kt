package com.d12.expirymonitor.ui_screens.ui_components


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.d12.expirymonitor.R
import com.d12.expirymonitor.navigationGraph.Screen
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Plus


@Composable
fun FabHome(navController: NavController) {
    val primaryColor = colorResource(id = R.color.aquamarine)
    val whiteColor = colorResource(id = R.color.white)

    FloatingActionButton(
        onClick = {
            navController.navigate(Screen.AddProduct.route)
            /* Handle click */ },
        containerColor = primaryColor,
        shape = CircleShape, // ✅ Ensures the FAB is a perfect circle
        modifier = Modifier
            .padding(bottom = 32.dp, end = 16.dp)
            .size(56.dp) // ✅ Standard FAB size (Material Design)
            .zIndex(1f)
    ) {
        Icon(
            imageVector = FontAwesomeIcons.Solid.Plus,
            contentDescription = "Add icon",
            tint = whiteColor,
            modifier = Modifier.size(24.dp) // ✅ Icon size
        )
    }
}



@Preview(showBackground = true)
@Composable
fun FabHomePreview() {
    FabHome(navController = rememberNavController())
}