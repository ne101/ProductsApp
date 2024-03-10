package com.example.productsapp.data.api

import com.example.productsapp.data.models.CategoriesResponse
import com.example.productsapp.data.models.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products?limit=20")
    suspend fun loadProductList(@Query("skip") skip: Int): ProductsResponse
    @GET("products/category/{category}")
    suspend fun loadProductListWithCategory(@Path("category") category: String): ProductsResponse
    @GET("products/categories")
    suspend fun loadCategoryList(): CategoriesResponse

}