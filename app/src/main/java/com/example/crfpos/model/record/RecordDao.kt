package com.example.crfpos.model.record

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.crfpos.model.request.Request
import kotlinx.coroutines.flow.Flow
import javax.xml.datatype.DatatypeConstants.DATETIME

@Dao
interface RecordDao {
    @Insert
    suspend fun insert(record: Record)

    @Delete
    suspend fun delete(record: Record)

    @Query("SELECT * FROM Record ORDER BY _id desc")
    fun getAll(): Flow<List<Record>>

    @Query(
        "SELECT " +
                "date(datetime(time, 'unixepoch')) AS date," +
                "COUNT(*) AS numOfSales," +
                "SUM(total) AS salesAll," +
                "SUM(fareSales) AS salesRail," +
                "SUM(goodsSales) AS salesGoods," +
                "SUM(adult) + SUM(child) AS numOfPerson" +
                " FROM Record GROUP BY date ORDER BY date DESC"
    )
    fun getSummary(): Flow<List<Summary>>

    @Query(
        "SELECT " +
                "time," +
                "total," +
                "fareSales," +
                "otherSales," +
                "goodsSales," +
                "adult," +
                "child," +
                "(adult + child) AS numOfPerson," +
                "requestList," +
                "memo" +
                " FROM Record WHERE DATE(DATETIME(time, 'unixepoch')) = DATE(DATETIME(:dateUnix, 'unixepoch'))"
    )
    fun getDiarySummary(dateUnix: Long): Flow<List<OutputRecord>>


    data class Summary(
        val date: String,
        val numOfSales: Int,
        val salesAll: Int,
        val salesRail: Int,
        val salesGoods: Int,
        val numOfPerson: Int
    )

    data class OutputRecord(
        val time: Long,
        val total: Int,
        val fareSales: Int,
        val otherSales: Int,
        val goodsSales: Int,
        val adult: Int,
        val child: Int,
        val numOfPerson: Int,
        val requestList: List<Request>?,
        val memo: String
    )
}