package com.example.productsapp.presentation.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.productsapp.R
import com.example.productsapp.databinding.ProductCardBinding
import com.example.productsapp.domain.entities.ProductEntity
interface OnClickListener {
    fun onClickProduct(productEntity: ProductEntity)
}
class ProductAdapter(val clickListener: OnClickListener) : ListAdapter<ProductEntity, ProductViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val binding = holder.binding
        val context = holder.itemView.context
        val productItem = getItem(position)
        val dpValue = 5
        val scale: Float = context.resources.displayMetrics.density
        val pxValue = (dpValue * scale + 0.5f).toInt()
        with(binding) {
            with(productItem) {
                tvTitle.text = title
                tvDescription.text = description
                tvPrice.text = context.getString(R.string.price, price.toString())
                Glide.with(root).load(thumbnail).apply(RequestOptions.bitmapTransform(RoundedCorners(pxValue))).into(ivThumbnail)
                tvFullPrice.paintFlags = tvFullPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvFullPrice.text = context.getString(R.string.price, (price / (1 - discountPercentage / 100)).toInt().toString())
                tvDiscountPrecent.text = context.getString(R.string.disc, discountPercentage.toString())
                root.setOnClickListener {
                    clickListener.onClickProduct(productItem)
                }
            }
        }
    }
}