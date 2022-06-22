package com.example.cryptoapp_kotlin.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoDataList(
    @SerializedName("Data")
    @Expose()
    val data: List<Datum>? = null
)