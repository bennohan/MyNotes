package com.example.mynotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Note(
    @PrimaryKey
    @Expose
    @SerializedName("title")
    val titile : String?,
    @Expose
    @SerializedName("content")
    val note: String?,
    @Expose
    @SerializedName("Date")
    val date: String?
)
