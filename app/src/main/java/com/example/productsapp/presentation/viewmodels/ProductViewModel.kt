package com.example.productsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsapp.domain.entities.CategoriesEntity
import com.example.productsapp.domain.entities.ProductEntity
import com.example.productsapp.domain.state.State
import com.example.productsapp.domain.usecases.GetCategoryListUseCase
import com.example.productsapp.domain.usecases.GetProductListUseCase
import com.example.productsapp.domain.usecases.GetProductListWithCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getProductListWithCategory: GetProductListWithCategoryUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state


    private val currentProducts = mutableListOf<ProductEntity>()

    fun loadMoreProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_state.value != State.Loading && currentProducts.size < 100) {
                _state.postValue(State.Loading)
                val newProducts = getProductListUseCase.invoke(currentProducts.size).products
                currentProducts.addAll(newProducts)
                Log.d("loadMoreProducts", currentProducts.size.toString())
                _state.postValue(State.Products(products = currentProducts.toList()))
            }
            if (currentProducts.size >= 100) {
                _state.postValue(State.ListIsFull(products = currentProducts.toList()))
            }
        }
    }

    fun setStateProducts() {
        _state.value = State.Products(currentProducts.toList())
    }
    fun getProductListWithCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(State.Loading)
            _state.postValue(
                State.ProductsWithCategories(
                    productsWithCategories = getProductListWithCategory.invoke(
                        category
                    ).products
                )
            )
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(State.Categories(categories = getCategoryListUseCase.invoke()))
        }
    }

}