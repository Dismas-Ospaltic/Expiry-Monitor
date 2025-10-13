package com.d12.expirymonitor.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.d12.expirymonitor.utils.dateFormat


@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String = dateFormat(System.currentTimeMillis()), //DD-MM-YYYY
    val itemName: String,
    val itemPhoto: String,
    val itemCode: String? = null,
    val itemCategory: String,
    val itemDescription: String? = null,
    val itemQuantity: Int = 1,
    val manufactureDate: String,
    val expiryDate: String,
    val itemId: String,
    val timestamp: Long = System.currentTimeMillis()
)