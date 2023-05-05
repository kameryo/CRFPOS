package com.example.crfpos.medel.stock

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Stock(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val name: String,
    val price: Int,
    val quantity: Int
)
