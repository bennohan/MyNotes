package com.example.mynotes.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseFragment
import com.example.mynotes.data.Note
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import com.example.mynotes.databinding.FragmentProfileBinding
import com.example.mynotes.ui.editProfile.EditProfileActivity
import com.example.mynotes.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile){

    @Inject
    lateinit var userDao: UserDao

     private  var user: User? = null

    private lateinit var  selectedNote: Note


    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val tvemailPF = view.findViewById<TextView>(R.id.tv_emailPF)
        val ivprofilePF = view.findViewById<ImageView>(R.id.iv_profilePF)

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


//        viewModel.getUser.observe(viewLifecycleOwner) {
////                Timber.d("adaData")
//            binding?.userfr = it
//        }

        lifecycleScope.launch {
            viewModel.getUser.observe(requireActivity()) { data ->
                data.let {
                    user = it
                    tvnamePF.text = it?.name
                    tvemailPF.text = it?.email
//                    ivprofilePF.drawable = it.photo

                    Glide
                        .with(requireContext())
                        .load(it?.photo)
                        .into(ivprofilePF)

                }

            }
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                }
            }

        }

    }




}