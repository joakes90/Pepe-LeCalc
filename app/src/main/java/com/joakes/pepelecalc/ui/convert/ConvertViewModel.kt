package com.joakes.pepelecalc.ui.convert

import android.icu.text.DecimalFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConvertViewModel : ViewModel() {

    // This is just mg
    private val _trueValue = MutableLiveData<Double?>().apply {
        value = null
    }
    private val decimalFragment = DecimalFormat("#.####")
    private val _millilitres = MediatorLiveData<String?>().apply {
        fun update() {
            if (_trueValue.value == null) {
                value = ""
            } else {
                _trueValue.value?.let { newValue ->
                    value = decimalFragment.format (newValue * 0.001).toString()
                }
            }
        }
        addSource(_trueValue) { update() }
    }

    private val _milligrams = MediatorLiveData<String?>().apply {
        fun update() {
            if (_trueValue.value == null) {
                value = ""
            } else {
                _trueValue.value?.let { newValue ->
                    value = decimalFragment.format(newValue).toString()
                }
            }
        }
        addSource(_trueValue) { update() }
    }

    private val _microlitres = MediatorLiveData<String?>().apply {
        fun update() {
            if (_trueValue.value == null) {
                value = ""
            } else {
                _trueValue.value?.let { newValue ->
                    value = decimalFragment.format(newValue).toString()
                }
            }
        }
        addSource(_trueValue) { update() }
    }
    private val _micrograms = MediatorLiveData<String?>().apply {
        fun update() {
            if (_trueValue.value == null) {
                value = ""
            } else {
                _trueValue.value?.let { newValue ->
                    value = decimalFragment.format(newValue * 1000).toString()
                }
            }
        }
        addSource(_trueValue) { update() }
    }

    val millilitres: LiveData<String?> = _millilitres
    val milligrams: LiveData<String?> = _milligrams
    val microlitres: LiveData<String?> = _microlitres
    val micrograms: LiveData<String?> = _micrograms

    fun setTrueValue(value: Double?) {
        val oldValue = _trueValue.value
        if (oldValue != value) {
            _trueValue.value = value
        }
    }
}