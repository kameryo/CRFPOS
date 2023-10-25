package com.example.crfpos.page.stock.coupon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.crfpos.repository.CouponRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(
    repo: CouponRepository
) : ViewModel() {
    val couponList = repo.getAll().asLiveData()
}