package com.d12.expirymonitor.repository




import com.d12.expirymonitor.data.localData.ItemDao
import com.d12.expirymonitor.model.ItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ItemRepository(private val itemDao: ItemDao) {


    fun getAllItems(): Flow<List<ItemEntity>> = itemDao.getAllItems()


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



}