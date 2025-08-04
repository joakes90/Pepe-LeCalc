package com.joakes.pepelecalc.ui.convert

import android.icu.text.DecimalFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConvertViewModel : ViewModel() {

    // This is just mg
    private var _trueValue = 2.5
    private val decimalFragment = DecimalFormat("#.####")
    private val _millilitres = MutableLiveData<String>().apply {
        value = decimalFragment.format (_trueValue * 0.001).toString()
    }

    private val _milligrams = MutableLiveData<String>().apply {
        value = decimalFragment.format(_trueValue).toString()
    }

    private val _microlitres = MutableLiveData<String>().apply {
        value = decimalFragment.format(_trueValue).toString()
    }
    private val _micrograms = MutableLiveData<String>().apply {
        value = decimalFragment.format(_trueValue * 1000).toString()
    }

    val millilitres: LiveData<String> = _millilitres
    val milligrams: LiveData<String> = _milligrams
    val microlitres: LiveData<String> = _microlitres
    val micrograms: LiveData<String> = _micrograms

}