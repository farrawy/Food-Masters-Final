package com.example.foody.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

// I created this Extension Kotlin File so that when the user runs the app for the first time it will request
// the recipes from the api instead of reading the database and requesting at the same time

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}