package com.d12.expirymonitor.ui_screens.ui_components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d12.expirymonitor.R
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.AngleRight

@Composable
fun SettingLink(
    icon: ImageVector,
    title: String,
    iconColor: Color = colorResource(id = R.color.raisin_black),
    borderColor: Color = colorResource(id = R.color.raisin_black), // optional border color
    trailing: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = 4.dp),
        // 🧱 Sharp corners (0.dp)
        shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),

        // ⚪ White background, no elevation
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),

        // 🔲 2dp outline border
        border = BorderStroke(2.dp, borderColor),

        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Title text
            Text(
                text = title,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )

            // Trailing (arrow or custom)
            trailing?.invoke() ?: Icon(
                imageVector = FontAwesomeIcons.Solid.AngleRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
