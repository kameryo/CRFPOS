package com.example.crfpos.page.stock.goods

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crfpos.R
import com.example.crfpos.databinding.GoodsFragmentBinding
import com.example.crfpos.page.stock.goods.GoodsFragmentDirections.Companion.actionGoodsFragmentToEditGoodsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoodsFragment : Fragment(R.layout.goods_fragment) {
    private val vm: GoodsViewModel by viewModels()

    private var _binding: GoodsFragmentBinding? = null
    private val binding: GoodsFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = GoodsFragmentBinding.bind(view)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_goods, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_home -> {
                        findNavController().popBackStack()
                        true
                    }
                    R.id.action_add -> {
                        findNavController().navigate(R.id.action_goodsFragment_to_addGoodsFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val adapter = GoodsAdapter {
            val action = actionGoodsFragmentToEditGoodsFragment(it)
            findNavController().navigate(action)
        }

        binding.goodsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.goodsRecycler.adapter = adapter

        vm.goodsList.observe(viewLifecycleOwner) { stock ->
            adapter.submitList(stock)
        }

    }
}