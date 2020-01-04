package com.dotdevs.assignment.holahalo.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Image(
    @PrimaryKey @ColumnInfo val id:Int,
    @ColumnInfo val pageURL: String,
    @ColumnInfo val tags: String,
    @ColumnInfo val webFormatURL: String,
    @ColumnInfo val imageWidth: Int,
    @ColumnInfo val imageHeight: Int,
    @ColumnInfo val views: Int,
    @ColumnInfo val likes: Int,
    @ColumnInfo val comments: Int,
    @ColumnInfo val user: String,
    @ColumnInfo val userImageURL: String
): Parcelable