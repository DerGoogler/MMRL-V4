package dev.sanmer.mrepo.compat.impl

import com.topjohnwu.superuser.CallbackList
import com.topjohnwu.superuser.Shell
import com.topjohnwu.superuser.ShellUtils
import dev.sanmer.mrepo.compat.content.LocalModule
import dev.sanmer.mrepo.compat.content.State
import dev.sanmer.mrepo.compat.stub.IInstallCallback
import dev.sanmer.mrepo.compat.stub.IModuleManager
import java.io.File
import java.util.zip.ZipFile

internal abstract class BaseModuleManagerImpl(
    private val shell: Shell
) : IModuleManager.Stub() {
    internal val modulesDir = File(MODULES_PATH)

    private val mVersion by lazy {
        runCatching {
            "su -v".exec()
        }.getOrDefault("unknown")
    }

    private val mVersionCode by lazy {
        runCatching {
            "su -V".exec().toInt()
        }.getOrDefault(-1)
    }

    override fun getVersion(): String {
        return mVersion
    }

    override fun getVersionCode(): Int {
        return mVersionCode
    }

    override fun getModules() = modulesDir.listFiles()
        .orEmpty()
        .mapNotNull { dir ->
            readProps(dir)?.toModule(dir)
        }

    override fun getModuleById(id: String): LocalModule? {
        val dir = modulesDir.resolve(id)
        return readProps(dir)?.toModule(dir)
    }

    override fun getModuleInfo(zipPath: String): LocalModule? {
        val zipFile = ZipFile(zipPath)
        val entry = zipFile.getEntry(PROP_FILE) ?: return null

        return zipFile.getInputStream(entry).use {
            it.bufferedReader()
                .readText()
                .let(::readProps)
                .toModule()
        }
    }

    private fun readProps(props: String) = props.lines()
        .associate { line ->
            val items = line.split("=", limit = 2).map { it.trim() }
            if (items.size != 2) {
                "" to ""
            } else {
                items[0] to items[1]
            }
        }

    private fun readProps(moduleDir: File) =
        moduleDir.resolve(PROP_FILE).let {
            when {
                it.exists() -> readProps(it.readText())
                else -> null
            }
        }

    private fun readState(moduleDir: File): State {
        moduleDir.resolve("remove").apply {
            if (exists()) return State.REMOVE
        }

        moduleDir.resolve("disable").apply {
            if (exists()) return State.DISABLE
        }

        moduleDir.resolve("update").apply {
            if (exists()) return State.UPDATE
        }

        return State.ENABLE
    }

    private fun readLastUpdated(moduleDir: File): Long {
        MODULE_FILES.forEach { filename ->
            val file = moduleDir.resolve(filename)
            if (file.exists()) {
                return file.lastModified()
            }
        }

        return 0L
    }

    private fun Map<String, String>.toModule(
        dir: File
    ) = toModule(
        path = dir.name,
        state = readState(dir),
        lastUpdated = readLastUpdated(dir)
    )

    private fun Map<String, String>.toModule(
        path: String = "unknown",
        state: State = State.ENABLE,
        lastUpdated: Long = 0L
    ) = LocalModule(
        id = getOrDefault("id", path),
        name = getOrDefault("name", path),
        version = getOrDefault("version", ""),
        versionCode = getOrDefault("versionCode", "-1").toIntOr(-1),
        author = getOrDefault("author", ""),
        description = getOrDefault("description", ""),
        updateJson = getOrDefault("updateJson", ""),
        state = state,
        lastUpdated = lastUpdated
    )

    private fun String.toIntOr(defaultValue: Int) =
        runCatching {
            toInt()
        }.getOrDefault(defaultValue)

    private fun String.exec() = ShellUtils.fastCmd(shell, this)

    internal fun install(cmd: String, path: String, callback: IInstallCallback) {
        val stdout = object : CallbackList<String?>() {
            override fun onAddElement(msg: String?) {
                msg?.let(callback::onStdout)
            }
        }

        val stderr = object : CallbackList<String?>() {
            override fun onAddElement(msg: String?) {
                msg?.let(callback::onStderr)
            }
        }

        val result = shell.newJob().add(cmd).to(stdout, stderr).exec()
        if (result.isSuccess) {
            val module = getModuleInfo(path)
            callback.onSuccess(module)
        } else {
            callback.onFailure()
        }
    }

    internal fun String.submit(cb: Shell.ResultCallback) = shell
        .newJob().add(this).to(ArrayList(), null)
        .submit(cb)

    companion object {
        const val PROP_FILE = "module.prop"
        const val MODULES_PATH = "/data/adb/modules"

        val MODULE_FILES = listOf(
            "post-fs-data.sh", "service.sh", "uninstall.sh",
            "system", "system.prop", "module.prop"
        )
    }
}