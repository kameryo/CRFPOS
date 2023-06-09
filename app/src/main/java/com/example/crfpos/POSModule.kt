package com.example.crfpos

import android.content.Context
import androidx.room.Room
import com.example.crfpos.model.record.RecordDao
import com.example.crfpos.model.record.RecordDatabase
import com.example.crfpos.model.request.RequestDao
import com.example.crfpos.model.request.RequestDatabase
import com.example.crfpos.model.stock.StockDao
import com.example.crfpos.model.stock.StockDatabase
import com.example.crfpos.repository.RecordRepository
import com.example.crfpos.repository.RecordRepositoryImpl
import com.example.crfpos.repository.RequestRepository
import com.example.crfpos.repository.RequestRepositoryImpl
import com.example.crfpos.repository.StockRepository
import com.example.crfpos.repository.StockRepositoryImpl
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
    fun provideStockDao(stockDatabase: StockDatabase): StockDao {
        return stockDatabase.stockDao()
    }

    @Singleton
    @Provides
    fun provideStockDatabase(@ApplicationContext context: Context): StockDatabase {
        return Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            "stock_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRequestDao(requestDatabase: RequestDatabase): RequestDao {
        return requestDatabase.requestDao()
    }

    @Singleton
    @Provides
    fun provideRequestDatabase(@ApplicationContext context: Context): RequestDatabase {
        return Room.databaseBuilder(
            context,
            RequestDatabase::class.java,
            "request_database"
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
}

@Module
@InstallIn(SingletonComponent::class)
abstract class StockRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindStockRepository(
        impl: StockRepositoryImpl
    ): StockRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RequestRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRequestRepository(
        impl: RequestRepositoryImpl
    ): RequestRepository
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