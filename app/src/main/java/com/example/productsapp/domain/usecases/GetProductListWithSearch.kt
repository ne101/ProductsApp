package com.example.productsapp.domain.usecases

import com.example.productsapp.domain.entities.ProductListEntity
import com.example.productsapp.domain.repository.Repository
import javax.inject.Inject

class GetProductListWithSearch @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(query: String): ProductListEntity {
        return repository.getProductListWithSearch(query)
    }
}