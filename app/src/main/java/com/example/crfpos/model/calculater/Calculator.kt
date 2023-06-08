package com.example.crfpos.model.calculater

import com.example.crfpos.model.request.Request

class Calculator {
    fun calFare(adultNum: Int, childNum: Int): Int {
        return adultNum * 10 + childNum * 100
    }

    fun calGoodsSubTotal(requestList: List<Request>?): Int {
        if (requestList != null) {
            return requestList.sumBy { it.stockPrice * it.numOfOrder }
        }
        return 0
    }

}