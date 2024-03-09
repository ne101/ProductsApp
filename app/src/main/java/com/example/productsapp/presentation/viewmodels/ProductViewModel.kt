package com.example.productsapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsapp.domain.entities.CategoriesEntity
import com.example.productsapp.domain.entities.ProductEntity
import com.example.productsapp.domain.usecases.GetCategoryListUseCase
import com.example.productsapp.domain.usecases.GetProductListUseCase
import com.example.productsapp.domain.usecases.GetProductListWithCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getProductListWithCategory: GetProductListWithCategory
) : ViewModel() {

    private var skip = 0
    private var isLoading = false
    private var _isCategoryListActive = MutableLiveData<Boolean>()
    val isCategoryListActive: LiveData<Boolean>
        get() = _isCategoryListActive
    private val _productList = MutableLiveData<MutableList<ProductEntity>>()
    val productList: LiveData<MutableList<ProductEntity>>
        get() = _productList
    private var _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar
    private val _categoryList = MutableLiveData<CategoriesEntity>()
    val categoryList: LiveData<CategoriesEntity>
        get() = _categoryList
    private val _productListWithCategory = MutableLiveData<List<ProductEntity>>()
    val productListWithCategory: LiveData<List<ProductEntity>>
        get() = _productListWithCategory


    fun setActiveCategoryList() {
        _isCategoryListActive.value = false
    }
    fun loadMoreProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _isCategoryListActive.postValue(false)
            if (!isLoading && skip < 100) {
                isLoading = true
                _progressBar.postValue(true)
                val newProducts = getProductListUseCase.invoke(skip).products
                val updatedList = mutableListOf<ProductEntity>().apply {
                    addAll(_productList.value ?: emptyList())
                    addAll(newProducts)
                }
                _productList.postValue(updatedList)
                skip += 20
                _progressBar.postValue(false)
                isLoading = false
            }
        }
    }

    fun getProductListWithCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isCategoryListActive.postValue(true)
            _progressBar.postValue(true)
            _productListWithCategory.postValue(getProductListWithCategory.invoke(category).products)
            _progressBar.postValue(false)
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categoryList.postValue(getCategoryListUseCase.invoke())
        }
    }

}