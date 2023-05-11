package com.example.crfpos.page.sales

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.databinding.GoodsSelectedItemBinding
import com.example.crfpos.model.request.Request

class RequestAdapter(
    private val listener: (Request) -> Unit
) : ListAdapter<Request, RequestAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = GoodsSelectedItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val viewHolder = ViewHolder(view)

        view.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val order = getItem(position)
            listener(order)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)
        holder.bindTo(order)
    }


    class ViewHolder(
        private val binding: GoodsSelectedItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(request: Request) {
            binding.price.text = request.stockPrice.toString()
            binding.productName.text = request.stockName
            binding.purchases.text = request.numOfOrder.toString()
        }
    }


    companion object {
        private val callback = object : DiffUtil.ItemCallback<Request>() {
            override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Request, newItem: Request): Boolean {
                return oldItem == newItem
            }

        }
    }
}