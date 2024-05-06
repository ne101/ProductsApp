package com.example.productsapp.domain.state

import com.example.productsapp.domain.entities.CategoriesEntity
import com.example.productsapp.domain.entities.ProductEntity
import com.example.productsapp.domain.entities.ProductListEntity

sealed class State {
    data class Products(val products: List<ProductEntity>) : State()
    data class ListIsFull(val products: List<ProductEntity>) : State()
    object Loading : State()
    data class Product(val product: ProductEntity) : State()
    data class ProductsWithCategories(val productsWithCategories: List<ProductEntity>) : State()
    data class Categories(val categories: CategoriesEntity) : State()
}
