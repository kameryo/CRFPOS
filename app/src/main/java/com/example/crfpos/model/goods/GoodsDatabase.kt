package com.example.crfpos.model.goods

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Goods::class], version = 1, exportSchema = false)
abstract class GoodsDatabase : RoomDatabase() {
    abstract fun goodsDao(): GoodsDao
}