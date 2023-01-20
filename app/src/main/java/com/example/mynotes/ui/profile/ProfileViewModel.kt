package com.example.mynotes.ui.profile

import androidx.lifecycle.viewModelScope
import com.example.mynotes.api.ApiService
import com.example.mynotes.base.BaseViewModel
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val gson: Gson
) : BaseViewModel() {

//    private val _user = Channel<List<User>>()
//
//    fun getProfile(
//        name: String,
//        email: String
//    ) = viewModelScope.launch {
//        val idUser = userDao.().id




    }

