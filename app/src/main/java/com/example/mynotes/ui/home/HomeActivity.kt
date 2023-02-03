package com.example.mynotes.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.data.Note
import com.example.mynotes.data.UserDao
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.ui.addNote.AddActivity
import com.example.mynotes.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    @Inject
    lateinit var userDao: UserDao

    private var note = ArrayList<Note?>()

    private  var profileFragment = ProfileFragment()
    private  var homeFragment = HomeFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding.bottomNavigationView.setBackgroundColor(null)

        replaceFragment(homeFragment)
//        getNote()

        //buttonNavigation
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_profile -> {
                    replaceFragment(profileFragment)
                }
                R.id.nav_home -> {
                    replaceFragment(homeFragment)
                }

            }
            true
        }





        //AddButton
        binding.btnAdd.setOnClickListener {
            openActivity<AddActivity> {
                finish()
            }
        }

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                tos("tes3")
//                viewModel.note.observe(this@HomeActivity) { dataNote ->
//                    if (dataNote.isEmpty()) {
//                        tos("dataKosong")
//                    } else {
//                        tos("dataAda")
//                    }
//                    Log.d("HomeActivity", "checkDataObserve :$dataNote ")
////                    binding.rvNote.adapter?.notifyDataSetChanged()
////                    note.addAll(dataNote)
//
//                }
//                viewModel.apiResponse.collect { it ->
//                    tos("tes1")
//                    when (it.status) {
//                        ApiStatus.SUCCESS -> {
////                            binding.rvNote.adapter?.get()?.removeNull()
//                        }
//                        else -> {
//
//                        }
//
//                    }
//                }
//            }
//        }


    }

    private fun replaceFragment(fragment: Fragment) {
       supportFragmentManager.beginTransaction().apply {
           replace(R.id.constraint,fragment)
           commit()
       }
    }

//    private fun getNote() {
//        viewModel.getNote()
//        tos("test")
//
//    }

}