package com.sanmer.mrepo.model.online

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrackJson(
    @Json(name = "type") val typeName: String,
    val added: Float? = 0f,
    val source: String = "",
    val antifeatures: List<String> = emptyList(),
) {
    val type = TrackType.valueOf(typeName)
//    val hasLicense get() = license.isNotBlank()
//            && license.uppercase() != "UNKNOWN"
}

enum class TrackType {
    UNKNOWN,
    ONLINE_JSON,
    ONLINE_ZIP,
    GIT,
    LOCAL_JSON,
    LOCAL_ZIP,
}