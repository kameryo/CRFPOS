package com.example.crfpos.page.stock.add

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
import com.example.crfpos.R
import com.example.crfpos.databinding.AddStockFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStockFragment : Fragment(R.layout.add_stock_fragment) {
    private val vm: AddViewModel by viewModels()

    private var _binding: AddStockFragmentBinding? = null
    private val binding: AddStockFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = AddStockFragmentBinding.bind(view)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_add_stock, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_done -> {
                        add()
                        true
                    }
                    R.id.action_back -> {
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        vm.errorMessage.observe(viewLifecycleOwner) { msg ->
            if (msg.isEmpty()) return@observe
            Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
            vm.errorMessage.value = ""
        }

        vm.done.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

    }

    private fun add() {
        val name = binding.nameEdit.text.toString()
        val priceStr = binding.priceEdit.text.toString()
        val quantityStr = binding.quantityEdit.text.toString()

        vm.add(name, priceStr, quantityStr)
    }
}