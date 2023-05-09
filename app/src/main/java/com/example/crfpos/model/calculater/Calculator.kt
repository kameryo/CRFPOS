package com.example.crfpos.model.calculater

class Calculator {
    fun calFare(adultNum: Int, childNum: Int): Int {
        return adultNum * 10 + childNum * 100
    }
}