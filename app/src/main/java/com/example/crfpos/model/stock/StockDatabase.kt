package com.example.crfpos.model.stock

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Stock::class], version = 1, exportSchema = false)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
}