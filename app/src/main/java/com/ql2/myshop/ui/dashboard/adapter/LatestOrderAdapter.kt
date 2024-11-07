package com.ql2.myshop.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ql2.myshop.R
import com.ql2.myshop.databinding.ViewHolderLatestOrderItemBinding
import com.ql2.myshop.domain.model.dashboard.LatestOrderModel
import com.ql2.myshop.utils.formatPriceToCurrency

internal class LatestOrderAdapter (
    private var context: Context,
) : ListAdapter<LatestOrderModel, LatestOrderAdapter.LatestOrderItemViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LatestOrderAdapter.LatestOrderItemViewHolder {
        val binding = ViewHolderLatestOrderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return LatestOrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LatestOrderAdapter.LatestOrderItemViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        item?.let {
            holder.bind(item, position)
        }
    }

    internal inner class LatestOrderItemViewHolder(private val binding: ViewHolderLatestOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LatestOrderModel, position: Int) {
            binding.tvOrderId.text = String.format(context.getString(R.string.label_latest_order_id),
                item.orderItemId)
            binding.tvCreatedDate.text = String.format(context.getString(R.string.label_latest_order_createdTime),
                item.toDateTime())
            binding.tvFinalPrice.text = String.format(context.getString(R.string.label_latest_order_finalPrice),
                formatPriceToCurrency(item.finalPrice)
            )
            binding.tvOrderStatus.text = item.orderStatus
            val id: Int = when(item.orderStatus){
                "Paid" -> R.color.malachite
                "Created" -> R.color.blue
                else -> R.color.red
            }
            binding.tvOrderStatus.setTextColor(context.getColor(id))


        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LatestOrderModel>() {
            override fun areItemsTheSame(oldItem: LatestOrderModel, newItem: LatestOrderModel): Boolean {
                return oldItem.orderItemId == newItem.orderItemId
            }


            override fun areContentsTheSame(oldItem: LatestOrderModel, newItem: LatestOrderModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}