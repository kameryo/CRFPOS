package com.example.crfpos.page.stock.coupon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crfpos.databinding.CouponItemBinding
import com.example.crfpos.model.coupon.Coupon

class CouponAdapter(
    private val listener: (Coupon) -> Unit
) : ListAdapter<Coupon, CouponAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CouponItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val viewHolder = ViewHolder(view)

        view.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val coupon = getItem(position)
            listener(coupon)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coupon = getItem(position)
        holder.bindTo(coupon)
    }

    class ViewHolder(
        private val binding: CouponItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(coupon: Coupon) {
            binding.name.text = coupon.name
            binding.price.text = coupon.price.toString()
            binding.remain.text = coupon.remain.toString()
        }
    }

    companion object {
        private val callback = object : DiffUtil.ItemCallback<Coupon>() {
            override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
                return oldItem == newItem
            }

        }
    }

}