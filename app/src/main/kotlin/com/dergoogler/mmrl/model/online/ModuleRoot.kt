package com.dergoogler.mmrl.model.online


import com.squareup.moshi.JsonClass
import io.github.z4kn4fein.semver.constraints.toConstraint
import io.github.z4kn4fein.semver.satisfies
import io.github.z4kn4fein.semver.toVersionOrNull

@JsonClass(generateAdapter = true)
data class ModuleRoot(
    val magisk: String = "",
    val kernelsu: String = "",
    val apatch: String = "",
) {
    fun isNotEmpty() = magisk.isNotEmpty() || kernelsu.isNotEmpty() || apatch.isNotEmpty()

    fun isSupported(version: String): Boolean {
        val parsedRootProvider = version.replace(Regex("^.*:"), "")
        val parsedVersion = version.replace(Regex(":.*$"), "").toVersionOrNull()

        if (parsedVersion != null) {
            return when (parsedRootProvider) {
                "magisk" -> {
                    !(parsedVersion satisfies magisk.toConstraint())
                }

                "kernelsu" -> {
                    !(parsedVersion satisfies kernelsu.toConstraint())
                }

                "apatch" -> {
                    !(parsedVersion satisfies apatch.toConstraint())
                }

                else -> true
            }
        } else {
            return true
        }
    }
}

