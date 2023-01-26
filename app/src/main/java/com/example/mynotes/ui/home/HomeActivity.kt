package com.example.mynotes.ui.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.api.DataObserver
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.base.adapter.CoreListAdapter.Companion.get
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.data.Note
import com.example.mynotes.data.UserDao
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.databinding.ItemNoteBinding
import com.example.mynotes.ui.addNote.AddActivity
import com.example.mynotes.ui.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    @Inject
    lateinit var userDao: UserDao

    private lateinit var swipeRefresh : SwipeRefreshLayout
    private var note = ArrayList<Note?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        getNote()

//        swipeRefresh.setOnRefreshListener {
//            getNote()
//        }



        binding.rvNote.adapter = CoreListAdapter<ItemNoteBinding, Note>(R.layout.item_note)
            .initItem(note) { position, data ->
                tos("tes")
                openActivity<AddActivity> {
                }
            }

        binding.btnAdd.setOnClickListener {
            openActivity<AddActivity> {
                finish()
            }
        }
//
//        binding.btnHome.setOnClickListener {
//            openActivity<HomeActivity> {
////                finish()
//                getNote()
//            }
//        }

//        binding.btnProfile.setOnClickListener {
//            openActivity<ProfileActivity> {
//            }
//        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                tos("tes3")
                viewModel.note.observe(this@HomeActivity) { dataNote ->
                    if (dataNote.isEmpty()) {
                        tos("dataKosong")
                    } else {
                        tos("dataAda")
                    }
                    Log.d("HomeActivity", "checkDataObserve :$dataNote ")
                    binding.rvNote.adapter?.notifyDataSetChanged()
                    note.addAll(dataNote)

                }
                viewModel.apiResponse.collect { it ->
                    tos("tes1")
                    when (it.status) {
                        ApiStatus.SUCCESS -> {
                            binding.rvNote.adapter?.get()?.removeNull()
                        }
                        else -> {

                        }

                    }
                }
            }
        }


    }

    private fun getNote() {
        viewModel.getNote()
        tos("test")

    }

}