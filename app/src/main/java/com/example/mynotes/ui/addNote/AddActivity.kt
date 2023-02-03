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

    private var note: Note? = null
    private var oldTitle: String? = null
    private var oldContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        note = intent.getParcelableExtra(Cons.NOTE.NOTE)


        binding.data = note

        oldTitle = note?.titile
        oldContent = note?.note


        //Back Button
        binding.ivBack.setOnClickListener {
            openActivity<HomeActivity> {
                finish()
            }
        }

        //Delete Button
        binding.btnDelete.setOnClickListener {
            note?.id?.let { id -> viewModel.deleteNote(id) }

        }

//        //Edit Button
//        binding.btnEdit.setOnClickListener {
//            val title = binding.etTitle.textOf()
//            val content = binding.etContent.textOf()
//
//            if (title.isEmpty()) {
//                tos("Judul tidak boleh kosong")
//                return@setOnClickListener
//            }
//
//            if (content.isEmpty()) {
//                tos("Note tidak boleh kosong")
//                return@setOnClickListener
//            }
//        }

        //Button Save
        binding.ivCheck.setOnClickListener {

            val title = binding.etTitle.textOf()
            val content = binding.etContent.textOf()

            if (binding.etTitle.isEmptyRequired(R.string.mustFill) ||
                binding.etContent.isEmptyRequired(
                    R.string.mustFill
                )
            ) {
                return@setOnClickListener
            }
            if (oldTitle.isNullOrEmpty() && oldContent.isNullOrEmpty()) {
                viewModel.createNote(title, content)
            } else {
                if (note != null) {
                    if (oldTitle != title || oldContent != content) {
                        viewModel.updateNote(note!!.id.toString(), title, content)
                    }
                }


            }
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