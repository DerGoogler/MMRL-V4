package com.dergoogler.mmrl.model.online

import com.dergoogler.mmrl.utils.Utils
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OnlineModule(
    val id: String,
    val name: String,
    val version: String,
    val versionCode: Int,
    val author: String,
    val description: String,
    val track: TrackJson,
    val versions: List<VersionItem>,

    val maxApi: Int? = 0,
    val minApi: Int? = 0,

    val size: Int = 0,
    val categories: List<String> = emptyList(),
    val icon: String = "",
    val homepage: String = "",
    val donate: String = "",
    val support: String = "",
    val cover: String = "",
    val screenshots: List<String> = emptyList(),
    val license: String = "",
    val readme: String = "",
    val require: List<String> = emptyList(),
    val verified: Boolean = false,

    val root: ModuleRoot = ModuleRoot(magisk = "", kernelsu = "", apatch = ""),
    val note: ModuleNote = ModuleNote(title = "", message = "", color = "")
    //features: ModuleFeatures
) {
    val versionDisplay get() = Utils.getVersionDisplay(version, versionCode)
    val hasLicense
        get() = license.isNotBlank()
                && license.uppercase() != "UNKNOWN"


    override fun equals(other: Any?): Boolean {
        return when (other) {
            is OnlineModule -> id == other.id
            else -> false
        }
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        fun example() = OnlineModule(
            id = "online_example",
            name = "Example",
            version = "2022.08.16",
            versionCode = 1703,
            author = "Sanmer",
            description = "This is an example!",
            license = "GPL-3.0",
            track = TrackJson(
                typeName = "ONLINE_JSON",
                added = 0f,
                antifeatures = emptyList()
            ),
            versions = emptyList()
        )
    }
}