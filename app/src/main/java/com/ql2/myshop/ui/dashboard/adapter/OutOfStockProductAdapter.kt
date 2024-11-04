package com.ql2.myshop.ui.dashboard.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ql2.myshop.R
import com.ql2.myshop.databinding.ViewHolderAnalyticProductBinding
import com.ql2.myshop.databinding.ViewHolderProductItemBinding
import com.ql2.myshop.domain.model.dashboard.OutOfStockProductModel
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.utils.AssetUtils
import com.ql2.myshop.utils.formatPriceToCurrency
import timber.log.Timber

internal class OutOfStockProductAdapter (
    private var context: Context,
) : ListAdapter<OutOfStockProductModel,OutOfStockProductAdapter.OutOfStockItemViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OutOfStockProductAdapter.OutOfStockItemViewHolder {
        val binding = ViewHolderAnalyticProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return OutOfStockItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OutOfStockProductAdapter.OutOfStockItemViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        item?.let {
            holder.bind(item, position)
        }
    }

    internal inner class OutOfStockItemViewHolder(private val binding: ViewHolderAnalyticProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OutOfStockProductModel, position: Int) {
            binding.tvProName.text = item.productName
            binding.tvProPrice.text = formatPriceToCurrency(item.importPrice)
            binding.tvQuantity.text= String.format(
                context.getString(R.string.label_outOfStock_product_quantity),
                item.quantity.toString())
            AssetUtils.loadImageFromAssets(context = context,
                fileName = item.getImages()[0], binding.ivProductThumb)

        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OutOfStockProductModel>() {
            override fun areItemsTheSame(oldItem: OutOfStockProductModel, newItem: OutOfStockProductModel): Boolean {
                return oldItem.productId == newItem.productId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: OutOfStockProductModel, newItem: OutOfStockProductModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
