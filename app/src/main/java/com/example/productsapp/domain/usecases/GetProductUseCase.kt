package com.example.productsapp.domain.usecases

import com.example.productsapp.domain.entities.ProductEntity
import com.example.productsapp.domain.repository.Repository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(productEntity: ProductEntity): ProductEntity {
        return repository.getProduct(productEntity)
    }
}