package com.example.crfpos.repository

import com.example.crfpos.model.goods.Goods
import com.example.crfpos.model.goods.GoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GoodsRepositoryImpl @Inject constructor(
    private val dao: GoodsDao
) : GoodsRepository {

    override fun getAll(): Flow<List<Goods>> {
        return dao.getAll()
    }

    override suspend fun add(name: String, price: Int, purchases: Int) {
        val goods = Goods(name = name, price = price, purchases = purchases, remain = purchases)
        withContext(Dispatchers.IO) {
            dao.insert(goods)
        }
    }

    override suspend fun delete(goods: Goods) {
        dao.delete(goods)
    }

    override suspend fun update(goods: Goods, name: String, price: Int, remain: Int) {
        val updateGoods = Goods(
            _id = goods._id,
            name = name,
            price = price,
            purchases = remain,
            remain = remain
        )
        withContext(Dispatchers.IO) {
            dao.update(updateGoods)
        }
    }

}