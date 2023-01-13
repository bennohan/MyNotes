package com.example.mynotes.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.databinding.ActivityRegisterBinding
import com.example.mynotes.ui.login.LoginActivity

class RegisterActivity : BaseActivity<ActivityRegisterBinding,RegisterViewModel>(R.layout.activity_register) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivBack.setOnClickListener{
            openActivity<LoginActivity> {
                finish()
            }
        }

    }
}