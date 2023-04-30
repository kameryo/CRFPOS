package com.example.crfpos.page.sales

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
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

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_sales, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_home -> {
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

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

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding  = null
    }
}