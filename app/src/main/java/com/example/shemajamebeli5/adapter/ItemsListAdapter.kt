package com.example.shemajamebeli5.adapter

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shemajamebeli5.R
import com.example.shemajamebeli5.databinding.ItemBinding
import com.example.shemajamebeli5.model.Items


class ItemsListAdapter: ListAdapter<Items.Content, ItemsListAdapter.ItemVewHolder>(ItemDiffCallback()) {

    inner class ItemVewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
//              this.textInputEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_paste, 0)
                textInputEditText.hint = source.hint
                when(source.keyboard){
                    "text" -> {textInputEditText.inputType = InputType.TYPE_CLASS_TEXT}
                    "number" -> {textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER}
                    else -> {}
                }
                textInputEditText.id = source.id
                textInputEditText.isEnabled = source.fieldType != "chooser"
                if(source.required==true) textInputlayout.helperText = "Required"
                else textInputlayout.helperText = ""

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVewHolder {
        return ItemVewHolder(
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemsListAdapter.ItemVewHolder, position: Int) {
        holder.bind()
    }
}

private class ItemDiffCallback : DiffUtil.ItemCallback<Items.Content>() {
    override fun areItemsTheSame(oldItem: Items.Content, newItem: Items.Content): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Items.Content, newItem: Items.Content): Boolean =
        oldItem == newItem

}
