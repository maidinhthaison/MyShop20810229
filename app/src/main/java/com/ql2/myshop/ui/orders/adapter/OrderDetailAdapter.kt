package com.ql2.myshop.ui.orders.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ql2.myshop.R
import com.ql2.myshop.databinding.ViewHolderOrderDetailItemBinding
import com.ql2.myshop.domain.model.orders.OrderDetailModel
import com.ql2.myshop.utils.AssetUtils
import com.ql2.myshop.utils.StringExt
import com.ql2.myshop.utils.formatPriceToCurrency

internal class OrderDetailAdapter (
    private var context: Context,
) : ListAdapter<OrderDetailModel, OrderDetailAdapter.OrderDetailItemViewHolder>(
    DIFF_CALLBACK
) {

    var onClicked: ((ListOrderDetailUIEvent) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailAdapter.OrderDetailItemViewHolder {
        val binding = ViewHolderOrderDetailItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return OrderDetailItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrderDetailAdapter.OrderDetailItemViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item, position)

    }

    internal inner class OrderDetailItemViewHolder(private val binding: ViewHolderOrderDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("StringFormatMatches")
        fun bind(item: OrderDetailModel, position: Int) {

            binding.tvProName.text = item.productName
            binding.tvProPrice.text = String.format(context.getString(R.string.label_unit_price),
                formatPriceToCurrency(item.unitSalePrice))
            binding.tvQuantity.text = String.format(context.getString(R.string.label_quantity), item.quantity)
            binding.tvTotalPrice.text = String.format(context.getString(R.string.label_total_price)
                ,formatPriceToCurrency(item.totalPrice))
            AssetUtils.loadImageFromAssets(context = context,
                fileName = StringExt.splitString(item.productImage)[0], binding.ivProductThumb)

            binding.root.setOnClickListener {
                onClicked?.invoke(ListOrderDetailUIEvent.OnItemClicked(item))
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OrderDetailModel>() {
            override fun areItemsTheSame(oldItem: OrderDetailModel, newItem: OrderDetailModel): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(oldItem: OrderDetailModel, newItem: OrderDetailModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

sealed class ListOrderDetailUIEvent {
    data class OnItemClicked(val orderItemModel: OrderDetailModel) : ListOrderDetailUIEvent()
}
