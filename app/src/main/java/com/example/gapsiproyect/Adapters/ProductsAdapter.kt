package com.example.gapsiproyect.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gapsiproyect.daos.ProductsDao
import com.example.gapsiproyect.databinding.ProductItemBinding
import com.squareup.picasso.Picasso

class ProductsAdapter : PagingDataAdapter <ProductsDao,ProductsAdapter.ViewHolderAdapter>(ITEM_COMPARATOR) {

    inner class ViewHolderAdapter(val binding:ProductItemBinding ) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         * @bind Uses Item : @TransactionsDao to asing the Data to the View By DataBinding ->  @dataItem
         */
        fun bind(item: ProductsDao?) {

            item.apply {
                binding.dataItem = item
                Picasso.get().load(item?.image_url).into(binding.imgProductImage)
                binding.executePendingBindings()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ViewHolderAdapter(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderAdapter, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.bind(repoItem)
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<ProductsDao>() {
            override fun areItemsTheSame(oldItem: ProductsDao, newItem: ProductsDao): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProductsDao, newItem: ProductsDao): Boolean =
                oldItem == newItem
        }
    }

}



