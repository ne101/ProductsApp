package com.example.productsapp.data.models

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("products")
    val products: List<Product>,
)