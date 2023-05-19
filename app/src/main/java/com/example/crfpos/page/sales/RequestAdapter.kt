package com.example.crfpos.page.sales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.databinding.GoodsSelectedItemBinding
import com.example.crfpos.model.request.Request

class RequestAdapter(
    private val listener: (Request) -> Unit,
    private val plusListener: (Request) -> Unit,
    private val minusListener: (Request) -> Unit

) : ListAdapter<Request, RequestAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = GoodsSelectedItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val viewHolder = ViewHolder(view)

        view.deleteButton.setOnSingleClickListener {
            listener(getItem(viewHolder.bindingAdapterPosition))
        }

        view.plusButton.setOnClickListener {
            plusListener(getItem(viewHolder.bindingAdapterPosition))
        }

        view.minusButton.setOnClickListener {
            minusListener(getItem(viewHolder.bindingAdapterPosition))
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
            val priceText = (request.stockPrice * request.numOfOrder).toString() + " 円"
            binding.price.text = priceText
            binding.productName.text = request.stockName
            binding.purchases.text = request.numOfOrder.toString()
        }
    }

    /**
     * 短時間での連打による複数回実行を防ぐ setOnClickListener 実装.
     *
     * @param listener setOnClickListener
     */
    private fun View.setOnSingleClickListener(listener: () -> Unit) {
        val delayMillis = 1000 // 二度押しを防止する時間
        var pushedAt = 0L
        setOnClickListener {
            if (System.currentTimeMillis() - pushedAt < delayMillis) return@setOnClickListener
            pushedAt = System.currentTimeMillis()
            listener()
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