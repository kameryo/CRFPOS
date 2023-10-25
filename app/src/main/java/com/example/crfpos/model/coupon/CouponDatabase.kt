package com.example.crfpos.model.coupon

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Coupon::class], version = 1, exportSchema = false)
abstract class CouponDatabase : RoomDatabase() {
    abstract fun couponDao(): CouponDao
}