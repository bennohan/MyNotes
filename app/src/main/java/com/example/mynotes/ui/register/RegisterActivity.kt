package com.example.mynotes.ui.register

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
import com.example.mynotes.databinding.ActivityRegisterBinding
import com.example.mynotes.ui.home.HomeActivity
import com.example.mynotes.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity :
    BaseActivity<ActivityRegisterBinding, RegisterViewModel>(R.layout.activity_register) {

    @Inject
    lateinit var session: CoreSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenAPI()

        binding.ivBack.setOnClickListener {
            openActivity<LoginActivity> {
                finish()
            }
        }

        binding.btnSave.setOnClickListener {
            if(binding.etName.isEmptyRequired(R.string.mustFill) || binding.etEmail.isEmptyRequired(R.string.mustFill) || binding.etPassword.isEmptyRequired(R.string.mustFill)){
                return@setOnClickListener
            }
            val name = binding.etName.textOf()
            val email = binding.etEmail.textOf()
            val password = binding.etPassword.textOf()
            val confirmPassword = binding.tvConfirmPw.textOf()

            viewModel.register(name,email,password,confirmPassword)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Register in")
                            ApiStatus.SUCCESS -> {
                                tos(it.message ?: "Berhasil Register")
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
//                            else -> loadingDialog.setResponse(it.message ?: return@collect)
                        }
                    }
                }
            }
        }

    }
    fun tokenAPI() {
//            val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                LocalDateTime.now()
//            }else{
//            }
//            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-DD|rahasia")
//            val date: String = current.format(dateFormatter)
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