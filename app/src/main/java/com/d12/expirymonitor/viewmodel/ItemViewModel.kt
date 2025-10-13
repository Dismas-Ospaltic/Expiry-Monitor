package com.d12.expirymonitor.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d12.expirymonitor.model.ItemEntity
import com.d12.expirymonitor.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    // Holds the list of item
    private val _items = MutableStateFlow<List<ItemEntity>>(emptyList())
    val items: StateFlow<List<ItemEntity>> = _items

    init {
        getAllItemList()
    }


    /**
     * Fetch all wishes
     */
    private fun getAllItemList() {
        viewModelScope.launch {
            itemRepository.getAllItems().collectLatest { itemList ->
                _items.value = itemList
            }
        }
    }






    /**
     * Insert a new item record
     */
    fun insertItem(item: ItemEntity) {
        viewModelScope.launch {
            itemRepository.insertItem(item)
        }
    }

    /**
     * Update an existing item record
     */
    fun updateItem(item: ItemEntity) {
        viewModelScope.launch {
            itemRepository.updateItem(item)
        }
    }

    /**
     * Update the item by Id
     */
    fun updateItemById(
        itemName: String,
        itemPhoto: String,
        itemCode: String,
        itemCategory: String,
        itemDescription: String,
        itemQuantity: Int,
        manufactureDate: String,
        expiryDate: String,
        itemId: String
    ) {
        viewModelScope.launch {
            val success = itemRepository.updateItemById(
                itemName,
                itemPhoto,
                itemCode,
                itemCategory,
                itemDescription,
                itemQuantity,
                manufactureDate,
                expiryDate,
                itemId
            )

            if (success) {
                // ✅ Optional: Update local state if you’re holding a cached list
                _items.value = _items.value.map { item ->
                    if (item.itemId == itemId) {
                        item.copy(
                            itemName = itemName,
                            itemPhoto = itemPhoto,
                            itemCode = itemCode,
                            itemCategory = itemCategory,
                            itemDescription = itemDescription,
                            itemQuantity = itemQuantity,
                            manufactureDate = manufactureDate,
                            expiryDate = expiryDate
                        )
                    } else item
                }
            }
        }
    }







}