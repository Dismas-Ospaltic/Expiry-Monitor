package com.d12.expirymonitor.repository




import com.d12.expirymonitor.data.localData.ItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ItemRepository(private val itemDao: ItemDao) {

    //    val allDebt: Flow<List<DebtEntity>> = debtDao.getAllDebt(userId)
    fun getAllWishlist(): Flow<List<WishlistEntity>> = wishDao.getAllWishlist()


    suspend fun insertWish(wish: WishlistEntity) {
        wishDao.insertWish(wish)
    }

    suspend fun updateWish(wish: WishlistEntity) {
        wishDao.updateWish(wish)
    }


    fun getAllTotalWishlist(): Flow<Int> {
        return wishDao.getAllTotalWishlist()
            .map { total -> total ?: 0 }  // Convert NULL to 0.0
    }


    fun getAllTotalWishlistAmount(): Flow<Float> {
        return wishDao.getAllTotalWishlistAmount()
            .map { total -> total ?: 0.0f }  // Convert NULL to 0.0
    }


    fun getAllTotalWishlistPendingAmount(): Flow<Float> {
        return wishDao.getAllTotalWishlistPendingAmount()
            .map { total -> total ?: 0.0f }  // Convert NULL to 0.0
    }

    fun getAllTotalWishlistPurchasedAmount(): Flow<Float> {
        return wishDao.getAllTotalWishlistPurchasedAmount()
            .map { total -> total ?: 0.0f }  // Convert NULL to 0.0
    }



    suspend fun updateWishStatus(wishId: String, newStatus: String, itemPurchaseDate: String): Boolean {
        val rowsUpdated = wishDao.updateWishStatus(wishId, newStatus, itemPurchaseDate) ?: 0
        return rowsUpdated > 0
    }







}