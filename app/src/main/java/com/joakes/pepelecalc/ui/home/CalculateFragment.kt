package com.joakes.pepelecalc.ui.home

import android.content.res.Resources
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

class CalculateFragment : Fragment() {

    private companion object {
        const val TICK_WIDTH = 3 // 2dp
        const val TICK_HEIGHT_MAJOR = 60 // 30dp
        const val TICK_HEIGHT_MINOR = 30 // 15dp
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
        val calculateViewModel = ViewModelProvider(this).get(CalculateViewModel::class.java)
        _binding = FragmentCalculateBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val tickMarksContainer = binding.tickMarksContainer

        // Syringe Volume Selection
        val syringeVolumeGroup = binding.syringeVolumeGroup
        syringeVolumeGroup.setOnCheckedChangeListener { _, checkedId ->
            val syringeVolume = when (checkedId) {
                R.id.volume30 -> calculateViewModel.syringeVolume.value = 30
                R.id.volume50 -> calculateViewModel.syringeVolume.value = 50
                R.id.volume100 -> calculateViewModel.syringeVolume.value = 100
                // Add more cases for other volume options
                else -> {
                    calculateViewModel.syringeVolume.value = 100
                }
            }
            drawTickMarks(tickMarksContainer, calculateViewModel.syringeVolume.value ?: 100)
        }
        // Peptide Weight Input
        val weightEditText: EditText = binding.peptideWeight
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val newWeight = s?.toString()?.toIntOrNull()
                val currentViewModelValue = calculateViewModel.weight.value
                if (newWeight != currentViewModelValue) {
                    calculateViewModel.weight.value = newWeight
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        }
        weightEditText.addTextChangedListener(textWatcher)
        calculateViewModel.weight.observe(viewLifecycleOwner) {
            weightEditText.setText(it?.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun drawTickMarks(tickMarksContainer: LinearLayout, maxUnits: Int) {
        tickMarksContainer.removeAllViews()
        val majorInterval = 5
        val minorInterval = 1

        tickMarksContainer.post {
            val containerWidth = tickMarksContainer.width
            val totalTicks = maxUnits + 1

            val totalTickWidth = totalTicks * TICK_WIDTH
            val availableSpace = containerWidth - totalTickWidth
            val spaceBetweenTicks = availableSpace / maxUnits.toFloat()

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
        spaceBetweenTicks: Float,
        isFirst: Boolean
    ): View {
        return View(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                TICK_WIDTH,
                if (isMajor) TICK_HEIGHT_MAJOR else TICK_HEIGHT_MINOR
            ).apply {
                if (!isFirst) {
                    marginStart = spaceBetweenTicks.toInt()
                }
            }
            setBackgroundColor(if (isMajor) Color.BLACK else Color.GRAY)
        }
    }
}