package com.dergoogler.mmrl.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.dergoogler.mmrl.model.online.ModuleNote
import com.dergoogler.mmrl.model.online.ModuleRoot
import com.dergoogler.mmrl.model.online.OnlineModule
import com.dergoogler.mmrl.model.online.TrackJson

@Entity(tableName = "onlineModules", primaryKeys = ["id", "repoUrl"])
data class OnlineModuleEntity(
    val id: String,
    val repoUrl: String,
    val name: String,
    val version: String,
    val versionCode: Int,
    val author: String,
    val description: String,

    val maxApi: Int? = null,
    val minApi: Int? = null,

    val size: Int? = null,
    val categories: List<String>? = null,
    val icon: String? = null,
    val homepage: String? = null,
    val donate: String? = null,
    val support: String? = null,
    val cover: String? = null,
    val screenshots: List<String>? = null,
    val license: String? = "",
    val readme: String? = null,
    val require: List<String>? = null,
    val verified: Boolean? = null,

    @Embedded val root: ModuleRootEntity? =null,
    @Embedded val note: ModuleNoteEntity? = null,
    @Embedded val track: TrackJsonEntity
) {
    constructor(
        original: OnlineModule,
        repoUrl: String
    ) : this(
        id = original.id,
        repoUrl = repoUrl,
        name = original.name,
        version = original.version,
        versionCode = original.versionCode,
        author = original.author,
        description = original.description,
        track = TrackJsonEntity(original.track),
        note = ModuleNoteEntity(original.note),
        maxApi = original.maxApi,
        minApi = original.minApi,
        size = original.size,
        categories = original.categories,
        icon = original.icon,
        homepage = original.homepage,
        donate = original.donate,
        support = original.support,
        cover = original.cover,
        screenshots = original.screenshots,
        license = original.license,
        readme = original.readme,
        require = original.require,
        verified = original.verified
    )

    fun toModule() = OnlineModule(
        id = id,
        name = name,
        version = version,
        versionCode = versionCode,
        author = author,
        description = description,
        track = track.toTrack(),
        note = note?.toNote(),
        versions = listOf(),
        maxApi = maxApi,
        minApi = minApi,
        size = size,
        categories = categories,
        icon = icon,
        homepage = homepage,
        donate = donate,
        support = support,
        cover = cover,
        screenshots = screenshots,
        license = license,
        readme = readme,
        require = require,
        verified = verified
    )
}

@Entity(tableName = "track")
@TypeConverters
data class TrackJsonEntity(
    val type: String,
    val added: Float? = 0f,
    val source: String,
    val antifeatures: List<String>? = null,
) {
    constructor(original: TrackJson) : this(
        type = original.type.name,
        added = original.added,
        source = original.source,
        antifeatures = original.antifeatures,
    )

    fun toTrack() = TrackJson(
        typeName = type,
        added = added,
        source = source,
        antifeatures = antifeatures
    )
}

@Entity(tableName = "note")
@TypeConverters
data class ModuleNoteEntity(
    val color: String? = null,
    val title: String? = null,
    val message: String? = null,
) {
    constructor(original: ModuleNote?) : this(
        color = original?.color,
        title = original?.title,
        message = original?.message,
    )

    fun toNote() = ModuleNote(
        color = color,
        title = title,
        message = message,
    )
}

@Entity(tableName = "root")
@TypeConverters
data class ModuleRootEntity(
    val magisk: String? = null,
    val kernelsu: String? = null,
    val apatch: String? = null,
) {
    constructor(original: ModuleRoot) : this(
        magisk = original.magisk,
        kernelsu = original.kernelsu,
        apatch = original.apatch,
    )

    fun toNote() = ModuleRoot(
        magisk = magisk,
        kernelsu = kernelsu,
        apatch = apatch,
    )
}