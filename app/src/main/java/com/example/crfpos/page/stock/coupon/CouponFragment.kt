package com.example.crfpos.page.stock.coupon

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
import com.example.crfpos.databinding.CouponFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CouponFragment : Fragment(R.layout.coupon_fragment) {
    private val vm: CouponViewModel by viewModels()

    private var _binding: CouponFragmentBinding? = null
    private val binding: CouponFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = CouponFragmentBinding.bind(view)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_coupon, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_home -> {
                        findNavController().popBackStack()
                        true
                    }
                    R.id.action_add -> {
                        findNavController().navigate(R.id.action_couponFragment_to_addCouponFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val adapter = CouponAdapter {
//            val action = GoodsFragmentDirections.actionGoodsFragmentToEditGoodsFragment(it)
//            findNavController().navigate(action)
        }

        binding.couponRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.couponRecycler.adapter = adapter

        vm.couponList.observe(viewLifecycleOwner) { coupon ->
            adapter.submitList(coupon)
        }

    }
}