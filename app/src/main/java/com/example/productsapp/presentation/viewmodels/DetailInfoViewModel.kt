package com.example.productsapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsapp.domain.entities.ProductEntity
import com.example.productsapp.domain.usecases.GetProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailInfoViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    private val _product = MutableLiveData<ProductEntity>()
    val product: LiveData<ProductEntity>
        get() = _product

    fun getProduct(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _product.postValue(getProductUseCase.invoke(productEntity))
        }
    }
}