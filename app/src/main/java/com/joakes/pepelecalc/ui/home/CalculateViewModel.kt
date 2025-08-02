package com.joakes.pepelecalc.ui.home

import androidx.lifecycle.MediatorLiveData
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

    private val _calculatedUnits = MediatorLiveData<Int?>().apply {
        // Function to perform the calculation
        fun calculate() {
            val weight = _peptideWeight.value
            val vol = _dilutantVolume.value
            val dose = _dosage.value

            // Ensure all values are present and vol/weight are not zero to avoid errors
            if (weight != null && weight != 0 && vol != null && vol != 0 && dose != null) {
                // Consider using floating point numbers for precision if needed
                value = ((dose.toDouble() / vol.toDouble()) / weight.toDouble() * vol.toDouble()).toInt()
            } else {
                value = null // Or some default error/placeholder value
            }
        }

        // Add sources to observe
        addSource(_peptideWeight) { calculate() }
        addSource(_dilutantVolume) { calculate() }
        addSource(_dosage) { calculate() }
    }

    private val _calculatedUnitsString: MediatorLiveData<String> = MediatorLiveData<String>().apply {
        fun generateString() {
            val units = _calculatedUnits.value
            value = if (units != null) { "Draw $units" } else { "" }
        }
        addSource(_calculatedUnits) { generateString() }
    }

    var syringeVolume: MutableLiveData<Int> = _syringeVolume
    var peptideWeight: MutableLiveData<Int?> = _peptideWeight
    var dilutantVolume: MutableLiveData<Int?> = _dilutantVolume
    var dosage: MutableLiveData<Int?> = _dosage
    var calculatedUnits: MutableLiveData<Int?> = _calculatedUnits

    var calculcularUnitsString: MutableLiveData<String> = _calculatedUnitsString
}