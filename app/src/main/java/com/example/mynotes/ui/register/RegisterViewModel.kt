package com.example.mynotes.ui.register

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
class RegisterViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val gson: Gson,
    private val session: CoreSession,
    private val observer: BaseObserver
) : BaseViewModel() {

    // Register Function
    fun register(
        name: String,
        phone: String,
        password: String,
        confirmPassword: String
    ) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        observer(
            block = { apiService.register(name, phone, password,confirmPassword) },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.send(ApiResponse().responseSuccess("Berhasil Register"))
                    val token = response.getString("token")
                    session.setValue(Cons.TOKEN.API_TOKEN, token)

                }
                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.send(ApiResponse().responseError())
                }
            }
        )
    }

    //Get Token Function
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