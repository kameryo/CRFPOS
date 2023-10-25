package com.example.crfpos.repository

import com.example.crfpos.model.request.Request
import com.example.crfpos.model.goods.Goods
import kotlinx.coroutines.flow.Flow

interface RequestRepository {

    fun getAll(): Flow<List<Request>>

    suspend fun insert(goods: Goods, numOfOrder: Int)

    suspend fun delete(request: Request)

    suspend fun deleteAll()

    suspend fun incrementRequest(request: Request)

    suspend fun decrementRequest(request: Request)

}