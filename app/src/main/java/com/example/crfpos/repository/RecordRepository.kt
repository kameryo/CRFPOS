package com.example.crfpos.repository

import com.example.crfpos.model.record.Record
import com.example.crfpos.model.record.RecordDao
import kotlinx.coroutines.flow.Flow

interface RecordRepository {

    fun getAll(): Flow<List<Record>>

    suspend fun insert(record: Record)

    suspend fun delete(record: Record)

    fun getDateList(): Flow<List<RecordDao.Summary>>

}