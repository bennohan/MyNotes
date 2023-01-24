package com.example.mynotes.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.base.BaseViewModel
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import com.example.mynotes.databinding.ActivityProfileBinding
import com.example.mynotes.databinding.ActivityRegisterBinding
import com.example.mynotes.ui.editProfile.EditProfileActivity
import com.example.mynotes.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ProfileActivity :
    BaseActivity<ActivityProfileBinding, ProfileViewModel>(R.layout.activity_profile) {

    @Inject
    lateinit var userDao: UserDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.btnEditProfile.setOnClickListener {
            openActivity<EditProfileActivity> {
                finish()
            }
        }
        binding.btnLogout.setOnClickListener {
            viewModel.logout {
                openActivity<LoginActivity>()
                finish()
            }

        }

        lifecycleScope.launch {
            viewModel.getUser.observe(this@ProfileActivity) {
                binding.user = it
            }
        }
    }
}