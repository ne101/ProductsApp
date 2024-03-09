package com.example.productsapp.domain.usecases

import com.example.productsapp.domain.entities.ProductListEntity
import com.example.productsapp.domain.repository.Repository
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(skip: Int): ProductListEntity {
        return repository.getProductList(skip)
    }
}