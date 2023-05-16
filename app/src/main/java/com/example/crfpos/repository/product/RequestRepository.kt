package com.example.crfpos.repository.product

import com.example.crfpos.model.request.Request
import com.example.crfpos.model.stock.Stock
import kotlinx.coroutines.flow.Flow

interface RequestRepository {

    fun getAll(): Flow<List<Request>>

    suspend fun insert(stock: Stock, numOfOrder: Int)

    suspend fun delete(request: Request)

    suspend fun incrementRequest(request: Request)

    suspend fun decrementRequest(request: Request)

    suspend fun searchRequestByName(request: Request): Boolean

}