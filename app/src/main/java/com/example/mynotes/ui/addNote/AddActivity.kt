package com.example.mynotes.ui.addNote

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.extension.tos
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.data.Note
import com.example.mynotes.data.constant.Cons
import com.example.mynotes.databinding.ActivityAddBinding
import com.example.mynotes.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>(R.layout.activity_add) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val note : Note? = intent.getParcelableExtra(Cons.NOTE.NOTE)

        binding.data = note


        //Back Button
        binding.ivBack.setOnClickListener {
            openActivity<HomeActivity> {
                finish()
            }
        }

//        //btn eddit
//        binding.btnEdit.setOnClickListener {
//            val title = binding.etTitle.textOf()
//            val content = binding.etContent.textOf()
//            viewModel.updateNote(title, content)
//        }

        //Button Save
        binding.ivCheck.setOnClickListener {

            if (binding.etTitle.isEmptyRequired(R.string.mustFill) || binding.etContent.isEmptyRequired(
                    R.string.mustFill
                )
            ) {
                return@setOnClickListener
            }
            val title = binding.etTitle.textOf()
            val content = binding.etContent.textOf()

            viewModel.createNote(title, content)
//            viewModel.updateNote(title, content)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("creating note")
                            ApiStatus.SUCCESS -> {
                                tos(it.message ?: "Note Created")
                                loadingDialog.dismiss()

                            }
                            ApiStatus.ERROR -> {
                                disconnect(it)
                                tos("Creating Note Failed")
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
}