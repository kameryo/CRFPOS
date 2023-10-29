package com.example.crfpos.page.sales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crfpos.model.calculater.Calculator
//import com.example.crfpos.model.coupon.Coupon
import com.example.crfpos.model.record.Record
import com.example.crfpos.model.goods.Goods
import com.example.crfpos.model.selected.PendingPurchase
import com.example.crfpos.repository.CouponRepository
import com.example.crfpos.repository.RecordRepository
import com.example.crfpos.repository.GoodsRepository
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
    val selectedGoodsList: List<PendingPurchase> = emptyList(),
    val selectedCouponList: List<PendingPurchase> = emptyList()
)

@HiltViewModel
class SalesViewModel @Inject constructor(
    stockRepo: GoodsRepository,
    private val recordRepo: RecordRepository,
    couponRepo: CouponRepository
) : ViewModel() {
    private val calculator = Calculator()

    private val mutableViewModelState = MutableStateFlow(SalesViewModelState())
    private val viewModelState = mutableViewModelState.asStateFlow()

    val bindModel = combine(
        stockRepo.getAll(),
        couponRepo.getAll(),
        viewModelState,
    ) { stockList, couponList, viewModelState ->
        SalesBindModel(
            adultCount = viewModelState.adultCount,
            childCount = viewModelState.childCount,
            selectedGoods = viewModelState.selectedGoodsList,
            selectedCoupon = viewModelState.selectedCouponList,
            goods = stockList,
            coupon = couponList,
            subtotalFare = calculator.calFare(viewModelState.adultCount, viewModelState.childCount),
            subtotalCoupon = calculator.calSubTotalPendingPurchase(viewModelState.selectedCouponList),
            subtotalGoods = calculator.calSubTotalPendingPurchase(viewModelState.selectedGoodsList),
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
            selectedCoupon = emptyList(),
            goods = emptyList(),
            coupon = emptyList(),
            subtotalFare = 0,
            subtotalCoupon = 0,
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

    fun addGoodsToSelectedGoods(goods: Goods) {
        val addGoods =
            PendingPurchase(_id = goods._id, name = goods.name, price = goods.price, numOfOrder = 1)
        mutableViewModelState.update {
            it.copy(
                selectedGoodsList = (it.selectedGoodsList + listOf(
                    addGoods
                )).sortedBy { goods -> goods._id })
        }
    }

    fun incrementGoods(selectedGoods: PendingPurchase) {
        val updatedSelectedGoodsList = mutableViewModelState.value.selectedGoodsList.map { item ->
            if (item._id == selectedGoods._id) {
                item.copy(numOfOrder = item.numOfOrder + 1)
            } else {
                item
            }
        }
        mutableViewModelState.update { it.copy(selectedGoodsList = updatedSelectedGoodsList) }
    }

    fun decrementGoods(selectedGoods: PendingPurchase) {
        val updatedSelectedGoodsList = mutableViewModelState.value.selectedGoodsList.map { item ->
            if (item._id == selectedGoods._id) {
                item.copy(numOfOrder = if (item.numOfOrder > 1) item.numOfOrder - 1 else 1)
            } else {
                item
            }
        }
        mutableViewModelState.update { it.copy(selectedGoodsList = updatedSelectedGoodsList) }
    }

    fun deleteGoods(selectedGoods: PendingPurchase) {
        val updatedSelectedGoodsList =
            mutableViewModelState.value.selectedGoodsList.filter { item ->
                item._id != selectedGoods._id
            }
        mutableViewModelState.update { it.copy(selectedGoodsList = updatedSelectedGoodsList) }
    }

//    fun addCouponToSelectedCoupon(coupon: Coupon) {
//        val addCoupon =
//            PendingPurchase(
//                _id = coupon._id,
//                name = coupon.name,
//                price = coupon.price,
//                numOfOrder = 1
//            )
//        mutableViewModelState.update {
//            it.copy(
//                selectedCouponList = (it.selectedCouponList + listOf(
//                    addCoupon
//                )).sortedBy { coupon -> coupon._id })
//        }
//    }
//
//    fun incrementCoupon(selectedCoupon: PendingPurchase) {
//        val updatedSelectedCouponList = mutableViewModelState.value.selectedCouponList.map { item ->
//            if (item._id == selectedCoupon._id) {
//                item.copy(numOfOrder = item.numOfOrder + 1)
//            } else {
//                item
//            }
//        }
//        mutableViewModelState.update { it.copy(selectedCouponList = updatedSelectedCouponList) }
//    }
//
//    fun decrementCoupon(selectedCoupon: PendingPurchase) {
//        val updatedSelectedCouponList = mutableViewModelState.value.selectedCouponList.map { item ->
//            if (item._id == selectedCoupon._id) {
//                item.copy(numOfOrder = if (item.numOfOrder > 1) item.numOfOrder - 1 else 1)
//            } else {
//                item
//            }
//        }
//        mutableViewModelState.update { it.copy(selectedCouponList = updatedSelectedCouponList) }
//    }
//
//    fun deleteCoupon(selectedCoupon: PendingPurchase) {
//        val updatedSelectedCouponList =
//            mutableViewModelState.value.selectedCouponList.filter { item ->
//                item._id != selectedCoupon._id
//            }
//        mutableViewModelState.update { it.copy(selectedCouponList = updatedSelectedCouponList) }
//    }

    fun saveRecord() {
        val fareSales = bindModel.value.subtotalFare
        val goodsSales = bindModel.value.subtotalGoods
        val couponSales = bindModel.value.subtotalCoupon
        if (fareSales + goodsSales + couponSales == 0) return
        val record = Record(
            time = System.currentTimeMillis() / 1000,
            total = fareSales + goodsSales + couponSales,
            fareSales = fareSales,
            couponSales = couponSales,
            goodsSales = goodsSales,
            adult = bindModel.value.adultCount,
            child = bindModel.value.childCount,
            goodsList = bindModel.value.selectedGoods,
            couponList = bindModel.value.selectedCoupon,
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
                childManualCountText = "",
                selectedGoodsList = emptyList(),
                selectedCouponList = emptyList()
            )
        }
    }
}
