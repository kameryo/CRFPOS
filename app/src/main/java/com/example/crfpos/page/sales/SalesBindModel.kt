package com.example.crfpos.page.sales

import com.example.crfpos.model.request.Request
import com.example.crfpos.model.stock.Stock

data class SalesBindModel(
    val adultCount: Int = 0,
    val childCount: Int = 0,
    val selectedGoods: List<Request> = emptyList(),
    val stocks: List<Stock> = emptyList(),
    val subtotalFare: Int = 0,
    val specialFee: Int = 0,
    val subtotalGoods: Int = 0,
    val childManualCountText: String = "",
    val adultManualCountText: String = "",
)
