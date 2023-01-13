package com.example.mynotes.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.ui.addNote.AddActivity


class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding.btnAdd.setOnClickListener {
            openActivity<AddActivity> {
                finish()
            }
        }
    }
}