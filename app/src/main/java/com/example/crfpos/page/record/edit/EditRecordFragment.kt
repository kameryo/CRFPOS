package com.example.crfpos.page.record.edit

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crfpos.R
import com.example.crfpos.databinding.EditRecordFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EditRecordFragment : Fragment(R.layout.edit_record_fragment) {
    private val vm: EditRecordViewModel by viewModels()

    private val args: EditRecordFragmentArgs by navArgs()

    private var _binding: EditRecordFragmentBinding? = null

    private val binding: EditRecordFragmentBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("confirm") { _, data ->
            val which = data.getInt("result")
            if (which == DialogInterface.BUTTON_POSITIVE) {
                vm.delete()
            }
        }

        if (savedInstanceState == null) {
            vm.record.value = args.record
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this._binding = EditRecordFragmentBinding.bind(view)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_edit_stock, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_delete -> {
                        findNavController().navigate(
                            R.id.action_editRecordFragment_to_deleteRecodeConfirmDialogFragment
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


        vm.record.observe(viewLifecycleOwner) { record ->
            binding.timeText.text = convertUnixTimeToDateTime(record.time)
            binding.totalText.text = record.total.toString() + "円"
            binding.adultText.text = record.adult.toString() + "人"
            binding.childText.text = record.child.toString() + "人"
            binding.personSumText.text = (record.adult + record.child).toString() + "人"
            binding.fareSalesText.text = record.fareSales.toString() + "円"
            binding.goodsSalesText.text = record.goodsSales.toString() + "円"
        }

        vm.errorMessage.observe(viewLifecycleOwner) { msg ->
            if (msg.isEmpty()) return@observe

            Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
            vm.errorMessage.value = ""
        }

        vm.deleted.observe(viewLifecycleOwner) { deleted ->
            if (deleted) {
                findNavController().popBackStack(
                    R.id.recordFragment, false
                )
            }
        }

        val adapter = EditRecordAdapter()

        binding.recordGoodsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recordGoodsRecycler.adapter = adapter

        adapter.submitList(args.record.goodsList)
    }

    private fun convertUnixTimeToDateTime(unixTime: Long): String {
        val date = Date(unixTime * 1000)
        val formatter = SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.getDefault())
        return formatter.format(date)
    }

}