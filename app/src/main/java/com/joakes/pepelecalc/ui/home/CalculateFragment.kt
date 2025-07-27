package com.joakes.pepelecalc.ui.home

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.joakes.pepelecalc.databinding.FragmentCalculateBinding

class CalculateFragment : Fragment() {

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

        val weightEditText: EditText = binding.peptideWeight
        weightEditText.addTextChangedListener(object: TextWatcher {

        })
        calculateViewModel.weight.observe(viewLifecycleOwner) {
            weightEditText.setText(it?.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}