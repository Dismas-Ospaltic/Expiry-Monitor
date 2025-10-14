package com.d12.expirymonitor.utils
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

import androidx.compose.runtime.Composable
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.d12.expirymonitor.R

fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): String? {
    return try {
        val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun outlinedFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
    focusedContainerColor = Color.White.copy(alpha = 0.95f),
    focusedBorderColor = colorResource(id = R.color.raisin_black),
    unfocusedBorderColor = Color.Gray,
    focusedLabelColor = colorResource(id = R.color.raisin_black),
    cursorColor = colorResource(id = R.color.raisin_black)
)
