package com.example.crfpos.page.stock.goods.edit.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class ConfirmDialogFragment : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity()).apply {
            setMessage("この商品を削除しますか")
            setPositiveButton(android.R.string.ok, listener)
            setNegativeButton(android.R.string.cancel, listener)
        }.create()
    }

    private val listener = DialogInterface.OnClickListener {_, which ->
        setFragmentResult("confirm", bundleOf("result" to which))
    }
}