package com.example.crfpos.medel.stock

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert
    suspend fun insert(stock: Stock)

    @Query("SELECT * FROM Stock ORDER BY _id")
    fun getAll(): Flow<List<Stock>>
}