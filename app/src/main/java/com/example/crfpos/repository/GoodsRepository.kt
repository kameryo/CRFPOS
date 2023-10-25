package com.example.crfpos.repository

import com.example.crfpos.model.goods.Goods
import kotlinx.coroutines.flow.Flow

interface GoodsRepository {
    fun getAll(): Flow<List<Goods>>

    suspend fun add(name: String, price: Int, purchases: Int)

    suspend fun delete(goods: Goods)

    suspend fun update(goods: Goods, name: String, price: Int, remain: Int)
}