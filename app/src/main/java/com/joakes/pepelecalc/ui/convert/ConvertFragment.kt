package com.joakes.pepelecalc.ui.convert

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.joakes.pepelecalc.databinding.FragmentConvertBinding

class ConvertFragment : Fragment() {

    private var _binding: FragmentConvertBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val convertViewModel =
            ViewModelProvider(this).get(ConvertViewModel::class.java)

        _binding = FragmentConvertBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        Millilitres input
        val millilitres: EditText = binding.millilitresInput
        convertViewModel.millilitres.observe(viewLifecycleOwner) {
            millilitres.setText(it.toString())
        }
        millilitres.addTextChangedListener(createTextWatcher(millilitres, convertViewModel))

//        Milligrams input
        val milligrams: EditText = binding.milligramsInput
        convertViewModel.milligrams.observe(viewLifecycleOwner) {
            milligrams.setText(it.toString())
        }
        milligrams.addTextChangedListener(createTextWatcher(milligrams, convertViewModel))

//        Microlitres input
        val microlitres: EditText = binding.microlitresInput
        convertViewModel.microlitres.observe(viewLifecycleOwner) {
            microlitres.setText(it.toString())
        }
        microlitres.addTextChangedListener(createTextWatcher(microlitres, convertViewModel))

//        Micrograms input
        val micrograms: EditText = binding.microgramsInput
        convertViewModel.micrograms.observe(viewLifecycleOwner) {
            micrograms.setText(it.toString())
        }
        micrograms.addTextChangedListener(createTextWatcher(micrograms, convertViewModel))

        return root
    }

    private fun createTextWatcher(editText: EditText, viewModel: ConvertViewModel): TextWatcher {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().toDoubleOrNull()
                when (editText) {
                    binding.millilitresInput -> {
                        val newValue = input?.times(1000)
                        viewModel.setTrueValue(newValue, MeasurementType.MILLILITRES)
                    }

                    binding.milligramsInput -> {
                        viewModel.setTrueValue(input, MeasurementType.MILLIGRAMS)
                    }

                    binding.microlitresInput -> {
                        viewModel.setTrueValue(input, MeasurementType.MICROLITRES)
                    }

                    binding.microgramsInput -> {
                        val newValue = input?.times(0.001)
                        viewModel.setTrueValue(newValue, MeasurementType.MICROGRAMS)
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
}