package com.example.productsapp.di

import com.example.productsapp.presentation.fragments.DetailInfoFragment
import com.example.productsapp.presentation.fragments.ProductFragment
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(fragment: ProductFragment)
    fun inject(fragment: DetailInfoFragment)
}