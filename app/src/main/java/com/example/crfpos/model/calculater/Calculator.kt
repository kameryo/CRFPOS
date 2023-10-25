package com.example.crfpos.model.calculater

import com.example.crfpos.model.selected.PendingPurchase

class Calculator {
    fun calFare(adultNum: Int, childNum: Int): Int {
        return if (adultNum >= childNum) {
            adultNum * 100
        } else {
            childNum * 100
        }
    }

    fun calSubTotalPendingPurchase(selectedList: List<PendingPurchase>?): Int {
        if (selectedList != null) {
            return selectedList.sumBy { it.price * it.numOfOrder }
        }
        return 0
    }

}