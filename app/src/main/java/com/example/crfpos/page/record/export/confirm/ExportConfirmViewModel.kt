package com.example.crfpos.page.record.export.confirm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crfpos.model.exportCSV.ExportCSV
import com.example.crfpos.model.record.Record
import com.example.crfpos.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExportConfirmViewModel @Inject constructor(
    private val recordRepo: RecordRepository
) : ViewModel() {

    fun getDiaryRecord(date: String): List<Record> {
        var records: List<Record> = emptyList()
        viewModelScope.launch {
            try {
                records = recordRepo.getDiaryData(date)
                return@launch
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return records

    }

    fun exportRecordToCSV(date: String, context: Context?) {
        viewModelScope.launch {
            try {
                val records = recordRepo.getDiaryData(date)
                val exporter = ExportCSV()
                exporter.writeRecordsToCSV(records, date, context)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}