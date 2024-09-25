package com.dergoogler.mmrl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dergoogler.mmrl.Compat
import com.dergoogler.mmrl.datastore.DarkMode
import com.dergoogler.mmrl.datastore.WorkingMode
import com.dergoogler.mmrl.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val isProviderAlive get() = Compat.isAlive

    val version get() = Compat.get("") {
        with(moduleManager) { "$version (${versionCode})" }
    }

    init {
        Timber.d("SettingsViewModel init")
    }

    fun setWorkingMode(value: WorkingMode) {
        viewModelScope.launch {
            userPreferencesRepository.setWorkingMode(value)
        }
    }

    fun setDarkTheme(value: DarkMode) {
        viewModelScope.launch {
            userPreferencesRepository.setDarkTheme(value)
        }
    }

    fun setThemeColor(value: Int) {
        viewModelScope.launch {
            userPreferencesRepository.setThemeColor(value)
        }
    }

    fun setDeleteZipFile(value: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setDeleteZipFile(value)
        }
    }

    fun setUseDoh(value: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setUseDoh(value)
        }
    }

    fun setDownloadPath(value: File) {
        viewModelScope.launch {
            userPreferencesRepository.setDownloadPath(value)
        }
    }
}