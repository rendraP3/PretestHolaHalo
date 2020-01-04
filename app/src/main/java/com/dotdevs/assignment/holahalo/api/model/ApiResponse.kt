package com.dotdevs.assignment.holahalo.api.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApiResponse(
    @Json(name = "total")
    var total: Int,

    @Json(name = "totalHits")
    var totalHits: Int,

    @Json(name = "hits")
    var hits: List<Image>
): Parcelable