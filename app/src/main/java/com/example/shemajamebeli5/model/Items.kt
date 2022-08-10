package com.example.shemajamebeli5.model

import com.google.gson.annotations.SerializedName

data class Items(
    val content: List<Content>
) {
    data class Content(
        @SerializedName("field_id")
        val id: Int,
        @SerializedName("field_type")
        val fieldType: String,
        val hint: String,
        val icon: String,
        @SerializedName("is_active")
        val isActive: Boolean,
        val keyboard: String,
        val required: Boolean
    )

}