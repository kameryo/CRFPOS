package com.example.crfpos.page.record.edit


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.databinding.EditRecordItemBinding
import com.example.crfpos.model.selected.PendingPurchase

class EditRecordAdapter : ListAdapter<PendingPurchase, EditRecordAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = EditRecordItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recordGoods = getItem(position)
        holder.bindTo(recordGoods)
    }

    class ViewHolder(
        private val binding: EditRecordItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(recordGoods: PendingPurchase) {
            binding.name.text = recordGoods.name
            binding.unitPrice.text = recordGoods.unitPrice.toString() + "円"
            binding.numOfSales.text = recordGoods.numOfOrder.toString() + "個"
            binding.amount.text = recordGoods.amount.toString() + "円"
        }
    }

    companion object {
        private val callback = object : DiffUtil.ItemCallback<PendingPurchase>() {
            override fun areItemsTheSame(
                oldItem: PendingPurchase,
                newItem: PendingPurchase
            ): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(
                oldItem: PendingPurchase,
                newItem: PendingPurchase
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}