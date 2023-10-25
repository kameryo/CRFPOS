package com.example.crfpos.repository

import com.example.crfpos.model.coupon.Coupon
import com.example.crfpos.model.coupon.CouponDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CouponRepositoryImpl @Inject constructor(
    private val dao: CouponDao
) : CouponRepository {

    override fun getAll(): Flow<List<Coupon>> {
        return dao.getAll()
    }

    override suspend fun add(name: String, price: Int, remain: Int) {
        val coupon =
            Coupon(name = name, price = price, remain = remain)
        withContext(Dispatchers.IO) {
            dao.insert(coupon)
        }
    }

    override suspend fun delete(coupon: Coupon) {
        dao.delete(coupon)
    }

    override suspend fun update(coupon: Coupon, name: String, price: Int, remain: Int) {
        val updateCoupon = Coupon(
            _id = coupon._id,
            name = name,
            price = price,
            remain = remain
        )
        withContext(Dispatchers.IO) {
            dao.update(updateCoupon)
        }
    }
}