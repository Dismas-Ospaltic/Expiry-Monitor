package com.d12.expirymonitor.ui_screens.ui_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.d12.expirymonitor.R
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CalendarDay
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.view.ContextThemeWrapper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import java.util.Locale


@Composable
fun DatePickerField(
    label: String,
    value: String, // <-- Pass in the current date from parent
    onDateSelected: (String) -> Unit
) {
    val backgroundColor = colorResource(id = R.color.aquamarine)
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    fun showDatePicker() {

        DatePickerDialog(
//            context,
            ContextThemeWrapper(context, R.style.CustomDatePickerDialog), // <-- Apply custom style
            { _, year, month, day ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                onDateSelected(dateFormat.format(selectedDate.time)) // Send result to parent
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    OutlinedTextField(
        value = value, // <-- Show parent’s date here
        onValueChange = {},
        label = { Text(label) },
        trailingIcon = {
            Icon(
                imageVector = FontAwesomeIcons.Solid.CalendarDay,
                contentDescription = "date",
                tint = backgroundColor,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showDatePicker() }
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
            focusedContainerColor = Color.White.copy(alpha = 0.95f),
            focusedBorderColor = backgroundColor,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = backgroundColor,
            cursorColor = backgroundColor
        )
    )
}


