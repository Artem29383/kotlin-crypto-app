package com.example.cryptoapp_kotlin.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class CoinPriceInfoRawData(
    @SerializedName("Raw")
    @Expose()
    val coinPriceJson: JSONObject? = null
)