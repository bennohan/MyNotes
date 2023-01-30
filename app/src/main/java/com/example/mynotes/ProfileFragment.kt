package com.example.mynotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.crocodic.core.extension.openActivity
import com.example.mynotes.base.BaseFragment
import com.example.mynotes.data.UserDao
import com.example.mynotes.databinding.FragmentProfileBinding
import com.example.mynotes.ui.editProfile.EditProfileActivity
import com.example.mynotes.ui.login.LoginActivity
import com.example.mynotes.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    @Inject
    lateinit var userDao: UserDao

    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        lifecycleScope.launch {
//            viewModel.getUser.observe(requireActivity()) { data ->
//                data.let {
//                    binding?.user = it
//
//                    binding?.tvName?.text = it.name
//                    binding?.tvEmail?.text = it.email
//                }
//
//            }
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                launch {
//                }
//            }
//
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Button , TextView
        val btnEditProfile = view.findViewById<Button>(R.id.btnEditProfile)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val tvnamePF = view.findViewById<TextView>(R.id.tv_namePF)

        //EditProfile Button
        btnEditProfile.setOnClickListener {
            activity?.openActivity<EditProfileActivity> {

            }
        }

        //Logout Button
        btnLogout.setOnClickListener {
            viewModel.logout {
                activity?.openActivity<LoginActivity>()
            }
        }

            viewModel.getUser.observe(viewLifecycleOwner) {
//                Timber.d("adaData")
                binding?.userfr = it
            }



    }

//    private fun initClick() {
//        binding?.btnTest?.setOnClickListener{
//            requireContext().tos("Tesst Click")
//            val intentedit = Intent(requireContext(), MainActivity::class.java)
//            startActivity(intentedit)
//
//        }
//
//
//        binding?.btnEditProfile?.setOnClickListener {
//            activity?.openActivity<AddActivity> {}
//        }
//    }


}