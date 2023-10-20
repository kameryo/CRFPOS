package com.example.crfpos.page.records

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
import com.example.crfpos.databinding.RecordFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordFragment : Fragment(R.layout.record_fragment) {
    private val vm: RecordViewModel by viewModels()

    private var _binding: RecordFragmentBinding? = null
    private val binding: RecordFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = RecordFragmentBinding.bind(view)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_records, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_home -> {
                        findNavController().popBackStack()
                        true
                    }

                    R.id.action_add -> {
                        findNavController().popBackStack()
                        true
                    }

                    R.id.action_export -> {
                        findNavController().navigate(R.id.action_recordsFragment_to_exportFragment)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val adapter = RecordAdapter {

        }
        binding.recordRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recordRecycler.adapter = adapter

        vm.recordList.observe(viewLifecycleOwner) { record ->
            adapter.submitList(record)
        }
    }
}