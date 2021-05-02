package com.bharath.codeJournals.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bharath.codeJournals.api.ApiService
import com.bharath.codeJournals.model.AlbumDetailsModel
import com.bharath.codeJournals.model.AlbumsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by Bharath KM on 2/5/21.
 */
class MainActivityViewModel: ViewModel() {

    private val kbApiService = ApiService()
    private val disposable = CompositeDisposable()
    private val _dataModelObject = MutableLiveData<List<AlbumsModel>>()
    val dataModelObject: LiveData<List<AlbumsModel>> = _dataModelObject
    private val _dataModelObject2 = MutableLiveData<List<AlbumDetailsModel>>()
    val dataModelObject2: LiveData<List<AlbumDetailsModel>> = _dataModelObject2

    fun getAlbums() {
        disposable.add(
            kbApiService.getAlbums()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<AlbumsModel>>() {
                    override fun onSuccess(latestStats: List<AlbumsModel>) {
                        if (latestStats.isNullOrEmpty().not()) _dataModelObject.value = latestStats
                    }

                    override fun onError(e: Throwable) {
                        Log.d("HomeViewModel", "onError: ${e.localizedMessage}")
                    }

                })
        )
    }

    fun getAlbumDetails(albumId : String) {
        disposable.add(
            kbApiService.getAlbumMetadata(albumId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<AlbumDetailsModel>>() {
                    override fun onSuccess(latestStats: List<AlbumDetailsModel>) {
                        if (latestStats.isNullOrEmpty().not()) _dataModelObject2.value = latestStats
                    }

                    override fun onError(e: Throwable) {
                        Log.d("HomeViewModel", "onError: ${e.localizedMessage}")
                    }

                })
        )
    }
}