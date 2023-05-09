package com.example.crfpos.page.sales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.crfpos.repository.product.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val repo: StockRepository
) : ViewModel() {
    val stockList = repo.getAll().asLiveData()
}