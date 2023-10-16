package com.example.crfpos.page.sales

import android.graphics.Color
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.R
import com.example.crfpos.databinding.SalesFragmentBinding
import com.example.crfpos.model.calculater.Calculator
import com.example.crfpos.model.record.Record
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SalesFragment : Fragment(R.layout.sales_fragment) {
    private val vm: SalesViewModel by viewModels()

    private val calculator = Calculator()

    private var _binding: SalesFragmentBinding? = null
    private val binding: SalesFragmentBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setFragmentResultListener("confirm") {_, data ->
//            val which = data.getInt("result")
//            if (which == DialogInterface.BUTTON_POSITIVE) {
//                vm.addRecord(data)
//            }
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = SalesFragmentBinding.bind(view)

        refresh()

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

                    R.id.action_refresh -> {
                        refresh()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // observe ViewModel's values
        lifecycleScope.launch {
            vm.adultNum.collect { adultNum ->
                binding.adultNum.text = adultNum.toString()
                resetAdultButtonColor()
                when (adultNum) {
                    0 -> binding.adult0.setBackgroundColor(Color.CYAN)
                    1 -> binding.adult1.setBackgroundColor(Color.CYAN)
                    2 -> binding.adult2.setBackgroundColor(Color.CYAN)
                    3 -> binding.adult3.setBackgroundColor(Color.CYAN)
                    4 -> binding.adult4.setBackgroundColor(Color.CYAN)
                }
            }
        }

        lifecycleScope.launch {
            vm.childNum.collect { childNum ->
                binding.childNum.text = childNum.toString()
                resetChildButtonColor()
                when (childNum) {
                    0 -> binding.child0.setBackgroundColor(Color.CYAN)
                    1 -> binding.child1.setBackgroundColor(Color.CYAN)
                    2 -> binding.child2.setBackgroundColor(Color.CYAN)
                    3 -> binding.child3.setBackgroundColor(Color.CYAN)
                    4 -> binding.child4.setBackgroundColor(Color.CYAN)
                }
            }
        }

        lifecycleScope.launch {
            vm.subtotalFare.collect { subtotalFare ->
                binding.subtotalFare.text = "${subtotalFare}円"
            }
        }

        // setup onClickListeners
        binding.adult0.setOnClickListener { vm.updateAdultNum(0) }
        binding.adult1.setOnClickListener { vm.updateAdultNum(1) }
        binding.adult2.setOnClickListener { vm.updateAdultNum(2) }
        binding.adult3.setOnClickListener { vm.updateAdultNum(3) }
        binding.adult4.setOnClickListener { vm.updateAdultNum(4) }
        binding.adultInput.setOnClickListener {
            binding.editAdultNum.text.toString().toIntOrNull()?.let { vm.updateAdultNum(it) }
        }

        binding.child0.setOnClickListener { vm.updateChildNum(0) }
        binding.child1.setOnClickListener { vm.updateChildNum(1) }
        binding.child2.setOnClickListener { vm.updateChildNum(2) }
        binding.child3.setOnClickListener { vm.updateChildNum(3) }
        binding.child4.setOnClickListener { vm.updateChildNum(4) }
        binding.childInput.setOnClickListener {
            binding.editChildNum.text.toString().toIntOrNull()?.let { vm.updateChildNum(it) }
        }

        val stockListAdapter = StockListAdapter { stock ->
            // すでにリストにあったら追加されないようにする。
            val requestList = vm.requestList.value
            val isNameInRequest = requestList?.any { it.stockName == stock.name }
            if (isNameInRequest == false) {
                vm.addRequest(stock)
            }
        }

        binding.stockListRecycler.layoutManager =
            GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)

        binding.stockListRecycler.adapter = stockListAdapter

        //これがないと表示されない
        vm.stockList.observe(viewLifecycleOwner) { stock ->
            stockListAdapter.submitList(stock)
        }

        val requestAdapter = RequestAdapter({
            vm.delete(it)
        }, {
            vm.incrementRequest(it)
        }, {
            vm.decrementRequest(it)
        })

        binding.goodsSelected.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.goodsSelected.adapter = requestAdapter

        vm.requestList.observe(viewLifecycleOwner) { order ->
            requestAdapter.submitList(order)
            updateGoodsSubTotal()
        }

        vm.errorMessage.observe(viewLifecycleOwner) { msg ->
            if (msg.isEmpty()) return@observe
            Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
            vm.errorMessage.value = ""
        }

        binding.adjustment.setOnClickListener {
            val fareSales = calculator.calFare(vm.adultNum.value, vm.childNum.value)
            val goodsSales = calculator.calGoodsSubTotal(vm.requestList.value)
            val total = fareSales + goodsSales
            if (total != 0) {
                val record = Record(
                    time = System.currentTimeMillis() / 1000,
                    total = fareSales + goodsSales,
                    fareSales = fareSales,
                    otherSales = 0,
                    goodsSales = goodsSales,
                    adult = vm.adultNum.value,
                    child = vm.childNum.value,
                    requestList = vm.requestList.value,
                    memo = ""
                )
                vm.addRecord(record)
            }


            refresh()
//            findNavController().navigate(
//                R.id.action_salesFragment_to_salesConfirmDialogFragment
//            )
        }


    }

    private fun refresh() {
        vm.deleteAll()
        vm.updateAdultNum(0)
        vm.updateChildNum(0)
        binding.editAdultNum.text.clear()
        binding.editChildNum.text.clear()
    }


    private fun resetAdultButtonColor() {
        binding.adult0.setBackgroundColor(Color.WHITE)
        binding.adult1.setBackgroundColor(Color.WHITE)
        binding.adult2.setBackgroundColor(Color.WHITE)
        binding.adult3.setBackgroundColor(Color.WHITE)
        binding.adult4.setBackgroundColor(Color.WHITE)
    }

    private fun resetChildButtonColor() {
        binding.child0.setBackgroundColor(Color.WHITE)
        binding.child1.setBackgroundColor(Color.WHITE)
        binding.child2.setBackgroundColor(Color.WHITE)
        binding.child3.setBackgroundColor(Color.WHITE)
        binding.child4.setBackgroundColor(Color.WHITE)
    }

    private fun updateGoodsSubTotal() {
        var text: String = calculator.calGoodsSubTotal(vm.requestList.value).toString()
        text += " 円"
        binding.subtotalGoods.text = text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}
