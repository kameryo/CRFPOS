package com.example.crfpos.page.record.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crfpos.model.record.Record
import com.example.crfpos.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRecordViewModel @Inject constructor(
    private val repo: RecordRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    val record = savedStateHandle.getLiveData<Record>("record")
    val errorMessage = MutableLiveData<String>()
    val deleted = MutableLiveData<Boolean>()

    fun delete() {
        viewModelScope.launch {
            try {
                val record = this@EditRecordViewModel.record.value ?: return@launch
                repo.delete(record)
                deleted.value = true
            } catch (e: java.lang.Exception) {
                errorMessage.value = e.message
            }
        }
    }
}