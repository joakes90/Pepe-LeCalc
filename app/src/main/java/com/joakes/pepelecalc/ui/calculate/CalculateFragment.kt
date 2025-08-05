package com.joakes.pepelecalc.ui.calculate

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.joakes.pepelecalc.R
import com.joakes.pepelecalc.databinding.FragmentCalculateBinding
import kotlin.math.roundToInt

class CalculateFragment : Fragment() {

    private companion object {
        const val TICK_WIDTH = 3 // 3dp
        const val TICK_HEIGHT_MAJOR = 60 // 30dp
        const val TICK_HEIGHT_MINOR = 30     // 15dp
    }
    private var _binding: FragmentCalculateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calculateViewModel = ViewModelProvider(this)[CalculateViewModel::class.java]
        _binding = FragmentCalculateBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val tickMarksContainer = binding.tickMarksContainer

        // Syringe Volume Selection
        val syringeVolumeGroup = binding.syringeVolumeGroup
        syringeVolumeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.volume30 -> calculateViewModel.syringeVolume.value = 30
                R.id.volume50 -> calculateViewModel.syringeVolume.value = 50
                R.id.volume100 -> calculateViewModel.syringeVolume.value = 100
                // Add more cases for other volume options
                else -> {
                    calculateViewModel.syringeVolume.value = 100
                }
            }
            val maxUnits = calculateViewModel.syringeVolume.value ?: 100
            drawTickMarks(tickMarksContainer, maxUnits)
        }

        // Peptide Weight Input
        val weightEditText: EditText = binding.peptideWeightInput
        val weightTextWatcher = createWatcher(calculateViewModel, weightEditText)
        weightEditText.addTextChangedListener(weightTextWatcher)
        calculateViewModel.peptideWeight.observe(viewLifecycleOwner) {
        }

        // Dilutant Volume Input
        val dilutionEditText: EditText = binding.diluentInput
        val dilutionTextWatcher = createWatcher(calculateViewModel, dilutionEditText)
        dilutionEditText.addTextChangedListener(dilutionTextWatcher)

        // Desired Dosage Input
        val dosageEditText: EditText = binding.dosageInput
        val dosageTextWatcher = createWatcher(calculateViewModel, dosageEditText)
        dosageEditText.addTextChangedListener(dosageTextWatcher)

        calculateViewModel.calculatedUnits.observe(viewLifecycleOwner) {
            val unitWidth = (tickMarksContainer.width / (calculateViewModel.syringeVolume.value ?: 100)) + (TICK_WIDTH/2)
            binding.syringeFill.layoutParams.width = it?.times(unitWidth) ?: 0
            binding.syringeFill.requestLayout()
        }

        // Update Measurement Text
        calculateViewModel.calculatedUnitsString.observe(viewLifecycleOwner) {
            binding.measurementText.text = it
        }

        drawTickMarks(tickMarksContainer, calculateViewModel.syringeVolume.value ?: 100)
        return root
    }

    private fun createWatcher(calculateViewModel: CalculateViewModel, field: EditText): TextWatcher {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val newValue = s?.toString()?.toFloatOrNull()
                val currentViewModelValue = when(field) {
                    binding.peptideWeightInput -> calculateViewModel.peptideWeight.value
                    binding.diluentInput -> calculateViewModel.dilutantVolume.value
                    binding.dosageInput -> calculateViewModel.dosage.value
                    else -> 0
                }
                if (newValue != currentViewModelValue) {
                    println("calculated units = ${calculateViewModel.calculatedUnits.value}")
                    when(field) {
                        binding.peptideWeightInput -> calculateViewModel.updatePeptideWeight(newValue)
                        binding.diluentInput -> calculateViewModel.updateDilutantVolume(newValue)
                        binding.dosageInput -> calculateViewModel.updateDosage(newValue)
                        else -> {}
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        return textWatcher
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun drawTickMarks(tickMarksContainer: LinearLayout, maxUnits: Int) {
        tickMarksContainer.removeAllViews()
        val majorInterval = 5

        tickMarksContainer.post {
            val containerWidth = tickMarksContainer.width
            val totalTicks = maxUnits + 1

            val totalTickWidth = totalTicks * TICK_WIDTH
            val availableSpace = containerWidth - totalTickWidth
            val spaceBetweenTicks = (availableSpace.toFloat() / maxUnits.toFloat()).roundToInt()

            for (unit in 0..maxUnits) {
                val isMajor = unit % majorInterval == 0
                val tickMark = createTickMark(
                    isMajor,
                    spaceBetweenTicks,
                    isFirst = unit == 0
                )
                tickMarksContainer.addView(tickMark)
            }
        }
    }

    private fun createTickMark(
        isMajor: Boolean,
        spaceBetweenTicks: Int,
        isFirst: Boolean
    ): View {
        return View(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                TICK_WIDTH,
                if (isMajor) TICK_HEIGHT_MAJOR else TICK_HEIGHT_MINOR
            ).apply {
                if (!isFirst) {
                    marginStart = spaceBetweenTicks
                }
            }
            setBackgroundColor(if (isMajor) Color.BLACK else Color.GRAY)
        }
    }
}