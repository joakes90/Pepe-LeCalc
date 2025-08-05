package com.joakes.pepelecalc.ui.convert

import android.icu.text.DecimalFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class MeasurementType {
    MILLILITRES,
    MILLIGRAMS,
    MICROLITRES,
    MICROGRAMS
}
class ConvertViewModel : ViewModel() {

    // This is just mg
    private val _trueValue = MutableLiveData<Double?>().apply {
        value = null
    }
    private var _measurementType: MeasurementType? = null

    private val decimalFragment = DecimalFormat("#.####")
    private val _millilitres = MediatorLiveData<String?>().apply {
        fun update() {
            if (_trueValue.value == null) {
                value = ""
            } else if (_measurementType != MeasurementType.MILLILITRES) {
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
            } else if (_measurementType != MeasurementType.MILLIGRAMS) {
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
            } else if (_measurementType != MeasurementType.MICROLITRES) {
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
            } else if (_measurementType != MeasurementType.MICROGRAMS) {
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

    fun setTrueValue(value: Double?, fromField: MeasurementType) {
        val oldValue = _trueValue.value
        if (oldValue != value) {
            _measurementType = fromField
            _trueValue.value = value
        }
    }
}