package com.example.mynotes.ui.login

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.*
import com.crocodic.core.helper.DateTimeHelper
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.data.constant.Cons
import com.example.mynotes.databinding.ActivityLoginBinding
import com.example.mynotes.ui.home.HomeActivity
import com.example.mynotes.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    @Inject
    lateinit var session: CoreSession

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        tokenAPI()

        //Register Button
        binding.btnRegister.setOnClickListener {
            openActivity<RegisterActivity> {
                finish()
            }
        }

        //Login Button
        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.isEmptyRequired(R.string.mustFill) || binding.etPassword.isEmptyRequired(R.string.mustFill)
            ) {
                return@setOnClickListener
            }
            val email = binding.etEmail.textOf()
            val password = binding.etPassword.textOf()

            viewModel.login(email, password)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("login")
                            ApiStatus.SUCCESS -> {
                                tos(it.message ?: "sukses")
                                loadingDialog.dismiss()
                                openActivity<HomeActivity>()
                                finish()
                            }
                            ApiStatus.ERROR -> {
                                disconnect(it)
                            }
                            ApiStatus.EXPIRED -> {

                            }
                            else -> loadingDialog.setResponse("Else")
                        }
                    }
                }
            }
        }

    }

    //Token Api Function
    fun tokenAPI() {
        val dateNow = DateTimeHelper().dateNow()
        val tokenInit = "$dateNow|rahasia"
        val tokenEncrypt = tokenInit.base64encrypt()
        session.setValue(Cons.TOKEN.API_TOKEN, tokenEncrypt)

        Timber.d("Check Token : $tokenInit")

        lifecycleScope.launch {
            viewModel.getToken()
        }

    }
}