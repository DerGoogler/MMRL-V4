package com.dergoogler.mmrl.compat.impl

import com.dergoogler.mmrl.compat.stub.IPowerManager

internal class PowerManagerImpl(
    private val original: android.os.IPowerManager
) : IPowerManager.Stub() {
    override fun reboot(confirm: Boolean, reason: String?, wait: Boolean) {
        original.reboot(confirm, reason, wait)
    }
}