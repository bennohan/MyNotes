package com.example.mynotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Note(
    @PrimaryKey
    @Expose
    @SerializedName("id_room")
    val idRoom: Int,
    @Expose
    @SerializedName("id")
    val id : Int,
    @Expose
    @SerializedName("name")
    val name : String?,
    @Expose
    @SerializedName("email")
    val email : String?,
    @Expose
    @SerializedName("photo")
    val photo : String?
)

