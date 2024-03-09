package com.example.productsapp.presentation

import android.app.Application
import com.example.productsapp.di.DaggerApplicationComponent

class ProductApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.create()
    }

}