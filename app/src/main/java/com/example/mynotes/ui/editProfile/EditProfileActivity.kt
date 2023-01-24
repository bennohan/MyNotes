package com.example.mynotes.ui.editProfile

import android.content.Intent
import android.content.UriPermission
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.openActivity
import com.example.mynotes.R
import com.example.mynotes.base.BaseActivity
import com.example.mynotes.databinding.ActivityEditProfileBinding
import com.example.mynotes.ui.profile.ProfileActivity
import java.io.File
import javax.xml.transform.Source

class EditProfileActivity : NoViewModelActivity<ActivityEditProfileBinding>(R.layout.activity_edit_profile) {

    private lateinit var photoFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        photoFile = getPhotoFile()

        binding.ivBack.setOnClickListener {
            openActivity<ProfileActivity>{

            }
        }

    }
//Gallery , photo not Done yet
    private var activityLauncherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        it.data?.data?.let { uri ->
            val bitmapImage = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
            }else{
                val source = ImageDecoder.createSource(this.contentResolver,uri)
                ImageDecoder.decodeBitmap(source)
            }
            binding.ivProfile.setImageBitmap(bitmapImage)
        }

    }

    private fun openGallery(){
        val galeryInt = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityLauncherGallery.launch(galeryInt)
    }

    fun requestPermissionGallery(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),200)
    }

    fun requestPermissionResult(requestCode: Int,permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

//    private fun getPhotoFile(): File {
//
//    }

}


