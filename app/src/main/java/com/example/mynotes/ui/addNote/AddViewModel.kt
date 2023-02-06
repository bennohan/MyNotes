package com.example.mynotes.ui.addNote

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.example.mynotes.api.ApiService
import com.example.mynotes.base.BaseObserver
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
    private val userDao: UserDao,
    private val observer: BaseObserver,
) : BaseViewModel() {

    //Create Note Function
    fun createNote(title: String, content: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        observer(
            block = { apiService.createNote(title, content) },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
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
    fun updateNote(id: String, title: String, content: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        observer(
            block = { apiService.updateNote(id, title, content) },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
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

    //Delete Note
    fun deleteNote(id: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        observer(
            block = { apiService.deleteNote(id) },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.send(ApiResponse().responseSuccess(" Note Deleted"))

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
            observer(
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