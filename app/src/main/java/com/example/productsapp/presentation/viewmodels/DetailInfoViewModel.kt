package com.example.productsapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsapp.domain.entities.ProductEntity
import com.example.productsapp.domain.state.State
import com.example.productsapp.domain.usecases.GetProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailInfoViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun getProduct(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(State.Loading)
            _state.postValue(State.Product(product = getProductUseCase.invoke(productEntity)))
        }
    }
}