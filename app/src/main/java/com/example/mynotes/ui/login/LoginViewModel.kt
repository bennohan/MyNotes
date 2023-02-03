package com.example.mynotes.ui.login

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toObject
import com.example.mynotes.api.ApiService
import com.example.mynotes.base.BaseObserver
import com.example.mynotes.base.BaseViewModel
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import com.example.mynotes.data.constant.Cons
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val session: CoreSession,
    private val observer: BaseObserver,
) : BaseViewModel() {

    fun login(
        email: String,
        password: String,
    ) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        observer(
            block = { apiService.login(email, password) },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    Timber.d("DataLogin : $response")

                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    val token = response.getString("token")
                    session.setValue(Cons.TOKEN.API_TOKEN, token)
//                    Timber.d("DataUser : $data")
                    userDao.insert(data.copy(idRoom = 1))
                    _apiResponse.send(ApiResponse().responseSuccess("Sukses"))
                    session.setValue(Cons.USER.EMAIL, email)
                    session.setValue(Cons.USER.PASSWORD, password)
                    session.setValue(Cons.USER.PROFILE, "Login")

                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.send(ApiResponse().responseError())
                }
            })
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
                        _apiResponse.send(ApiResponse().responseError())

                    }
                })


        }

    }

}

