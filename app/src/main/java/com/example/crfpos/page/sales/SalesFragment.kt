package com.example.crfpos.page.sales

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.ProductAdapter
import com.example.crfpos.R
import com.example.crfpos.databinding.SalesFragmentBinding
import com.example.crfpos.medel.product.Product

class SalesFragment : Fragment(R.layout.sales_fragment) {

    private var _binding: SalesFragmentBinding? = null
    private val binding: SalesFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = SalesFragmentBinding.bind(view)

        val productList = listOf<Product>(
            Product("A", 10),
            Product("B", 20),
            Product("C", 30),
            Product("D", 40)
        )

        binding.recycler.adapter = ProductAdapter(productList)
        binding.recycler.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

    }

}