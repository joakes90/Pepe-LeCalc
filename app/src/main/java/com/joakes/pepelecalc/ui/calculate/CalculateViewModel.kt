package com.joakes.pepelecalc.ui.calculate

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculateViewModel : ViewModel() {

    private val _syringeVolume = MutableLiveData<Int>().apply {
        value = 100
    }

    private val _peptideWeight = MutableLiveData<Float?>().apply {
        value = null
    }

    private val _dilutantVolume = MutableLiveData<Float?>().apply {
        value = null
    }

    private val _dosage = MutableLiveData<Float?>().apply {
        value = null
    }

    private val _calculatedUnits = MediatorLiveData<Int?>().apply {
        // Function to perform the calculation
        fun calculate() {
            val weight = _peptideWeight.value
            val vol = _dilutantVolume.value
            val dose = _dosage.value

            // Ensure all values are present and vol/weight are not zero to avoid errors
            if (weight != null && weight != 0.toFloat() && vol != null && vol != 0.toFloat() && dose != null) {
                val concentration = weight / vol
                value =
                    ((dose / concentration) * 100).toInt()
            } else {
                value = null // Or some default error/placeholder value
            }
        }

        // Add sources to observe
        addSource(_peptideWeight) { calculate() }
        addSource(_dilutantVolume) { calculate() }
        addSource(_dosage) { calculate() }
        addSource(_syringeVolume) { calculate() }
    }

    private val _calculatedUnitsString: MediatorLiveData<String> =
        MediatorLiveData<String>().apply {
            fun generateString() {
                val units = _calculatedUnits.value
                value = if (units != null) { "Draw $units units" } else { "" }
            }

            addSource(_calculatedUnits) { generateString() }
        }

        var syringeVolume: MutableLiveData<Int> = _syringeVolume
        var peptideWeight: MutableLiveData<Float?> = _peptideWeight
        var dilutantVolume: MutableLiveData<Float?> = _dilutantVolume
        var dosage: MutableLiveData<Float?> = _dosage
        var calculatedUnits: MediatorLiveData<Int?> = _calculatedUnits
        var calculatedUnitsString: MediatorLiveData<String> = _calculatedUnitsString

        fun updateSyringeVolume(newVolume: Int) {
            _syringeVolume.value = newVolume
        }

        fun updatePeptideWeight(newWeight: Float?) {
            _peptideWeight.value = newWeight
        }

        fun updateDilutantVolume(newVolume: Float?) {
            _dilutantVolume.value = newVolume
        }

        fun updateDosage(newDosage: Float?) {
            _dosage.value = newDosage
        }
    }