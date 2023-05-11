package com.example.crfpos.model.request

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestDao {

    @Insert
    suspend fun insert(request: Request)

    @Query("SELECT * FROM Request ORDER BY _id")
    fun getAll(): Flow<List<Request>>

    @Delete
    suspend fun delete(request: Request)
}