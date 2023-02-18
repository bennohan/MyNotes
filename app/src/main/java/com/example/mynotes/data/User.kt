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
}

