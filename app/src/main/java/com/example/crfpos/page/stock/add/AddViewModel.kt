package com.example.crfpos.page.stock.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crfpos.repository.product.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repo: StockRepository
) : ViewModel() {
    private val errorMessage = MutableLiveData<String>()

    fun add(name: String, price: Int, quantity: Int) {
        if (name.trim().isEmpty()) {
            errorMessage.value = "Please input name"
            return
        }

        if (price.toString().trim().isEmpty()) {
            errorMessage.value = "Please input price"
            return
        }

        if (quantity.toString().trim().isEmpty()) {
            errorMessage.value = "Please input quantity"
            return
        }

        viewModelScope.launch {
            try {
                repo.add(name, price, quantity)
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }

    }
}