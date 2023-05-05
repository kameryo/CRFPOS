package com.example.crfpos.model.stock

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Stock(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val name: String,
    val price: Int,
    val quantity: Int
): Parcelable
