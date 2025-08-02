package com.joakes.pepelecalc.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculateViewModel : ViewModel() {

    private val _syringeVolume = MutableLiveData<Int>().apply {
        value = 100
    }

    private val _peptideWeight = MutableLiveData<Int?>().apply {
        value = null
    }

    private val _dilutantVolume = MutableLiveData<Int?>().apply {
        value = null
    }

    private val _dosage = MutableLiveData<Int?>().apply {
        value = null
    }

    var syringeVolume: MutableLiveData<Int> = _syringeVolume
    var weight: MutableLiveData<Int?> = _peptideWeight
    var dilutantVolume: MutableLiveData<Int?> = _dilutantVolume
    var dosage: MutableLiveData<Int?> = _dosage
}