package com.example.productsapp.data

import com.example.productsapp.data.models.CategoriesResponse
import com.example.productsapp.data.models.Product
import com.example.productsapp.data.models.ProductsResponse
import com.example.productsapp.domain.entities.CategoriesEntity
import com.example.productsapp.domain.entities.ProductEntity
import com.example.productsapp.domain.entities.ProductListEntity
import javax.inject.Inject

class Mapper @Inject constructor() {

    private fun mapProductToEntity(product: Product): ProductEntity = ProductEntity(
        brand = product.brand,
        category = product.category,
        description = product.description,
        discountPercentage = product.discountPercentage,
        id = product.id,
        images = product.images,
        price = product.price,
        rating = product.rating,
        stock = product.stock,
        thumbnail = product.thumbnail,
        title = product.title
    )

    fun mapProductResponseToEntityList(productsResponse: ProductsResponse): ProductListEntity = ProductListEntity(
        products = productsResponse.products.map { mapProductToEntity(it) }
    )

    fun mapCategoryListToEntity(categoriesResponse: CategoriesResponse): CategoriesEntity {
        return CategoriesEntity().apply { addAll(categoriesResponse) }
    }

}