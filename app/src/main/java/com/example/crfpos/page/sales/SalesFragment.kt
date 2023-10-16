package com.example.crfpos.page.sales

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crfpos.R
import com.example.crfpos.databinding.SalesFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SalesFragment : Fragment(R.layout.sales_fragment) {
    private val vm: SalesViewModel by viewModels()

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

    @OptIn(ExperimentalFoundationApi::class)
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
                binding.subtotalFare.text = getString(R.string.yen, subtotalFare)
            }
        }

        lifecycleScope.launch {
            vm.subtotalGoods.asFlow().collect { subtotalGoods ->
                binding.subtotalGoods.text = getString(R.string.yen, subtotalGoods)
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

        binding.stockListRecycler.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val stocks = vm.stockList.asFlow().collectAsState(initial = emptyList())
                LazyVerticalStaggeredGrid(
                    columns =StaggeredGridCells.Adaptive(120.dp),
                )  {
                    items(stocks.value) { stock ->
                        GoodsItemView(
                            name = stock.name,
                            price = stock.price,
                            onClick = {
                                // すでにリストにあったら追加されないようにする。
                                val requestList = vm.requestList.value
                                val isNameInRequest = requestList?.any { it.stockName == stock.name }
                                if (isNameInRequest == false) {
                                    vm.addRequest(stock)
                                }
                            }
                        )
                    }
                }
            }
        }

        val requestAdapter = RequestAdapter(
            onClickDelete = { vm.delete(it) },
            onClickPlus = { vm.incrementRequest(it) },
            onClickMinus = { vm.decrementRequest(it) },
        )

        binding.goodsSelected.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.goodsSelected.adapter = requestAdapter

        vm.requestList.observe(viewLifecycleOwner) { order ->
            requestAdapter.submitList(order)
        }

        vm.errorMessage.observe(viewLifecycleOwner) { msg ->
            if (msg.isEmpty()) return@observe
            Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
            vm.errorMessage.value = ""
        }

        binding.adjustment.setOnClickListener {
            vm.saveRecord()
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

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}
