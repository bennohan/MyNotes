package com.example.mynotes.ui.addNote

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.example.mynotes.api.ApiService
import com.example.mynotes.base.BaseViewModel
import com.example.mynotes.data.UserDao
import com.example.mynotes.data.constant.Cons
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val apiService: ApiService,
    private val session: CoreSession,
    private val gson: Gson,
    private val userDao: UserDao
) : BaseViewModel() {
    //Create Note
    fun createNote(title:String,content:String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver({apiService.createNote(title, content)},
        false,
        object : ApiObserver.ResponseListener {
            override suspend fun onSuccess(response: JSONObject) {
                _apiResponse.send(ApiResponse().responseSuccess("create Note Success"))
            }
            override suspend fun onError(response: ApiResponse) {
                super.onError(response)
                _apiResponse.send(ApiResponse().responseError())
            }
        }
        )
    }

    //Update Note
    fun updateNote(title:String, content:String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver({apiService.updateNote(title,content)},
        false,
            object : ApiObserver.ResponseListener{
                override suspend fun onSuccess(response: JSONObject) {

                    _apiResponse.send(ApiResponse().responseSuccess(" Note Updated"))
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.send(ApiResponse().responseError())
                }
            }
        )
    }

    fun getToken() {
        viewModelScope.launch {
            ApiObserver(
                block = { apiService.getToken() },
                toast = false,
                responseListener = object : ApiObserver.ResponseListener {
                    override suspend fun onSuccess(response: JSONObject) {
                        val token = response.getString("token")
                        session.setValue(Cons.TOKEN.API_TOKEN, token)

                    }

                    override suspend fun onError(response: ApiResponse) {
                        super.onError(response)
                    }
                })

        }

    }

}