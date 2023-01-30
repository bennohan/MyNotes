package com.example.mynotes.ui.editProfile

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toObject
import com.example.mynotes.api.ApiService
import com.example.mynotes.base.BaseViewModel
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val session: CoreSession,
    private val gson: Gson
) : BaseViewModel() {

    //EditProfile
    fun UpdateProfile(name: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver(
            { apiService.updateProfile(name) },
            false,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    userDao.update(data.copy(idRoom = 1))
                    _apiResponse.send(ApiResponse().responseSuccess("Profile Updated"))
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    _apiResponse.send(ApiResponse().responseError())

                }
            }
        )
    }

//    fun getToken() {
//        viewModelScope.launch {
//            ApiObserver(
//                block = { apiService.getToken() },
//                toast = false,
//                responseListener = object : ApiObserver.ResponseListener {
//                    override suspend fun onSuccess(response: JSONObject) {
//                        val token = response.getString("token")
//                        session.setValue(Cons.TOKEN.API_TOKEN, token)
//
//                    }
//
//                    override suspend fun onError(response: ApiResponse) {
//                        super.onError(response)
//                    }
//                })
//
//        }
//
//    }

}