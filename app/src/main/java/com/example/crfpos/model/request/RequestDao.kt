package com.example.crfpos.model.request

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestDao {

    @Insert
    suspend fun insert(request: Request)

    @Query("SELECT * FROM Request ORDER BY _id")
    fun getAll(): Flow<List<Request>>

    @Delete
    suspend fun delete(request: Request)

    @Query("delete from Request")
    suspend fun deleteAll()

    @Update
    suspend fun update(request: Request)

    @Query("select * from Request where stockName = :stockName")
    fun getRequest(stockName: String): Request
}