package com.example.crfpos.repository.product

import com.example.crfpos.model.request.Request
import com.example.crfpos.model.request.RequestDao
import com.example.crfpos.model.stock.Stock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestRepositoryImpl @Inject constructor(
    private val dao: RequestDao
) : RequestRepository {
    override fun getAll(): Flow<List<Request>> {
        return dao.getAll()
    }

    override suspend fun insert(stock: Stock, numOfOrder: Int) {
        val request =
            Request(stockName = stock.name, stockPrice = stock.price, numOfOrder = numOfOrder)
        withContext(Dispatchers.IO) {
            dao.insert(request)
        }
    }

    override suspend fun delete(request: Request) {
        dao.delete(request)
    }

}