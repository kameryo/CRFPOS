package com.example.crfpos.page.stock.coupon.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crfpos.repository.CouponRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCouponViewModel @Inject constructor(
    private val repo: CouponRepository
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val done = MutableLiveData<Boolean>()

    fun add(name: String, priceStr: String, quantityStr: String) {
        if (name.trim().isEmpty()) {
            errorMessage.value = "Please input name"
            return
        }

        if (priceStr.trim().isEmpty()) {
            errorMessage.value = "Please input price"
            return
        }

        if (quantityStr.trim().isEmpty()) {
            errorMessage.value = "Please input quantity"
            return
        }

        viewModelScope.launch {
            try {
                repo.add(name, priceStr.toInt(), quantityStr.toInt())
                errorMessage.value = "success!"
                done.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }

    }
}