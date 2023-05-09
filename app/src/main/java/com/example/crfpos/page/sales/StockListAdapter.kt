package com.example.crfpos.page.sales

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.databinding.GoodsItemBinding
import com.example.crfpos.model.stock.Stock

//import kotlinx.coroutines.NonCancellable.message

class StockListAdapter(
    private val listener: (Stock) -> Unit
) : ListAdapter<Stock, StockListAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = GoodsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val viewHolder = ViewHolder(view)

        view.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val stock = getItem(position)
            listener(stock)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = getItem(position)
        holder.bindTo(stock)
    }

    class ViewHolder(
        private val binding: GoodsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(stock: Stock) {
            binding.productNameText.text = stock.name
            val priceText = stock.price.toString() + " 円"
            binding.priceText.text = priceText
//            binding.quantity.text = stock.quantity.toString()
        }
    }

    companion object {
        private val callback = object : DiffUtil.ItemCallback<Stock>() {
            override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
                return oldItem == newItem
            }

        }
    }

}