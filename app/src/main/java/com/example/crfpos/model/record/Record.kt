package com.example.crfpos.model.record

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.crfpos.model.request.Request
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Record(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val time: Long,
    val total: Int,
    val fareSales: Int,
    val otherSales: Int,
    val goodsSales: Int,
    val adult: Int,
    val child: Int,
    val requestList: List<Request>?,
    val memo: String
) : Parcelable