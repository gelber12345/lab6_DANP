package com.example.lab6.blood

import com.google.gson.annotations.SerializedName

data class Blood (
    @SerializedName("id")
    val id: Int ,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("type")
    val type: String ,
    @SerializedName("rh_factor")
    val rh_factor: String,
    @SerializedName("group")
    val group: String ,
)