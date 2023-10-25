package com.example.crfpos.page.stock

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.crfpos.R
import com.example.crfpos.databinding.StockFragmentBinding

class StockFragment : Fragment(R.layout.stock_fragment) {

    private var _binding: StockFragmentBinding? = null
    private val binding: StockFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = StockFragmentBinding.bind(view)

        binding.buttonGoods.setOnClickListener {
            findNavController().navigate(R.id.action_stockFragment_to_goodsFragment)
        }

        binding.buttonCoupon.setOnClickListener {
            findNavController().navigate(R.id.action_stockFragment_to_couponFragment)

        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}