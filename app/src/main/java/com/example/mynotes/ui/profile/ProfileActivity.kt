package com.example.mynotes.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.base.BaseViewModel
import com.example.mynotes.databinding.ActivityProfileBinding
import com.example.mynotes.databinding.ActivityRegisterBinding

class ProfileActivity : BaseActivity<ActivityProfileBinding,ProfileViewModel>(R.layout.activity_profile) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}