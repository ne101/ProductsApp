package com.example.productsapp.domain.usecases

import com.example.productsapp.domain.entities.ProductListEntity
import com.example.productsapp.domain.repository.Repository
import javax.inject.Inject

class GetProductListWithCategory @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(category: String): ProductListEntity {
        return repository.getProductListWithCategories(category)
    }
}