package com.example.crfpos.page.stock.goods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.crfpos.repository.GoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoodsViewModel @Inject constructor(
    repo: GoodsRepository
) : ViewModel() {
    val goodsList = repo.getAll().asLiveData()
}