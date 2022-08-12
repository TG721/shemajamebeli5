package com.example.shemajamebeli5.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shemajamebeli5.MainViewModel
import com.example.shemajamebeli5.MainViewModelFactory
import com.example.shemajamebeli5.MyResponseState
import com.example.shemajamebeli5.R
import com.example.shemajamebeli5.adapter.ItemsListAdapter
import com.example.shemajamebeli5.databinding.ActivityMainBinding
import com.example.shemajamebeli5.repository.Repository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private val adapter by lazy { ItemsListAdapter() }

    private val fieldsMap = mutableMapOf<Int, String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getInfo()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myState.collect {
                    when (it) {
                        is MyResponseState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is MyResponseState.Error -> {
                            binding.finalText.text = it.message
                            binding.progressBar.visibility = View.GONE
                        }
                        is MyResponseState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            adapter.submitList(it.items.content)
//                            binding.finalText.text = it.items.toString()
                        }
                        else -> {}
                    }
                }
            }

        }
        lateinit var itemView: androidx.constraintlayout.widget.ConstraintLayout
        lateinit var itemInputView: com.google.android.material.textfield.TextInputLayout
        lateinit var text: String
        lateinit var hint: String
        binding.buttonRegister.setOnClickListener {
            val size = binding.recyclerView.size
            var ind = true //whether all required fields are entered
            //if at least one required field is empty ind will become false
            //but non empty required and non required fields will be added/replace to map
            for (i in 0 until size) {
                itemView =
                    binding.recyclerView.findViewHolderForAdapterPosition(i)!!.itemView as androidx.constraintlayout.widget.ConstraintLayout
                itemInputView =
                    itemView.getChildAt(0) as com.google.android.material.textfield.TextInputLayout
                text = itemInputView.editText!!.text.toString()
                hint = itemInputView.helperText.toString()
                if (text.toString().trim() == "" && itemInputView.helperText == "Required") {
                    itemInputView.setHelperTextColor(generateColorStateList())
                    ind = false
                } else {
                    fieldsMap.put(
                        itemInputView.editText!!.id,
                        itemInputView.editText!!.text.toString()
                    )

                }

            }
            //if ind is true then show snackbar with all required or non-required fields values
            if (ind) {
                Snackbar.make(binding.rootLayout, fieldsMap.toString(), Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
                    .setTextMaxLines(4)
                    .show()
            }
        }
    }
}


// method to generate color state list programmatically
fun generateColorStateList(
    enabledColor: Int = Color.parseColor("#fc0303"), // Capri
    disabledColor: Int = Color.parseColor("#A2A2D0"), // Blue bell
    checkedColor: Int = Color.parseColor("#7BB661"), // Bud green
    uncheckedColor: Int = Color.parseColor("#A3C1AD"), // Cambridge blue
    activeColor: Int = Color.parseColor("#1034A6"), // Egyptian blue
    inactiveColor: Int = Color.parseColor("#614051"), // Eggplant
    pressedColor: Int = Color.parseColor("#FFD300"), // Cyber yellow
    focusedColor: Int = Color.parseColor("#00FFFF") // Aqua
): ColorStateList {
    val states = arrayOf(
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_enabled),
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_active),
        intArrayOf(-android.R.attr.state_active),
        intArrayOf(android.R.attr.state_pressed),
        intArrayOf(android.R.attr.state_focused)
    )
    val colors = intArrayOf(
        enabledColor, // enabled
        disabledColor, // disabled
        checkedColor, // checked
        uncheckedColor, // unchecked
        activeColor, // active
        inactiveColor, // inactive
        pressedColor, // pressed
        focusedColor // focused
    )
    return ColorStateList(states, colors)
}