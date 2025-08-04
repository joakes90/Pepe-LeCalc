package com.joakes.pepelecalc.ui.convert

import android.os.Bundle
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

//        Milligrams input
        val milligrams: EditText = binding.milligramsInput
        convertViewModel.milligrams.observe(viewLifecycleOwner) {
            milligrams.setText(it.toString())
        }

//        Microlitres input
        val microlitres: EditText = binding.microlitresInput
        convertViewModel.microlitres.observe(viewLifecycleOwner) {
            microlitres.setText(it.toString())
        }

//        Micrograms input
        val micrograms: EditText = binding.microgramsInput
        convertViewModel.micrograms.observe(viewLifecycleOwner) {
            micrograms.setText(it.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}