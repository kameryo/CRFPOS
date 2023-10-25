package com.example.crfpos.page.stock.goods

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.databinding.GoodsItemBinding
import com.example.crfpos.model.goods.Goods

class GoodsAdapter(
    private val listener: (Goods) -> Unit
) : ListAdapter<Goods, GoodsAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = GoodsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val viewHolder = ViewHolder(view)

        view.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val goods = getItem(position)
            listener(goods)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goods = getItem(position)
        holder.bindTo(goods)
    }

    class ViewHolder(
        private val binding: GoodsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(goods: Goods) {
            binding.name.text = goods.name
            binding.price.text = goods.price.toString()
            binding.purchases.text = goods.purchases.toString()
            binding.remain.text = goods.remain.toString()
        }
    }

    companion object {
        private val callback = object : DiffUtil.ItemCallback<Goods>() {
            override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
                return oldItem == newItem
            }

        }
    }

}