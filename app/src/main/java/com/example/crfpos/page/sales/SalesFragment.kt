package com.example.crfpos.page.sales

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.R
import com.example.crfpos.databinding.SalesFragmentBinding
import com.example.crfpos.model.calculater.Calculator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SalesFragment : Fragment(R.layout.sales_fragment) {
    private val vm: SalesViewModel by viewModels()

    private var _binding: SalesFragmentBinding? = null
    private val binding: SalesFragmentBinding get() = _binding!!

    private var adultNum: Int = 0
    private var childNum: Int = 0

    private val calculator = Calculator()

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

        binding.adultNum.text = adultNum.toString()
        binding.childNum.text = childNum.toString()

        binding.adult0.setOnClickListener { updateAdultNum(0) }
        binding.adult1.setOnClickListener { updateAdultNum(1) }
        binding.adult2.setOnClickListener { updateAdultNum(2) }
        binding.adult3.setOnClickListener { updateAdultNum(3) }
        binding.adult4.setOnClickListener { updateAdultNum(4) }

        binding.child0.setOnClickListener { updateChildNum(0) }
        binding.child1.setOnClickListener { updateChildNum(1) }
        binding.child2.setOnClickListener { updateChildNum(2) }
        binding.child3.setOnClickListener { updateChildNum(3) }
        binding.child4.setOnClickListener { updateChildNum(4) }

        val stockListAdapter = StockListAdapter {
        }

//        binding.recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recycler.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        binding.recycler.adapter = stockListAdapter

        //これがないと表示されない
        vm.stockList.observe(viewLifecycleOwner) { stock ->
            stockListAdapter.submitList(stock)
        }
    }

    private fun updateAdultNum(adultNum: Int) {
        this.adultNum = adultNum
        binding.adultNum.text = adultNum.toString()
        updateFee()
    }

    private fun updateChildNum(childNum: Int) {
        this.childNum = childNum
        binding.childNum.text = childNum.toString()
        updateFee()
    }

    private fun updateFee() {
        var text: String = calculator.calFee(adultNum, childNum).toString()
        text += " 円"
        binding.sum.text = text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}