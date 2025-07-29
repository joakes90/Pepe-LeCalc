package com.joakes.pepelecalc.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculateViewModel : ViewModel() {

    private val _weight = MutableLiveData<Int?>().apply {
        value = null
    }
    private val _syringeVolume = MutableLiveData<Int>().apply {
        value = 30
    }

    var weight: MutableLiveData<Int?> = _weight
    var syringeVolume: MutableLiveData<Int> = _syringeVolume
}