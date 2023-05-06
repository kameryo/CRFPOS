package com.example.crfpos.page.stock.edit

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.crfpos.R
import com.example.crfpos.databinding.EditStockFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditStockFragment : Fragment(R.layout.edit_stock_fragment) {
    private val vm: EditStockViewModel by viewModels()

    private val args: EditStockFragmentArgs by navArgs()

    private var _binding: EditStockFragmentBinding? = null
    private val binding: EditStockFragmentBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("confirm") { _, data ->
            val which = data.getInt("result")
            if (which == DialogInterface.BUTTON_POSITIVE) {
                vm.delete()
            }
        }

        if (savedInstanceState == null) {
            vm.stock.value = args.stock
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this._binding = EditStockFragmentBinding.bind(view)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_edit_stock, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_delete -> {
                        findNavController().navigate(
                            R.id.action_editStockFragment_to_confirmDialogFragment
                        )
                        true
                    }
                    R.id.action_done -> {
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


        vm.stock.observe(viewLifecycleOwner) { stock ->
            binding.name.text = stock.name
            binding.price.text = stock.price.toString()
            binding.quantity.text = stock.quantity.toString()
        }

        vm.deleted.observe(viewLifecycleOwner) { deleted ->
            if (deleted) {
                findNavController().popBackStack(
                    R.id.stockFragment, false
                )
            }
        }

    }


}