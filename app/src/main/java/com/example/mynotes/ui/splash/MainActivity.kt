package com.example.mynotes.ui.splash

import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.databinding.ActivityMainBinding
import com.example.mynotes.ui.home.HomeActivity
import com.example.mynotes.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

class MainActivity :  BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.splash {
                openActivity<LoginActivity>(){
                    finish()
                }
            }
        }
    }

