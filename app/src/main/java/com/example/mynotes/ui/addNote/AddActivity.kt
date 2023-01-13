package com.example.mynotes.ui.addNote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.databinding.ActivityAddBinding
import com.example.mynotes.ui.home.HomeActivity

class AddActivity : BaseActivity<ActivityAddBinding,AddViewModel>(R.layout.activity_add) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivBack.setOnClickListener {
            openActivity<HomeActivity> {
                finish()
            }
        }

    }
}