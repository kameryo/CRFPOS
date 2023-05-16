package com.example.crfpos.page.sales

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.crfpos.model.request.Request
import com.example.crfpos.model.stock.Stock
import com.example.crfpos.repository.product.RequestRepository
import com.example.crfpos.repository.product.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val stockRepo: StockRepository,
    private val requestRepo: RequestRepository
) : ViewModel() {
    val stockList = stockRepo.getAll().asLiveData()
    val requestList = requestRepo.getAll().asLiveData()

    val errorMessage = MutableLiveData<String>()
//    val done = MutableLiveData<Boolean>()

    fun addRequest(stock: Stock) {
        viewModelScope.launch {
            try {
                requestRepo.insert(stock, 1)
                errorMessage.value = "success!"
//                done.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun delete(request: Request) {
        viewModelScope.launch {
            try {
                requestRepo.delete(request)
                errorMessage.value = "success!"
//                done.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun incrementRequest(request: Request) {
        viewModelScope.launch {
            try {
                requestRepo.incrementRequest(request)
                errorMessage.value = "success!"
//                done.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun decrementRequest(request: Request) {
        viewModelScope.launch {
            try {
                requestRepo.decrementRequest(request)
                errorMessage.value = "success!"
//                done.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }
}