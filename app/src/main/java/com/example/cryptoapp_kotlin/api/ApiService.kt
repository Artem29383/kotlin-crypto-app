package com.example.cryptoapp_kotlin.api

import com.example.cryptoapp_kotlin.pojo.CoinInfoDataList
import com.example.cryptoapp_kotlin.pojo.CoinPriceInfoRawData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getCoinsInfo(
        @Query(QUERY_API_KEY) apiKey: String = "387992fef26004ef4a2b3da710ddf217d502acf9b9a07bee817481181a22de5f",
        @Query(QUERY_LIMIT) limit: Int = 10,
        @Query(QUERY_TO_SYMBOL) tSym: String = Currency,
    ): Single<CoinInfoDataList>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_API_KEY) apiKey: String = "387992fef26004ef4a2b3da710ddf217d502acf9b9a07bee817481181a22de5f",
        @Query(QUERY_TO_SYMBOLS) tSyms: String = Currency,
        @Query(QUERY_FROM_SYMBOLS) fSyms: String
    ): Single<CoinPriceInfoRawData>

    companion object {
        private const val QUERY_LIMIT = "limit"
        private const val QUERY_TO_SYMBOL = "tsym"
        private const val QUERY_API_KEY = "api_key"
        private const val QUERY_TO_SYMBOLS = "tsyms"
        private const val QUERY_FROM_SYMBOLS = "fsyms"


        private const val Currency = "USD"
    }
}