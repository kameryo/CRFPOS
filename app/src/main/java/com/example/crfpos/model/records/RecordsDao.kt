package com.example.crfpos.model.records

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordsDao {
    @Insert
    suspend fun insert(records: Records)

    @Delete
    suspend fun delete(records: Records)

    @Query("SELECT * FROM Records ORDER BY _id")
    fun getAll() : Flow<List<Records>>
}