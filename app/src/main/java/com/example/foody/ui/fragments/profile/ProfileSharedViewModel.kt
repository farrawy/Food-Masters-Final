package com.example.foody.ui.fragments.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileSharedViewModel :ViewModel(){
    // shared view model to receive  Calories  and weight and glasses number values and update the UI with the new values

    private val _calories= MutableLiveData<String>("")
       val calories:LiveData<String> = _calories


    private val _weight= MutableLiveData<String>("")
    val weight:LiveData<String> = _weight

    private val _glassesNumber= MutableLiveData<String>("0")
    val glassesNumber:LiveData<String> = _glassesNumber

    private val _userName= MutableLiveData<String>("")
    val userName:LiveData<String> = _userName



     fun setCalories( cal:String)
    {
        _calories.value = "$cal Cal"
    }

     fun setWeight( we:String)
    {
        _weight.value ="$we Kg"
    }

    fun setGlassesNumber( glasses:Int)
    {
        _glassesNumber.value = glasses.toString()
    }

    fun setUserName(name:String)
    {
        _userName.value=name

    }



}