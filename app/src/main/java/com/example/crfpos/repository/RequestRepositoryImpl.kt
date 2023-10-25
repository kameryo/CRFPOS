package com.example.crfpos.repository

import com.example.crfpos.model.request.Request
import com.example.crfpos.model.request.RequestDao
import com.example.crfpos.model.goods.Goods
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

    override suspend fun insert(goods: Goods, numOfOrder: Int) {
        val request =
            Request(_id = goods._id, stockName = goods.name, stockPrice = goods.price, numOfOrder = numOfOrder)
        withContext(Dispatchers.IO) {
            dao.insert(request)
        }
    }

    override suspend fun delete(request: Request) {
        dao.delete(request)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun incrementRequest(request: Request) {
        val updateRequest = Request(
            _id = request._id,
            stockName = request.stockName,
            stockPrice = request.stockPrice,
            numOfOrder = request.numOfOrder + 1
        )

        withContext(Dispatchers.IO) {
            dao.update(updateRequest)
        }

    }

    override suspend fun decrementRequest(request: Request) {
        val updateNum: Int = if (request.numOfOrder > 1) {
            request.numOfOrder - 1
        } else {
            request.numOfOrder
        }


        val updateRequest = Request(
            _id = request._id,
            stockName = request.stockName,
            stockPrice = request.stockPrice,
            numOfOrder = updateNum
        )

        withContext(Dispatchers.IO) {
            dao.update(updateRequest)
        }
    }

}