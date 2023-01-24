package com.example.mynotes.ui.home

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.api.DataObserver
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.base.adapter.CoreListAdapter.Companion.get
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.crocodic.core.extension.openActivity
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

    private var note = ArrayList<Note?>()

    private val adapter by lazy {
        ReactiveListAdapter<ItemNoteBinding, Note>(R.layout.item_note)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.rvNote.adapter = CoreListAdapter<ItemNoteBinding,Note>(R.layout.item_note)
            .initItem(note){ position, data ->

            openActivity<AddActivity>{
            }
        }

        binding.btnAdd.setOnClickListener {
            openActivity<AddActivity> {
                finish()
            }
        }

        binding.btnHome.setOnClickListener {
            openActivity<HomeActivity> {
                finish()
            }
        }

        binding.btnProfile.setOnClickListener {
            openActivity<ProfileActivity> {
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.apiResponse.collect{ it ->
                    when (it.status) {
                        ApiStatus.SUCCESS -> {
                            viewModel.note.collect{ dataNote ->
                                note.addAll(dataNote)
                                Timber.d("check note:$dataNote")
                            }
//                            val data = it.dataAs<DataObserver<Note>>()
//                           Timber.d("check note:$data")
//                            val datas = data?.datas
//                            Timber.d("check note datas:$datas")
                            //note.addAll(datas)


                            binding.rvNote.adapter?.get()?.removeNull()

//                            if (data?.page == 1) {
//                                note.clear()
//                                binding.rvNote.adapter?.notifyDataSetChanged()
//
//                            }

                        }else -> {

                    }

                }
                }
            }
        }
        getNote()

    }

    private fun getNote(page: Int = 1) {
        viewModel.getNote(page)
    }

}