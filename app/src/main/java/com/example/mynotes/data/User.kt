package com.example.mynotes.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey
    @Expose
    @SerializedName("id_room")
    val idRoom: Int,
    @Expose
    @SerializedName("id")
    val id : String,
    @Expose
    @SerializedName("name")
    val name : String?,
    @Expose
    @SerializedName("email")
    val email : String?,
    @Expose
    @SerializedName("photo")
    val photo : String?
) : Parcelable {
//    companion object {
//        @JvmStatic
//        @BindingAdapter(value = ["image", "imageThumbnail"], requireAll = false)
//        fun loadImage(imageView: ImageView,image: String?){
//            image.let {
//                Glide
//                    .with(imageView.context)
//                    .load(image)
//                    .placeholder(R.drawable.ic_baseline_person_24)
////                    .error(R.drawable.placeholder)
////                    .thumbnail(thumbnail)
//                    .apply(RequestOptions.centerCropTransform())
//                    .into(imageView)
//            }
//        }
//    }
}

