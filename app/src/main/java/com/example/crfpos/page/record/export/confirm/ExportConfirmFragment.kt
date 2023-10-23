package com.example.crfpos.page.record.export.confirm

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
import androidx.navigation.fragment.navArgs
import com.example.crfpos.R
import com.example.crfpos.databinding.ExportConfirmFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExportConfirmFragment : Fragment(R.layout.export_confirm_fragment) {
    private val vm: ExportConfirmViewModel by viewModels()

    private val args: ExportConfirmFragmentArgs by navArgs()

    private var _binding: ExportConfirmFragmentBinding? = null

    private val binding: ExportConfirmFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this._binding = ExportConfirmFragmentBinding.bind(view)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_export_confirm, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_export -> {
                        exportCSV()
                        findNavController().popBackStack()
                        true
                    }

                    R.id.action_add -> {
                        true
                    }

                    R.id.action_home -> {
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.textView.text = args.date
    }

    private fun exportCSV() {
        vm.exportRecordToCSV(args.date, context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

}