package com.example.crfpos.model.selected

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PendingPurchaseConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromList(couponList: List<PendingPurchase>): String {
        return gson.toJson(couponList)
    }

    @TypeConverter
    fun toList(couponListString: String): List<PendingPurchase> {
        val listType = object : TypeToken<List<PendingPurchase>>() {}.type
        return gson.fromJson(couponListString, listType)
    }
}