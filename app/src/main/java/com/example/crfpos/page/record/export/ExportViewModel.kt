package com.example.crfpos.page.record.export

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.crfpos.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ExportViewModel @Inject constructor(
    recordRepo: RecordRepository
) : ViewModel() {
    val dateList = recordRepo.getDateList().asLiveData()
}