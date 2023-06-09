package com.example.crfpos.model.record

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.crfpos.model.request.RequestConverters

@Database(entities = [Record::class], version = 1, exportSchema = false)
@TypeConverters(RequestConverters::class)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordsDao(): RecordDao
}