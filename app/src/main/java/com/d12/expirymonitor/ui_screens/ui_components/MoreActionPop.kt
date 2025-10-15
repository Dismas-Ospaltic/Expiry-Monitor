package com.d12.expirymonitor.ui_screens.ui_components


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.d12.expirymonitor.R
import com.d12.expirymonitor.viewmodel.ItemViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.TrashAlt
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreActionPop(
    onDismiss: () -> Unit,
    itemId: String,
    itemPhotoPath: String?
) {
    val itemViewModel: ItemViewModel = koinViewModel()
    val backgroundColor = colorResource(id = R.color.aquamarine)

    val context = LocalContext.current

    var showDeleteDialog by remember { mutableStateOf(false) }


    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .imePadding()
                    .verticalScroll(rememberScrollState()), // Adjust for keyboard
                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {
                Text(text = "Actions", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                // Delete Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showDeleteDialog = true
                        }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = FontAwesomeIcons.Regular.TrashAlt,
                        contentDescription = "Delete",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Delete", fontSize = 16.sp)
                }



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onDismiss()
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.aquamarine), // Green background
                            contentColor = Color.White          // White text
                        )
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }




//    // AlertDialog logic
//    if (showDeleteDialog) {
//        AlertDialog(
//            onDismissRequest = { showDeleteDialog = false },
//            title = { Text("Delete Item") },
//            text = { Text("Delete Item permanently From List?") },
//            confirmButton = {
//                TextButton(onClick = {
//                    itemViewModel.deleteItemById(itemId)
//                    onDismiss()
//
//                    Toast.makeText(
//                        context,
//                        "Item Delete",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    showDeleteDialog = false
//                }) {
//                    Text(
//                        text = "Delete",
//                        color = colorResource(id = R.color.aquamarine)
//                    )
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showDeleteDialog = false;
//                    onDismiss()
//                }) {
//                    Text(
//                        text = "Cancel",
//                        color = colorResource(id = R.color.gray01)
//                    )
//                }
//            }
//        )
//    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Item") },
            text = { Text("Delete Item permanently from the list and remove its image?") },
            confirmButton = {
                TextButton(onClick = {
                    itemViewModel.deleteItemById(context, itemId, itemPhotoPath)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                    showDeleteDialog = false
                    onDismiss()
                }) {
                    Text("Delete", color = colorResource(id = R.color.aquamarine))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    onDismiss()
                }) {
                    Text("Cancel", color = colorResource(id = R.color.gray01))
                }
            }
        )
    }


}