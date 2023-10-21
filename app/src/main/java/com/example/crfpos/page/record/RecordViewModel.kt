package com.example.crfpos.page.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.crfpos.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepo: RecordRepository
) : ViewModel() {
    val recordList = recordRepo.getAll().asLiveData()
}