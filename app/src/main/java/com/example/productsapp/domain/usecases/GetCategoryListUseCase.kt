package com.example.productsapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.productsapp.domain.entities.CategoriesEntity
import com.example.productsapp.domain.repository.Repository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): CategoriesEntity {
        return repository.getCategoryList()
    }
}