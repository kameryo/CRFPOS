package com.example.crfpos.model.goods

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Goods(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val name: String,
    val price: Int,
    val purchases: Int,
    val remain: Int
) : Parcelable
