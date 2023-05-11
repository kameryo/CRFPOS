package com.example.crfpos.model.request

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Request::class], version = 1, exportSchema = false)
abstract class RequestDatabase : RoomDatabase() {
    abstract fun requestDao(): RequestDao
}