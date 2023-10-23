package com.example.crfpos.repository

import com.example.crfpos.model.record.Record
import com.example.crfpos.model.record.RecordDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val dao: RecordDao
) : RecordRepository {
    override fun getAll(): Flow<List<Record>> {
        return dao.getAll()
    }

    override suspend fun insert(record: Record) {
        dao.insert(record)
    }

    override suspend fun delete(record: Record) {
        dao.delete(record)
    }

    override fun getDateList(): Flow<List<RecordDao.Summary>> {
        return dao.getSummary()
    }

    override suspend fun getDiaryData(date: String): List<Record> {
        return dao.getDiaryData(date)
    }
}