package com.example.crfpos.model.records

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Records::class], version = 1, exportSchema = false)
abstract class RecordsDatabase : RoomDatabase() {
    abstract fun recordsDao(): RecordsDao
}