package com.dergoogler.mmrl.ui.screens.repository.view.pages

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dergoogler.mmrl.R
import com.dergoogler.mmrl.model.online.ModuleFeatures
import com.dergoogler.mmrl.model.online.OnlineModule
import com.dergoogler.mmrl.utils.extensions.isObjectEmpty
import com.dergoogler.mmrl.utils.extensions.openUrl

@Composable
fun AboutPage(
    online: OnlineModule
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
) {
    HelpItem(online = online)

    HorizontalDivider(thickness = 0.9.dp)

    online.features?.let {
        if (!it.isObjectEmpty()) {
            FeaturesItem(features = it)

            HorizontalDivider(thickness = 0.9.dp)
        }
    }
}


@Composable
private fun HelpItem(
    online: OnlineModule
) = Column(
    modifier = Modifier
        .padding(all = 16.dp)
        .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    Text(
        text = stringResource(id = R.string.view_module_help),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary
    )

    online.homepage?.let {
        ValueItem(
            key = stringResource(id = R.string.view_module_homepage),
            value = it,
            icon = R.drawable.world_www
        )
    }

    ValueItem(
        key = stringResource(id = R.string.view_module_source),
        value = online.track.source,
        icon = R.drawable.brand_git
    )

    online.support?.let {
        ValueItem(
            key = stringResource(id = R.string.view_module_support),
            value = it,
            icon = R.drawable.heart_handshake
        )
    }
}


@Composable
private fun FeaturesItem(
    features: ModuleFeatures
) = Column(
    modifier = Modifier
        .padding(all = 16.dp)
        .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    Text(
        text = stringResource(id = R.string.view_module_features),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary
    )

    FeatureValueItem(
        feature = features.service,
        key = "Service",
        value = "Runs code later during boot (more system initialized)."
    )
    FeatureValueItem(
        feature = features.postFsData,
        key = "Post FS Data",
        value = "Runs code early after boot (partially booted system)."
    )
    FeatureValueItem(
        feature = features.resetprop,
        key = "System Properties",
        value = "Sets system properties loaded by Magisk's resetprop."
    )
    FeatureValueItem(
        feature = features.sepolicy,
        key = "SELinux Policy",
        value = "Adds custom security policies (SEPolicy rules)."
    )
    FeatureValueItem(
        feature = features.zygisk,
        key = "Zygisk",
        value = "Allows modules to interact with the Zygisk framework, enabling system-level modifications and interactions."
    )
    FeatureValueItem(
        feature = features.apks,
        key = "APKs",
        value = "Including additional APKs inside the module."
    )
    FeatureValueItem(
        feature = features.webroot,
        key = "WebUI",
        value = "Web interface for managing modules (KernelSU & APatch, not Magisk)."
    )
    FeatureValueItem(
        feature = features.postMount,
        key = "Post Mount",
        value = " Script execution after overlayfs mounts (KernelSU & APatch)."
    )
    FeatureValueItem(
        feature = features.bootCompleted,
        key = "Boot completed",
        value = " Script execution after boot completes (KernelSU & APatch)."
    )
    // FeatureValueItem(feature = features.modconf, key ="ModConf", value ="Module configuration files for MMRL (not Magisk, KernelSU).")

}


@Composable
private fun ValueItem(
    key: String,
    value: String,
    @DrawableRes icon: Int = R.drawable.world_www
) {
    if (value.isBlank()) return
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = key,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }

        ElevatedAssistChip(
            onClick = { context.openUrl(value) },
            label = { Text(text = stringResource(id = R.string.open)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            }
        )
    }
}

@Composable
private fun FeatureValueItem(
    feature: Boolean?,
    key: String,
    value: String?,
    modifier: Modifier = Modifier
) {
    if (feature == null || value.isNullOrBlank()) return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = key,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}