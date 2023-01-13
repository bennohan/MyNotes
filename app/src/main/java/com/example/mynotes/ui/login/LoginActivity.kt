package com.example.mynotes.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.databinding.ActivityLoginBinding
import com.example.mynotes.databinding.ActivityRegisterBinding
import com.example.mynotes.ui.home.HomeActivity
import com.example.mynotes.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding,LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnRegister.setOnClickListener {
            openActivity<RegisterActivity> {
                finish()
            }
        }
        binding.btnLogin.setOnClickListener {
            openActivity<HomeActivity> {
                finish()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect{
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("login")
                            ApiStatus.SUCCESS ->{
                                loadingDialog.dismiss()
                                openActivity<HomeActivity>()
                                finish()
                            }
                            else -> loadingDialog.setResponse(it.message?:return@collect)
                        }
                    }
                }
            }
        }

    }
}