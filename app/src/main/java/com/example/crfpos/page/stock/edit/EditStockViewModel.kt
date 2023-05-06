package com.example.crfpos.page.stock.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crfpos.model.stock.Stock
import com.example.crfpos.repository.product.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditStockViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val stock = savedStateHandle.getLiveData<Stock>("stock")
    val errorMessage = MutableLiveData<String>()
    val deleted = MutableLiveData<Boolean>()

    fun delete() {
        viewModelScope.launch {
            try {
                val stock = this@EditStockViewModel.stock.value ?: return@launch
                stockRepository.delete(stock)
                deleted.value = true
            } catch (e: java.lang.Exception) {
                errorMessage.value = e.message
            }
        }
    }


}