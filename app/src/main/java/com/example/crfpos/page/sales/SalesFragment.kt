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


@AndroidEntryPoint
class SalesFragment : Fragment(R.layout.sales_fragment) {
    private val vm: SalesViewModel by viewModels()

    private var _binding: SalesFragmentBinding? = null
    private val binding: SalesFragmentBinding get() = _binding!!

    private var adultNum: Int = 0
    private var childNum: Int = 0

    private val calculator = Calculator()

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
            val fareSales = calculator.calFare(adultNum, childNum)
            val goodsSales = calculator.calGoodsSubTotal(vm.requestList.value)
            val total = fareSales + goodsSales
            if (total != 0) {
                val record = Record(
                    time = System.currentTimeMillis() / 1000,
                    total = fareSales + goodsSales,
                    fareSales = fareSales,
                    otherSales = 0,
                    goodsSales = goodsSales,
                    adult = adultNum,
                    child = childNum,
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

    private fun updateAdultNum(adultNum: Int) {
        this.adultNum = adultNum
        binding.adultNum.text = adultNum.toString()
        resetAdultButtonColor()
        when (adultNum) {
            0 -> binding.adult0.setBackgroundColor(Color.CYAN)
            1 -> binding.adult1.setBackgroundColor(Color.CYAN)
            2 -> binding.adult2.setBackgroundColor(Color.CYAN)
            3 -> binding.adult3.setBackgroundColor(Color.CYAN)
            4 -> binding.adult4.setBackgroundColor(Color.CYAN)
        }
        updateFare()
    }

    private fun resetAdultButtonColor() {
        binding.adult0.setBackgroundColor(Color.WHITE)
        binding.adult1.setBackgroundColor(Color.WHITE)
        binding.adult2.setBackgroundColor(Color.WHITE)
        binding.adult3.setBackgroundColor(Color.WHITE)
        binding.adult4.setBackgroundColor(Color.WHITE)
    }


    private fun updateChildNum(childNum: Int) {
        this.childNum = childNum
        binding.childNum.text = childNum.toString()
        resetChildButtonColor()
        when (childNum) {
            0 -> binding.child0.setBackgroundColor(Color.CYAN)
            1 -> binding.child1.setBackgroundColor(Color.CYAN)
            2 -> binding.child2.setBackgroundColor(Color.CYAN)
            3 -> binding.child3.setBackgroundColor(Color.CYAN)
            4 -> binding.child4.setBackgroundColor(Color.CYAN)
        }
        updateFare()
    }

    private fun resetChildButtonColor() {
        binding.child0.setBackgroundColor(Color.WHITE)
        binding.child1.setBackgroundColor(Color.WHITE)
        binding.child2.setBackgroundColor(Color.WHITE)
        binding.child3.setBackgroundColor(Color.WHITE)
        binding.child4.setBackgroundColor(Color.WHITE)
    }

    private fun updateFare() {
        val fareSum = calculator.calFare(adultNum, childNum)
        var text: String = fareSum.toString()
        text += " 円"
        binding.subtotalFare.text = text
    }

    private fun updateGoodsSubTotal() {
        var text: String = calculator.calGoodsSubTotal(vm.requestList.value).toString()
        text += " 円"
        binding.subtotalGoods.text = text
    }

    private fun refresh() {
        vm.deleteAll()
        updateAdultNum(0)
        updateChildNum(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}