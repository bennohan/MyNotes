package com.example.mynotes.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.mynotes.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    //Splash Function
    fun splash(done: () -> Unit) = viewModelScope.launch {
        delay(4000)
        done()
    }
}