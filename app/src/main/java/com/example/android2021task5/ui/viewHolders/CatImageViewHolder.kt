package com.example.android2021task5.ui.viewHolders

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.example.android2021task5.data.models.CatImage
import com.example.android2021task5.databinding.CatItemViewBinding
import com.example.android2021task5.ui.adapters.ICatImageListener

class CatImageViewHolder(
    private val binding: CatItemViewBinding,
    private val listener: ICatImageListener,
    private val resources: Resources
): RecyclerView.ViewHolder(binding.root) {

    fun bind(catImage: CatImage) {
        binding.textId.text = catImage.id
    }

}