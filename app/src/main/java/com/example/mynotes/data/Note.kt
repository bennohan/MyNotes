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
    @PrimaryKey(autoGenerate = false)
    @Expose
    @SerializedName("idRoom")
    val idRoom: Int,
    @Expose
    @SerializedName("id")
    val id: String?,
    @Expose
    @SerializedName("title")
    val titile : String?,
    @Expose
    @SerializedName("content")
    val note: String?,
    @Expose
    @SerializedName("created_at")
    val createdAt: Long?,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: Long?,
) : Parcelable
