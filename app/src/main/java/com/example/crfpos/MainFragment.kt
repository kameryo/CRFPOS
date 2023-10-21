package com.example.crfpos

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

        binding.buttonRecords.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_recordFragment)
        }

        // salesFragmentだけComposeのTopAppBarを出すので、ActionBarを隠す
        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.salesFragment -> (activity as? AppCompatActivity)?.supportActionBar?.hide()
                else -> (activity as? AppCompatActivity)?.supportActionBar?.show()
            }
        }
    }
}
