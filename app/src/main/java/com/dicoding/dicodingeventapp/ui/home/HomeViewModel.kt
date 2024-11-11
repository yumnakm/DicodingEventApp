package com.dicoding.dicodingeventapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingeventapp.data.remote.response.EventResponse
import com.dicoding.dicodingeventapp.data.remote.response.ListEventsItem
import com.dicoding.dicodingeventapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _upcoming = MutableLiveData<List<ListEventsItem>>()
    val upcoming: LiveData<List<ListEventsItem>> = _upcoming

    private val _finished = MutableLiveData<List<ListEventsItem>>()
    val finished: LiveData<List<ListEventsItem>> = _finished

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG_UPCOMING = "UpcomingViewModel"
        private const val TAG_FINISHED = "FinishedViewModel"
        private const val UPCOMING_ID = 1
        private const val FINISHED_ID = 0
    }

    init {
        findUpcoming()
        findFinished()
    }

    private fun findUpcoming() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getActiveEvents(UPCOMING_ID)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _upcoming.value = response.body()?.listEvents?.take(5)
                } else {
                    Log.e(TAG_UPCOMING, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG_UPCOMING, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun findFinished() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFinishedEvents(FINISHED_ID)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _finished.value = response.body()?.listEvents?.take(5)
                } else {
                    Log.e(TAG_FINISHED, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG_FINISHED, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
