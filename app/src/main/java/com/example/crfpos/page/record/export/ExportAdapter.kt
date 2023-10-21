package com.example.crfpos.page.record.export

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.databinding.ExportItemBinding
import com.example.crfpos.model.record.RecordDao

class ExportAdapter(
    private val listener: (RecordDao.Summary) -> Unit
) : ListAdapter<RecordDao.Summary, ExportAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ExportItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val viewHolder = ViewHolder(view)

        view.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val summary = getItem(position)
            listener(summary)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val summary = getItem(position)
        holder.bindTo(summary)
    }


    class ViewHolder(
        private val binding: ExportItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(summary: RecordDao.Summary) {
            binding.date.text = summary.date
            binding.numOfSales.text = summary.numOfSales.toString() + "件"
            binding.salesAll.text = summary.salesAll.toString() + "円"
            binding.salesRail.text = summary.salesRail.toString() + "円"
            binding.salesGoods.text = summary.salesGoods.toString() + "円"
            binding.numOfPerson.text = summary.numOfPerson.toString() + "人"
        }
    }


    companion object {
        private val callback = object : DiffUtil.ItemCallback<RecordDao.Summary>() {
            override fun areItemsTheSame(
                oldItem: RecordDao.Summary,
                newItem: RecordDao.Summary
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RecordDao.Summary,
                newItem: RecordDao.Summary
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}