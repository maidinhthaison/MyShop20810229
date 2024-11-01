package com.ql2.myshop.ui.orders.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ql2.myshop.R
import com.ql2.myshop.databinding.ViewHolderOrderItemBinding
import com.ql2.myshop.domain.model.orders.OrdersModel
import com.ql2.myshop.utils.formatDateTimeServer
import com.ql2.myshop.utils.formatPriceToCurrency

internal class ListOrderAdapter (
    private var context: Context,
) : ListAdapter<OrdersModel, ListOrderAdapter.OrderItemViewHolder>(
    DIFF_CALLBACK
) {

    var onClicked: ((ListOrderUIEvent) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListOrderAdapter.OrderItemViewHolder {
        val binding = ViewHolderOrderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ListOrderAdapter.OrderItemViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item, position)

    }

    internal inner class OrderItemViewHolder(private val binding: ViewHolderOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrdersModel, position: Int) {
            binding.tvOrderId.text = item.orderId
            binding.tvOrderPrice.text = formatPriceToCurrency(item.finalPrice)
            binding.tvOrderDate.text= item.createdTime?.let {
                formatDateTimeServer(
                    it
                )
            }
            binding.tvOrderStatus.text = item.orderStatus
            val id: Int = when(item.orderStatus){
                "Paid" -> R.color.malachite
                "Created" -> R.color.blue
                else -> R.color.red
            }
            binding.tvOrderStatus.setTextColor(context.getColor(id))
            binding.root.setOnClickListener {
                onClicked?.invoke(ListOrderUIEvent.OnItemClicked(item))
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OrdersModel>() {
            override fun areItemsTheSame(oldItem: OrdersModel, newItem: OrdersModel): Boolean {
                return oldItem.orderId == newItem.orderId
            }

            override fun areContentsTheSame(oldItem: OrdersModel, newItem: OrdersModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

sealed class ListOrderUIEvent {
    data class OnItemClicked(val orderItemModel: OrdersModel) : ListOrderUIEvent()
}
