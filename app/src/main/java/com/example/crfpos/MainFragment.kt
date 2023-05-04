package com.example.crfpos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.crfpos.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = MainFragmentBinding.bind(view)

        binding.buttonSales.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_salesFragment)
        }

        binding.buttonStock.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_stockFragment)
        }
    }
}