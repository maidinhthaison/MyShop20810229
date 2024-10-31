package com.ql2.myshop.ui.products.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ql2.myshop.databinding.ViewHolderProductItemBinding
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.utils.AssetUtils
import com.ql2.myshop.utils.formatPriceToCurrency

internal class ProductAdapter (
    private var context: Context,
) : ListAdapter<ProductModel, ProductAdapter.ProductItemViewHolder>(
    DIFF_CALLBACK
) {

    var onClicked: ((ListProductUIEvent) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductItemViewHolder {
        val binding = ViewHolderProductItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ProductItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductAdapter.ProductItemViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        item?.let {
            holder.bind(item, position)
        }
    }

    internal inner class ProductItemViewHolder(private val binding: ViewHolderProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel, position: Int) {
            binding.tvProName.text = item.productName
            binding.tvProPrice.text = formatPriceToCurrency(item.importPrice)
            binding.tvProDescription.text= item.description
            AssetUtils.loadImageFromAssets(context = context,
                fileName = item.getImages()[0], binding.ivProductThumb)
            binding.root.setOnClickListener {
                onClicked?.invoke(ListProductUIEvent.OnItemClicked(item))
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductModel>() {
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

sealed class ListProductUIEvent() {
    data class OnItemClicked(val productItem: ProductModel) : ListProductUIEvent()
}
