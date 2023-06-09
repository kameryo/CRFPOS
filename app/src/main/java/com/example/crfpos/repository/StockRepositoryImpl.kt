package com.example.crfpos.repository

import com.example.crfpos.model.stock.Stock
import com.example.crfpos.model.stock.StockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val dao: StockDao
) : StockRepository {

    override fun getAll(): Flow<List<Stock>> {
        return dao.getAll()
    }

    override suspend fun add(name: String, price: Int, purchases: Int) {
        val stock = Stock(name = name, price = price, purchases = purchases, remain = purchases)
        withContext(Dispatchers.IO) {
            dao.insert(stock)
        }
    }

    override suspend fun delete(stock: Stock) {
        dao.delete(stock)
    }


}