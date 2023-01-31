package com.example.mynotes.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey
//    @Expose
//    @SerializedName("id")
//    val id: Int,
    @Expose
    @SerializedName("title")
    val titile : String?,
    @Expose
    @SerializedName("content")
    val note: String?,
    @Expose
    @SerializedName("Date")
    val date: String?
) : Parcelable
