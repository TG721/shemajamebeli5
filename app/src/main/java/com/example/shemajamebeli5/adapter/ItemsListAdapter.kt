package com.example.shemajamebeli5.adapter

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shemajamebeli5.databinding.ItemBinding
import com.example.shemajamebeli5.model.Items
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ItemsListAdapter(): ListAdapter<Items.Content, ItemsListAdapter.ItemVewHolder>(ItemDiffCallback()) {

    inner class ItemVewHolder(private val binding: ItemBinding,  val parent: ViewGroup ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind( ) {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                Glide.with(this.textInputEditText)
                    .load(source.icon)
                    .into(this.imageView)
                textInputEditText.hint = source.hint
                when(source.keyboard){
                    "text" -> {textInputEditText.inputType = InputType.TYPE_CLASS_TEXT}
                    "number" -> {textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER}
                    else -> {}
                }
                textInputEditText.id = source.id
                if( source.fieldType == "chooser") textInputEditText.inputType = InputType.TYPE_NULL
                if(source.required) textInputlayout.helperText = "Required"
                else textInputlayout.helperText = ""
                imageView.setOnClickListener() {
                    when(bindingAdapterPosition){
                        4 -> {
                            val c = Calendar.getInstance()
                            val year = c.get(Calendar.YEAR)
                            val month = c.get(Calendar.MONTH)
                            val day = c.get(Calendar.DAY_OF_MONTH)
                            val dcpopup = DatePickerDialog(parent.context,DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                                val months = monthOfYear+1
                                binding.textInputEditText.setText("$monthOfYear/$dayOfMonth/$year")
                            }, year, month, day)
                            dcpopup.show()
                        }
                        5 -> {
                            var selectedItemIndex = 0
                            val arrItems = arrayOf("Male", "Female")
                            var selectedItem = arrItems[selectedItemIndex]
                            MaterialAlertDialogBuilder(parent.context)
                                .setTitle("Select gender")
                                .setSingleChoiceItems(arrItems, selectedItemIndex) { dialog, which ->
                                    selectedItemIndex = which
                                    selectedItem = arrItems[which]
                                }
                                .setPositiveButton("Ok") { dialog, which ->
                                    textInputEditText.setText(selectedItem)
                                }
                                .setNeutralButton("Cancel"){dialog, which ->

                                }.show()

                        }
                        else -> {}
                    }
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVewHolder {
        return ItemVewHolder(
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ,parent)
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
