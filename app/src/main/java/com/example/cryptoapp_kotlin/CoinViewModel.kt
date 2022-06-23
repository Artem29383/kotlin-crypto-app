package com.example.cryptoapp_kotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp_kotlin.api.ApiFactory
import com.example.cryptoapp_kotlin.database.AppDatabase
import com.example.cryptoapp_kotlin.pojo.CoinPriceInfo
import com.example.cryptoapp_kotlin.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application): AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getCoinsInfo()
            .map { main -> main.data?.map { it.coinInfo?.name }?.joinToString(",") }
            .flatMap { it?.let { ApiFactory.apiService.getFullPriceList(fSyms = it) } }
            .map { getPriceListFromRawData(it) }
//            .delaySubscription(10, TimeUnit.SECONDS)
//            .repeat()
//            .retry()
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let { db.coinPriceInfoDao().insertPriceList(it) }
                Log.d("DATA_LOGGER", it.toString())
            }, {
                it.message?.let { it1 -> Log.d("ERROR_DATA_LOGGER", it1) }
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo>? {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return null
        for (coinKey in jsonObject.keySet()) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            for (currencyKey in currencyJson.keySet()) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }

        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}