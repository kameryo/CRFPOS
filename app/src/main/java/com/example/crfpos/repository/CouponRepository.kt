package com.example.crfpos.repository

import com.example.crfpos.model.coupon.Coupon
import kotlinx.coroutines.flow.Flow

interface CouponRepository {
    fun getAll(): Flow<List<Coupon>>

    suspend fun add(name: String, price: Int, remain: Int)

    suspend fun delete(coupon: Coupon)

    suspend fun update(coupon: Coupon, name: String, price: Int, remain: Int)
}