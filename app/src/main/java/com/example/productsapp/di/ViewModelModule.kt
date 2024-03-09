package com.example.productsapp.di

import androidx.lifecycle.ViewModel
import com.example.productsapp.presentation.viewmodels.DetailInfoViewModel
import com.example.productsapp.presentation.viewmodels.ProductViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    fun bindProductViewModel(viewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailInfoViewModel::class)
    fun bindDetailInfoViewModel(viewModel: DetailInfoViewModel): ViewModel

}