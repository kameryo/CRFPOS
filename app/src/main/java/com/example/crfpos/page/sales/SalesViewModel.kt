package com.example.crfpos.page.sales

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.crfpos.model.calculater.Calculator
import com.example.crfpos.model.record.Record
import com.example.crfpos.model.request.Request
import com.example.crfpos.model.stock.Stock
import com.example.crfpos.repository.RecordRepository
import com.example.crfpos.repository.RequestRepository
import com.example.crfpos.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val stockRepo: StockRepository,
    private val requestRepo: RequestRepository,
    private val recordRepo: RecordRepository
) : ViewModel() {
    private val calculator = Calculator()

    private val mutableAdultNum = MutableStateFlow(0)
    val adultNum = mutableAdultNum.asStateFlow()
    private val mutableChildNum = MutableStateFlow(0)
    val childNum = mutableChildNum.asStateFlow()

    val stockList = stockRepo.getAll().asLiveData()
    val requestList = requestRepo.getAll().asLiveData()

    val subtotalFare = combine(
        adultNum,
        childNum,
    ) { adultNum, childNum ->
        calculator.calFare(adultNum, childNum)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = 0,
    )

    val subtotalGoods = requestList.map { requestList -> calculator.calGoodsSubTotal(requestList) }

    val errorMessage = MutableLiveData<String>()
//    val done = MutableLiveData<Boolean>()

    fun addRequest(stock: Stock) {
        viewModelScope.launch {
            try {
                requestRepo.insert(stock, 1)
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun delete(request: Request) {
        viewModelScope.launch {
            try {
                requestRepo.delete(request)
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            try {
                requestRepo.deleteAll()
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun incrementRequest(request: Request) {
        viewModelScope.launch {
            try {
                requestRepo.incrementRequest(request)
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun decrementRequest(request: Request) {
        viewModelScope.launch {
            try {
                requestRepo.decrementRequest(request)
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun saveRecord() {
        val fareSales = subtotalFare.value
        val goodsSales = subtotalGoods.value ?: 0
        if (fareSales + goodsSales == 0) return
        val record = Record(
            time = System.currentTimeMillis() / 1000,
            total = fareSales + goodsSales,
            fareSales = fareSales,
            otherSales = 0,
            goodsSales = goodsSales,
            adult = adultNum.value,
            child = childNum.value,
            requestList = requestList.value,
            memo = ""
        )
        viewModelScope.launch {
            try {
                recordRepo.insert(record)
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun updateChildNum(childNum: Int) {
        mutableChildNum.value = childNum
    }

    fun updateAdultNum(adultNum: Int) {
        mutableAdultNum.value = adultNum
    }
}
