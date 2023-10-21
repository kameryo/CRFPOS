package com.example.crfpos.model.stock

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert
    suspend fun insert(stock: Stock)

    @Update
    suspend fun update(stock: Stock)

    @Query("SELECT * FROM Stock ORDER BY _id")
    fun getAll(): Flow<List<Stock>>

    @Delete
    suspend fun delete(stock: Stock)
}