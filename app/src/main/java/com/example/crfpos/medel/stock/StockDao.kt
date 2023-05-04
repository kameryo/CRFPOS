package com.example.crfpos.medel.stock

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stock: Stock)

    @Query("SELECT * FROM stock_table ORDER BY _id")
    fun getAll(): Flow<List<Stock>>
}