package com.example.mynotes.ui.splash

import android.os.Bundle
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.data.constant.Cons
import com.example.mynotes.databinding.ActivityMainBinding
import com.example.mynotes.ui.home.HomeActivity
import com.example.mynotes.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    @Inject
    lateinit var session : CoreSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userLogin = session.getString(Cons.USER.PROFILE)

        viewModel.splash {
            if (userLogin == "Login"){
                openActivity<HomeActivity>()
            }else{
                openActivity<LoginActivity> {  }
            }
            finish()
        }
    }
}

