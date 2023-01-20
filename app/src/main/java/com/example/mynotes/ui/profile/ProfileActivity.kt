package com.example.mynotes.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.base.BaseViewModel
import com.example.mynotes.data.UserDao
import com.example.mynotes.databinding.ActivityProfileBinding
import com.example.mynotes.databinding.ActivityRegisterBinding
import com.example.mynotes.ui.editProfile.EditProfileActivity
import javax.inject.Inject

class ProfileActivity : BaseActivity<ActivityProfileBinding,ProfileViewModel>(R.layout.activity_profile) {

    @Inject
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userDao.getUser().observe(this){
            binding.user
        }

        binding.lEditProfile.setOnClickListener {
            openActivity<EditProfileActivity> {
                finish()
            }
        }
    }
}