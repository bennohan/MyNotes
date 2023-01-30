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
//    private  var profileFragment = ProfileFragment()
//    private  var homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        //buttonNavigation
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.nav_profile -> {
//                    replaceFragment(profileFragment)
//                }
//                R.id.nav_home -> {
//                    replaceFragment(homeFragment)
//                }
//
//            }
//            true
//        }

//        binding.btnEditProfile.setOnClickListener {
//            openActivity<EditProfileActivity> {
//                finish()
//            }
//        }
//        binding.btnLogout.setOnClickListener {
//            viewModel.logout {
//                openActivity<LoginActivity>()
//                finish()
//            }
//
//        }
//
//        lifecycleScope.launch {
//            viewModel.getUser.observe(this@ProfileActivity) {
//                binding.user = it
//            }
//        }
    }
//    private fun replaceFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.constraint,fragment)
//            commit()
//        }
//    }
}