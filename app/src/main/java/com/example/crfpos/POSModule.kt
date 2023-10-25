package com.example.crfpos

import android.content.Context
import androidx.room.Room
import com.example.crfpos.model.coupon.CouponDao
import com.example.crfpos.model.coupon.CouponDatabase
import com.example.crfpos.model.record.RecordDao
import com.example.crfpos.model.record.RecordDatabase
import com.example.crfpos.model.goods.GoodsDao
import com.example.crfpos.model.goods.GoodsDatabase
import com.example.crfpos.repository.CouponRepository
import com.example.crfpos.repository.CouponRepositoryImpl
import com.example.crfpos.repository.RecordRepository
import com.example.crfpos.repository.RecordRepositoryImpl
import com.example.crfpos.repository.GoodsRepository
import com.example.crfpos.repository.GoodsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object POSModule {
    @Singleton
    @Provides
    fun provideGoodsDao(goodsDatabase: GoodsDatabase): GoodsDao {
        return goodsDatabase.goodsDao()
    }

    @Singleton
    @Provides
    fun provideGoodsDatabase(@ApplicationContext context: Context): GoodsDatabase {
        return Room.databaseBuilder(
            context,
            GoodsDatabase::class.java,
            "goods_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRecordDao(recordDatabase: RecordDatabase): RecordDao {
        return recordDatabase.recordsDao()
    }

    @Singleton
    @Provides
    fun provideRecordDatabase(@ApplicationContext context: Context): RecordDatabase {
        return Room.databaseBuilder(
            context,
            RecordDatabase::class.java,
            "record_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideCouponDao(couponDatabase: CouponDatabase): CouponDao {
        return couponDatabase.couponDao()
    }

    @Singleton
    @Provides
    fun provideCouponDatabase(@ApplicationContext context: Context): CouponDatabase {
        return Room.databaseBuilder(
            context,
            CouponDatabase::class.java,
            "coupon_database"
        ).fallbackToDestructiveMigration().build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class GoodsRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindGoodsRepository(
        impl: GoodsRepositoryImpl
    ): GoodsRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RecordRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRecordRepository(
        impl: RecordRepositoryImpl
    ): RecordRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CouponRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCouponRepository(
        impl: CouponRepositoryImpl
    ): CouponRepository
}