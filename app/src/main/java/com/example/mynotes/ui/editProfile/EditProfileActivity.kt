package com.example.mynotes.ui.editProfile

import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
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
import com.example.mynotes.databinding.ActivityEditProfileBinding
import com.example.mynotes.ui.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class EditProfileActivity :
    BaseActivity<ActivityEditProfileBinding, EditProfileViewModel>(R.layout.activity_edit_profile) {
//
//    @Inject
//    lateinit var session : CoreSession

    private lateinit var photoFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        photoFile = getPhotoFile()

//        tokenAPI()


        binding.ivBack.setOnClickListener {
            openActivity<ProfileActivity> {
            }
        }

        //Button Save
        binding.btnSave.setOnClickListener {
            if (binding.etName.isEmptyRequired(R.string.mustFill)) {
                return@setOnClickListener
            }
            val name = binding.etName.textOf()
            viewModel.UpdateProfile(name)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Update Profile")
                            ApiStatus.SUCCESS -> {
                                tos(it.message ?: "Berhasil Update Profile")
                                loadingDialog.dismiss()
                                openActivity<ProfileActivity>()
                                finish()
                            }
                            ApiStatus.ERROR -> {
                                disconnect(it)
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

//    fun tokenAPI() {
//        val dateNow = DateTimeHelper().dateNow()
//        val tokenInit = "$dateNow|rahasia"
//        val tokenEncrypt = tokenInit.base64encrypt()
//        session.setValue(Cons.TOKEN.API_TOKEN, tokenEncrypt)
//
//        Timber.d("Check Token : $tokenInit")
//
//        lifecycleScope.launch {
//            viewModel.getToken()
//        }
//
//    }


    //Gallery , photo not Done yet
    private var activityLauncherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.data?.let { uri ->
                val bitmapImage = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(this.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
                binding.ivProfile.setImageBitmap(bitmapImage)
            }

        }

    private fun openGallery() {
        val galeryInt = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityLauncherGallery.launch(galeryInt)
    }

    fun requestPermissionGallery() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            200
        )
    }

    fun requestPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

//    private fun getPhotoFile(): File {
//
//    }

}


