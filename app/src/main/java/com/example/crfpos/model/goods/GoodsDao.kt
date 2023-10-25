package com.example.crfpos.model.goods

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodsDao {
    @Insert
    suspend fun insert(goods: Goods)

    @Update
    suspend fun update(goods: Goods)

    @Query("SELECT * FROM Goods ORDER BY _id")
    fun getAll(): Flow<List<Goods>>

    @Delete
    suspend fun delete(goods: Goods)
}