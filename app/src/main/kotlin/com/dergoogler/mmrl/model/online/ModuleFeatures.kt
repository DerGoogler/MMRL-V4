package com.dergoogler.mmrl.model.online


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.github.z4kn4fein.semver.constraints.toConstraint
import io.github.z4kn4fein.semver.satisfies
import io.github.z4kn4fein.semver.toVersionOrNull

@JsonClass(generateAdapter = true)
data class ModuleFeatures(
    val service: Boolean? = null,
    @Json(name = "post_fs_data") val postFsData: Boolean? = null,
    val resetprop: Boolean? = null,
    val sepolicy: Boolean? = null,
    val zygisk: Boolean? = null,
    val apks: Boolean? = null,
    val webroot: Boolean? = null,
    @Json(name = "post_mount") val postMount: Boolean? = null,
    @Json(name = "boot_completed") val bootCompleted: Boolean? = null,
//    val modconf: Boolean? = false,
) {
    fun isNotEmpty() =
        service != null || postFsData != null || resetprop != null || sepolicy != null || zygisk != null || apks != null || webroot != null || postMount != null || bootCompleted != null //|| modconf != null
}

