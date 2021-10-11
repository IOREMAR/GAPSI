package com.example.gapsiproyect.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gapsiproyect.daos.Results
import com.example.gapsiproyect.databinding.ProductItemBinding
import com.squareup.picasso.Picasso


class ProductsAdapter : PagingDataAdapter <Results,ProductsAdapter.ViewHolderAdapter>(ITEM_COMPARATOR) {

    lateinit var clickItem: (Item: Results) -> Unit

    inner class ViewHolderAdapter(val binding:ProductItemBinding ) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         * @bind Uses Item : @TransactionsDao to asing the Data to the View By DataBinding ->  @dataItem
         */
        fun bind(item: Results?) {
            item.apply {
                binding.dataItem = item
               if(item?.backdropPath != null) {
                   Picasso.get().load(IMAGE_URL + item?.backdropPath).into(binding.imgProductImage)
               }

                binding.savemovie.setOnClickListener {
                    clickItem.invoke(item!!)
                }
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

        private val IMAGE_URL = "https://image.tmdb.org/t/p/original"

        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Results>() {
            override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean =
                oldItem == newItem
        }
    }

}



