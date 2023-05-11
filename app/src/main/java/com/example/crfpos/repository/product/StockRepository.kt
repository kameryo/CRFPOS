package com.example.crfpos.repository.product

import com.example.crfpos.model.stock.Stock
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun getAll(): Flow<List<Stock>>

    suspend fun add(name: String, price: Int, purchases: Int)

    suspend fun delete(stock: Stock)

//    suspend fun update(stock: Stock, name: String, price: Int, quantity: Int): Stock
//
}