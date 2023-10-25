package com.example.crfpos.page.stock.goods.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crfpos.model.goods.Goods
import com.example.crfpos.repository.GoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditGoodsViewModel @Inject constructor(
    private val goodsRepository: GoodsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val goods = savedStateHandle.getLiveData<Goods>("stock")
    val errorMessage = MutableLiveData<String>()
    val deleted = MutableLiveData<Boolean>()
    val done = MutableLiveData<Boolean>()


    fun delete() {
        viewModelScope.launch {
            try {
                val stock = this@EditGoodsViewModel.goods.value ?: return@launch
                goodsRepository.delete(stock)
                deleted.value = true
            } catch (e: java.lang.Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun save(goods: Goods, name: String, price: String, remain: String) {
        if (name.trim().isEmpty()) {
            errorMessage.value = "Please input name"
            return
        }

        if (price.trim().isEmpty()) {
            errorMessage.value = "Please input price"
            return
        }

        if (remain.trim().isEmpty()) {
            errorMessage.value = "Please input remain"
            return
        }

        viewModelScope.launch {
            try {
                goodsRepository.update(goods, name, price.toInt(), remain.toInt())
                errorMessage.value = "success!"
                done.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

}