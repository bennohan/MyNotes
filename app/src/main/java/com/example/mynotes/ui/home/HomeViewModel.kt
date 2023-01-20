package com.example.mynotes.ui.home

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.*
import com.crocodic.core.extension.toList
import com.crocodic.core.extension.toObject
import com.example.mynotes.api.ApiService
import com.example.mynotes.base.BaseViewModel
import com.example.mynotes.data.Note
import com.example.mynotes.data.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson
) : BaseViewModel() {
    fun listNote(note: Int) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver({ apiService.getNote(note) }, false, object : ApiObserver.ResponseListener {
            override suspend fun onSuccess(response: JSONObject) {
                val status = response.getInt("api_status")
                if (status == 1) {
                    val data =
                        response.getJSONObject(ApiCode.DATA).getJSONArray("note").toList<Note>(gson)
                    val note = DataObserver(note, data)
                    _apiResponse.send(ApiResponse(status = ApiStatus.SUCCESS, data = note))
                } else {
                    val message = response.getString(ApiCode.MESSAGE)
                    _apiResponse.send(ApiResponse(status = ApiStatus.ERROR, message = message))
                }
            }

        })
    }
}