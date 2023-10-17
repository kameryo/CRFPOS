package com.example.crfpos.page.sales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crfpos.model.calculater.Calculator
import com.example.crfpos.model.record.Record
import com.example.crfpos.model.request.Request
import com.example.crfpos.model.stock.Stock
import com.example.crfpos.repository.RecordRepository
import com.example.crfpos.repository.RequestRepository
import com.example.crfpos.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SalesViewModelState(
    val adultCount: Int = 0,
    val childCount: Int = 0,
    val adultManualCountText: String = "",
    val childManualCountText: String = "",
)

@HiltViewModel
class SalesViewModel @Inject constructor(
    stockRepo: StockRepository,
    private val requestRepo: RequestRepository,
    private val recordRepo: RecordRepository
) : ViewModel() {
    private val calculator = Calculator()

    private val mutableViewModelState = MutableStateFlow(SalesViewModelState())
    private val viewModelState = mutableViewModelState.asStateFlow()

    val bindModel = combine(
        stockRepo.getAll(),
        requestRepo.getAll(),
        viewModelState,
    ) { stockList, requestList, viewModelState ->
        SalesBindModel(
            adultCount = viewModelState.adultCount,
            childCount = viewModelState.childCount,
            selectedGoods = requestList,
            stocks = stockList,
            subtotalFare = calculator.calFare(viewModelState.adultCount, viewModelState.childCount),
            specialFee = 0, // TODO: トクトク対応
            subtotalGoods = calculator.calGoodsSubTotal(requestList),
            adultManualCountText = viewModelState.adultManualCountText,
            childManualCountText = viewModelState.childManualCountText,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = SalesBindModel(
            adultCount = 0,
            childCount = 0,
            selectedGoods = emptyList(),
            stocks = emptyList(),
            subtotalFare = 0,
            specialFee = 0,
            subtotalGoods = 0,
        ),
    )

    private val mutableErrorMessageFlow = MutableSharedFlow<String?>()
    val errorMessageFlow = mutableErrorMessageFlow.asSharedFlow()

    fun updateAdultCount(count: Int) {
        mutableViewModelState.update { it.copy(adultCount = count) }
    }

    fun updateChildCount(count: Int) {
        mutableViewModelState.update { it.copy(childCount = count) }
    }

    fun updateChildManualCountText(countText: String) {
        mutableViewModelState.update { it.copy(childManualCountText = countText) }
    }

    fun updateAdultManualCountText(countText: String) {
        mutableViewModelState.update { it.copy(adultManualCountText = countText) }
    }

    fun applyAdultManualCountText() {
        val count = mutableViewModelState.value.adultManualCountText.toIntOrNull() ?: return
        mutableViewModelState.update { it.copy(adultCount = count) }
    }

    fun applyChildManualCountText() {
        val count = mutableViewModelState.value.childManualCountText.toIntOrNull() ?: return
        mutableViewModelState.update { it.copy(childCount = count) }
    }

    fun addRequest(stock: Stock) {
        viewModelScope.launch {
            try {
                requestRepo.insert(stock, 1)
            } catch (e: Exception) {
                mutableErrorMessageFlow.emit(e.message)
            }
        }
    }

    fun delete(request: Request) {
        viewModelScope.launch {
            try {
                requestRepo.delete(request)
            } catch (e: Exception) {
                mutableErrorMessageFlow.emit(e.message)
            }
        }
    }

    fun incrementRequest(request: Request) {
        viewModelScope.launch {
            try {
                requestRepo.incrementRequest(request)
            } catch (e: Exception) {
                mutableErrorMessageFlow.emit(e.message)
            }
        }
    }

    fun decrementRequest(request: Request) {
        viewModelScope.launch {
            try {
                requestRepo.decrementRequest(request)
            } catch (e: Exception) {
                mutableErrorMessageFlow.emit(e.message)
            }
        }
    }

    fun saveRecord() {
        val fareSales = bindModel.value.subtotalFare
        val goodsSales = bindModel.value.subtotalGoods
        if (fareSales + goodsSales == 0) return
        val record = Record(
            time = System.currentTimeMillis() / 1000,
            total = fareSales + goodsSales,
            fareSales = fareSales,
            otherSales = 0,
            goodsSales = goodsSales,
            adult = bindModel.value.adultCount,
            child = bindModel.value.childCount,
            requestList = bindModel.value.selectedGoods,
            memo = "",
        )
        viewModelScope.launch {
            try {
                recordRepo.insert(record)
            } catch (e: Exception) {
                mutableErrorMessageFlow.emit(e.message)
            }
        }
    }

    fun clearInputs() {
        mutableViewModelState.update {
            it.copy(
                adultCount = 0,
                childCount = 0,
                adultManualCountText = "",
                childManualCountText = ""
            )
        }
        viewModelScope.launch {
            try {
                requestRepo.deleteAll()
            } catch (e: Exception) {
                mutableErrorMessageFlow.emit(e.message)
            }
        }
    }
}
