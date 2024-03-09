package com.example.productsapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.productsapp.domain.entities.ProductEntity

class ProductDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
    override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
        return oldItem == newItem
    }
}