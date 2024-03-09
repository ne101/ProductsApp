package com.example.productsapp.di

import com.example.productsapp.data.api.ApiFactory
import com.example.productsapp.data.api.ApiService
import com.example.productsapp.data.repositoryImpl.RepositoryImpl
import com.example.productsapp.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepositoryImpl(impl: RepositoryImpl): Repository

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService
    }

}