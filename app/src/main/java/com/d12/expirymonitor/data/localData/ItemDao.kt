package com.d12.expirymonitor.data.localData


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.d12.expirymonitor.model.ItemEntity
import com.d12.expirymonitor.utils.dateFormat
import kotlinx.coroutines.flow.Flow

//This interface defines the database operations.
@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Query("SELECT * FROM items ORDER BY timestamp DESC")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items")
    suspend fun getAllItemsOnce(): List<ItemEntity>  // One-time fetch for ViewModel check


    @Query("UPDATE items SET itemName = :itemName , " +
            " itemPhoto = :itemPhoto ,itemCode =:itemCode," +
            " itemCategory =:itemCategory,itemDescription=:itemDescription," +
            "itemQuantity=:itemQuantity, manufactureDate=:manufactureDate, " +
            "expiryDate=:expiryDate  WHERE itemId = :itemId")
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
    ): Int?


    // 🔹 Count expired products (expiry date before today)
    @Query("SELECT COUNT(*) FROM items WHERE expiryDate < :today")
    suspend fun getExpiredProductsCount(today: String): Int

    // 🔹 Count unexpired products (expiry date today or later)
    @Query("SELECT COUNT(*) FROM items WHERE expiryDate >= :today")
    suspend fun getUnexpiredProductsCount(today: String): Int

//    @Query("UPDATE items SET notified = 1 WHERE itemId = :itemId")
//    suspend fun markAsNotified(itemId: String)


}