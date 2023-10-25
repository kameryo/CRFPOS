package com.example.crfpos.model.record

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.crfpos.model.selected.PendingPurchaseConverters

@Database(entities = [Record::class], version = 1, exportSchema = false)
@TypeConverters(PendingPurchaseConverters::class)

abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordsDao(): RecordDao
}