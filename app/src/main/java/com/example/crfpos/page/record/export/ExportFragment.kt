package com.example.crfpos.page.record.export

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
import com.example.crfpos.databinding.ExportFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExportFragment : Fragment(R.layout.export_fragment) {
    private val vm: ExportViewModel by viewModels()

    private var _binding: ExportFragmentBinding? = null

    private val binding: ExportFragmentBinding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this._binding = ExportFragmentBinding.bind(view)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_summary, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_back -> {
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val adapter = ExportAdapter {
            val action =
                ExportFragmentDirections.actionExportFragmentToExportConfirmFragment(it.date)
            findNavController().navigate(action)
        }


        binding.exportRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.exportRecycler.adapter = adapter

        vm.dateList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }

}