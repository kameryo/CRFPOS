package com.example.crfpos.model.coupon

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CouponConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromCouponList(couponList: List<Coupon>): String {
        return gson.toJson(couponList)
    }

    @TypeConverter
    fun toCouponList(couponListString: String): List<Coupon> {
        val listType = object : TypeToken<List<Coupon>>() {}.type
        return gson.fromJson(couponListString, listType)
    }
}