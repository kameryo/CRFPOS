package com.example.crfpos.page.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.databinding.RecordItemBinding
import com.example.crfpos.model.record.Record
import java.text.SimpleDateFormat
import java.util.Date

import java.util.Locale

class RecordAdapter(
    private val listener: (Record) -> Unit
) : ListAdapter<Record, RecordAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecordItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val viewHolder = ViewHolder(view)

        view.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val record = getItem(position)
            listener(record)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = getItem(position)
        holder.bindTo(record)
    }


    class ViewHolder(
        private val binding: RecordItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        //        @RequiresApi(Build.VERSION_CODES.N)
        fun bindTo(record: Record) {
            binding.time.text = convertUnixTimeToDateTime(record.time)
            binding.total.text = record.total.toString() + "円"
            binding.adult.text = record.adult.toString() + "人"
            binding.child.text = record.child.toString() + "人"
            binding.personSum.text = (record.adult + record.child).toString() + "人"
            binding.fareSales.text = record.fareSales.toString() + "円"
            binding.goodsSales.text = record.goodsSales.toString() + "円"
            binding.otherSales.text = record.otherSales.toString() + "円"


        }

        //        @RequiresApi(Build.VERSION_CODES.N)
        private fun convertUnixTimeToDateTime(unixTime: Long): String {
            val date = Date(unixTime * 1000)
            val formatter = SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.getDefault())
            return formatter.format(date)
        }
    }


    companion object {
        private val callback = object : DiffUtil.ItemCallback<Record>() {
            override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
                return oldItem == newItem
            }

        }
    }
}