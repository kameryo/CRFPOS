package com.example.crfpos.model.request

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Request(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val stockName: String,
    val stockPrice: Int,
    val numOfOrder: Int
) : Parcelable