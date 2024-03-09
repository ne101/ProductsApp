package com.example.productsapp.data.repositoryImpl

import com.example.productsapp.data.Mapper
import com.example.productsapp.data.api.ApiService
import com.example.productsapp.domain.entities.CategoriesEntity
import com.example.productsapp.domain.entities.ProductEntity
import com.example.productsapp.domain.entities.ProductListEntity
import com.example.productsapp.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val mapper: Mapper,
    private val apiService: ApiService
) : Repository {
    override suspend fun getProductList(skip: Int): ProductListEntity {
        val response = apiService.loadProductList(skip)
        return mapper.mapProductResponseToEntityList(response)
    }

    override suspend fun getProduct(productEntity: ProductEntity): ProductEntity {
        return productEntity
    }

    override suspend fun getProductListWithSearch( query: String): ProductListEntity {
        val response = apiService.loadProductListWithSearch(query)
        return mapper.mapProductResponseToEntityList(response)
    }

    override suspend fun getProductListWithCategories(category: String): ProductListEntity {
        val response = apiService.loadProductListWithCategory(category)
        return mapper.mapProductResponseToEntityList(response)
    }

    override suspend fun getCategoryList(): CategoriesEntity {
        val response = apiService.loadCategoryList()
        return mapper.mapCategoryListToEntity(response)
    }
}