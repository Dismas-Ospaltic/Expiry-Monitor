package com.d12.expirymonitor.repository




import com.d12.expirymonitor.data.localData.ItemDao
import com.d12.expirymonitor.model.ItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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