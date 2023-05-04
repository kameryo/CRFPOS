package com.example.crfpos

import android.content.Context
import androidx.room.Room
import com.example.crfpos.medel.stock.StockDao
import com.example.crfpos.medel.stock.StockDatabase
import com.example.crfpos.repository.product.StockRepository
import com.example.crfpos.repository.product.StockRepositoryImpl
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