package com.joakes.pepelecalc.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculateViewModel : ViewModel() {

    private val _weight = MutableLiveData<Int?>().apply {
        value = null
    }

    var weight: MutableLiveData<Int?> = _weight
}