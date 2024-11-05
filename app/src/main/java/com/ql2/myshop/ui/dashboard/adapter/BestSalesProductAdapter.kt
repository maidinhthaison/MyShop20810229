package com.ql2.myshop.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ql2.myshop.R
import com.ql2.myshop.databinding.ViewHolderAnalyticProductBinding
import com.ql2.myshop.domain.model.dashboard.BestSalesProductModel
import com.ql2.myshop.utils.AssetUtils
import com.ql2.myshop.utils.formatPriceToCurrency

internal class BestSalesProductAdapter (
    private var context: Context,
) : ListAdapter<BestSalesProductModel, BestSalesProductAdapter.BestSalesItemViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BestSalesProductAdapter.BestSalesItemViewHolder {
        val binding = ViewHolderAnalyticProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return BestSalesItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BestSalesProductAdapter.BestSalesItemViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        item?.let {
            holder.bind(item, position)
        }
    }

    internal inner class BestSalesItemViewHolder(private val binding: ViewHolderAnalyticProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BestSalesProductModel, position: Int) {
            binding.tvProName.text = item.productName
            binding.tvProPrice.text = formatPriceToCurrency(item.importPrice)
            when(item.totalSales){
                "1" -> binding.tvQuantity.text = String.format(
                    context.getString(R.string.label_bestSales_product_quantity),
                    item.totalSales)
                else -> binding.tvQuantity.text =  String.format(
                    context.getString(R.string.label_bestSales_product_quantity_units),
                    item.totalSales)
            }
            AssetUtils.loadImageFromAssets(context = context,
                fileName = item.getImages()[0], binding.ivProductThumb)

        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BestSalesProductModel>() {
            override fun areItemsTheSame(oldItem: BestSalesProductModel, newItem: BestSalesProductModel): Boolean {
                return oldItem.productId == newItem.productId
            }


            override fun areContentsTheSame(oldItem: BestSalesProductModel, newItem: BestSalesProductModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}