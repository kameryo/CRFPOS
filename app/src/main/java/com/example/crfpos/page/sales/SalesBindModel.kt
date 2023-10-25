package com.example.crfpos.page.sales

import com.example.crfpos.model.coupon.Coupon
import com.example.crfpos.model.goods.Goods
import com.example.crfpos.model.selected.PendingPurchase

data class SalesBindModel(
    val adultCount: Int = 0,
    val childCount: Int = 0,
    val selectedGoods: List<PendingPurchase> = emptyList(),
    val selectedCoupon: List<PendingPurchase> = emptyList(),
    val goods: List<Goods> = emptyList(),
    val coupon: List<Coupon> = emptyList(),
    val subtotalFare: Int = 0,
    val subtotalCoupon: Int = 0,
    val subtotalGoods: Int = 0,
    val childManualCountText: String = "",
    val adultManualCountText: String = "",
)
