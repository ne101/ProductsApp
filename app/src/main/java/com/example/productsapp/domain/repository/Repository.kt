package com.example.productsapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.productsapp.domain.entities.CategoriesEntity
import com.example.productsapp.domain.entities.ProductEntity
import com.example.productsapp.domain.entities.ProductListEntity

interface Repository {

    suspend fun getProductList(skip: Int): ProductListEntity
    suspend fun getProduct(productEntity: ProductEntity): ProductEntity
    suspend fun getProductListWithSearch(query: String): ProductListEntity
    suspend fun getProductListWithCategories(category: String): ProductListEntity
    suspend fun getCategoryList(): CategoriesEntity

}