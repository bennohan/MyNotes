package com.example.mynotes.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.crocodic.core.extension.openActivity
import com.crocodic.core.helper.list.EndlessScrollListener
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.data.Note
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.ui.addNote.AddActivity
import com.example.mynotes.ui.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>(R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnProfile.setOnClickListener {
            openActivity<ProfileActivity> {
                finish()
            }
        }


    }

}