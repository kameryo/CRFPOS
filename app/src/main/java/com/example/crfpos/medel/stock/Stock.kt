package com.example.crfpos.medel.stock

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_table")
data class Stock(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    val name: String,
    val price: Int,
    val quantity: Int
)
