package com.example.mynotes.ui.profile

import android.os.Bundle
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.data.UserDao
import com.example.mynotes.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileActivity :
    BaseActivity<ActivityProfileBinding, ProfileViewModel>(R.layout.activity_profile) {

    @Inject
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

}
    }