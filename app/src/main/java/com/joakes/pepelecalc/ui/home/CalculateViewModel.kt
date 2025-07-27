package com.joakes.pepelecalc.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculateViewModel : ViewModel() {

    private val _weight = MutableLiveData<Int?>().apply {
        value = null
    }

    val weight: LiveData<Int?> = _weight
}