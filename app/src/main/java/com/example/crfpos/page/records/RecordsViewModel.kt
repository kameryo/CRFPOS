package com.example.crfpos.page.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.crfpos.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val recordRepo: RecordRepository
): ViewModel(){
    val recordList = recordRepo.getAll().asLiveData()
}