package com.dotdevs.assignment.holahalo.api.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    @Json(name = "id")
    var id: Int,

    @Json(name = "pageURL")
    var pageURL: String,

    @Json(name = "tags")
    var tags: String,

    @Json(name = "webformatURL")
    var webformatURL: String,

    @Json(name = "imageWidth")
    var imageWidth: Int,

    @Json(name = "imageHeight")
    var imageHeight: Int,

    @Json(name = "views")
    var views: Int,

    @Json(name = "comments")
    var comments: Int,

    @Json(name = "likes")
    var likes: Int,

    @Json(name = "user")
    var user: String,

    @Json(name = "userImageURL")
    var userImageURL: String

): Parcelable