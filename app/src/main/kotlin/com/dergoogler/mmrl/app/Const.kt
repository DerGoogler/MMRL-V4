package com.dergoogler.mmrl.app

import android.os.Environment
import java.io.File

object Const {
    val PUBLIC_DOWNLOADS: File = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_DOWNLOADS
    )

    const val SANMER_GITHUB_URL = "https://github.com/SanmerDev"
    const val GOOGLER_GITHUB_URL = "https://github.com/DerGoogler"
    const val SUPPORTED_REPOS_URL =
        "https://github.com/DerGoogler/MMRL/wiki/Supported-Repositories-in-MMRL"
    const val TRANSLATE_URL = "https://example.com/translate"
    const val GITHUB_URL = "https://github.com/DerGoogler/MMRL"
    const val TELEGRAM_URL = "https://t.me/GooglersRepo"
    const val DEMO_REPO_URL = "https://gr.dergoogler.com/gmr/"
    const val SPDX_URL = "https://spdx.org/licenses/%s.json"
}