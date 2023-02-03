package com.example.mynotes.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.extension.toList
import com.example.mynotes.api.ApiService
import com.example.mynotes.base.BaseViewModel
import com.example.mynotes.data.Note
import com.example.mynotes.data.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val gson: Gson
) : BaseViewModel() {
//    private val _note: Channel<List<Note?>> = Channel()
//    val note =_note.receiveAsFlow()

    var note = MutableLiveData<List<Note>>()

    fun getNote(
        search: String? = null
    ) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver({ apiService.getNote(search) }, false, object : ApiObserver.ResponseListener {
            override suspend fun onSuccess(response: JSONObject) {
                val data =
                        response.getJSONArray(ApiCode.DATA).toList<Note>(gson)
                 note.postValue(data)
            }

            override suspend fun onError(response: ApiResponse) {
                super.onError(response)
            }

        })

    }

    fun deleteNote() = viewModelScope.launch {

    }

}





