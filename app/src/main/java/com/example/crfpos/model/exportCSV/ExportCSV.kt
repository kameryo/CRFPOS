package com.example.crfpos.model.exportCSV

import android.content.Context
import android.util.Log
import com.example.crfpos.model.record.Record
import java.io.File
import java.io.IOException

class ExportCSV {

    fun writeRecordsToCSV(recordsList: List<Record>, date: String, context: Context?) {
        try {
            val fileName = "$date.csv"

            File(context?.filesDir, fileName).writer().use {

                it.write("ID,Time,Total,Fare Sales,Goods Sales,Adult,Child,\n")

                for (record in recordsList) {
                    // 各行のデータを書き込む
                    it.write(record._id.toString())
                    it.write(",")
                    it.write(record.time.toString())
                    it.write(",")
                    it.write(record.total.toString())
                    it.write(",")
                    it.write(record.fareSales.toString())
                    it.write(",")
                    it.write(record.goodsSales.toString())
                    it.write(",")
                    it.write(record.adult.toString())
                    it.write(",")
                    it.write(record.child.toString())
                    it.write(",")
                    for (goods in record.goodsList!!) {
                        it.write(goods.name)
                        it.write(",")
                        it.write(goods.numOfOrder.toString())
                        it.write(",")
                        it.write(goods.amount.toString())
                        it.write(",")
                    }
                    it.write("\n")
                }

            }

            Log.d("FilesDir", "finish")

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}