package com.example.crfpos.model.coupon

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CouponDao {
    @Insert
    suspend fun insert(coupon: Coupon)

    @Update
    suspend fun update(coupon: Coupon)

    @Query("SELECT * FROM Coupon ORDER BY _id")
    fun getAll(): Flow<List<Coupon>>

    @Delete
    suspend fun delete(coupon: Coupon)
}