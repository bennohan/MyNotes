package com.example.mynotes.ui.profile

import androidx.lifecycle.viewModelScope
import com.example.mynotes.api.ApiService
import com.example.mynotes.base.BaseViewModel
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val gson: Gson
) : BaseViewModel() {

    private val _user = kotlinx.coroutines.channels.Channel<List<User>>()
    val user = _user.receiveAsFlow()

    val getUser = userDao.getUser()

    //Logout Function
    fun logout(logout: () -> Unit) =viewModelScope.launch {
        userDao.deleteAll()
        logoutSuccess()
        logout()
    }

}

