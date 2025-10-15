package com.d12.expirymonitor.repository




import android.content.Context
import android.util.Log
import com.d12.expirymonitor.data.localData.ItemDao
import com.d12.expirymonitor.model.ItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ItemRepository(private val itemDao: ItemDao) {


    fun getAllItems(): Flow<List<ItemEntity>> = itemDao.getAllItems()

    suspend fun getAllItemsOnce(): List<ItemEntity> {
        return itemDao.getAllItemsOnce() // You’ll add this DAO method below
    }


    suspend fun insertItem(item: ItemEntity) {
       itemDao.insertItem(item)
    }

    suspend fun updateItem(item: ItemEntity) {
        itemDao.updateItem(item)
    }


    suspend fun updateItemById(
        itemName: String,
        itemPhoto: String,
        itemCode: String,
        itemCategory: String,
        itemDescription: String,
        itemQuantity: Int,
        manufactureDate: String,
        expiryDate: String,
        itemId: String
    ): Boolean {
        val rowsUpdated = itemDao.updateItemById(
            itemName,
            itemPhoto,
            itemCode,
            itemCategory,
            itemDescription,
            itemQuantity,
            manufactureDate,
            expiryDate,
            itemId
        ) ?: 0
        return rowsUpdated > 0
    }

//    suspend fun markAsNotified(itemId: String) = itemDao.markAsNotified(itemId)


    // 🟣 Get expired product count
    suspend fun getExpiredProductsCount(): Int {
        val today = getTodayDate()
        return itemDao.getExpiredProductsCount(today)
    }


//    // Delete item by ID
//    suspend fun deleteItemById(itemId: String): Boolean {
//        val rowsDeleted = itemDao.deleteItemById(itemId)
//        return rowsDeleted > 0
//    }

    // ✅ Delete item and its associated image file
    suspend fun deleteItemById(context: Context, itemId: String, itemPhotoPath: String?) {
        withContext(Dispatchers.IO) {
            try {
                // Delete database record
                itemDao.deleteItemById(itemId)

                // Delete the image file from internal storage if it exists
                itemPhotoPath?.let {
                    val file = File(it)
                    if (file.exists()) {
                        val deleted = file.delete()
                        Log.d("ItemRepository", "Image file deleted: $deleted")
                    }
                }
            } catch (e: Exception) {
                Log.e("ItemRepository", "Error deleting item or image", e)
            }
        }
    }


    // 🟣 Get unexpired product count
    suspend fun getUnexpiredProductsCount(): Int {
        val today = getTodayDate()
        return itemDao.getUnexpiredProductsCount(today)
    }

    // 🕒 Utility to get today’s date in "yyyy-MM-dd" format
    private fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}