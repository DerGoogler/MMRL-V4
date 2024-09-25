package com.sanmer.mrepo.ui.screens.repository.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sanmer.mrepo.R
import com.sanmer.mrepo.database.entity.Repo
import com.sanmer.mrepo.model.online.OnlineModule
import com.sanmer.mrepo.model.online.TrackJson
import com.sanmer.mrepo.ui.component.CollapsingTopAppBar
import com.sanmer.mrepo.ui.component.CollapsingTopAppBarDefaults
import com.sanmer.mrepo.ui.component.Logo
import com.sanmer.mrepo.ui.providable.LocalUserPreferences
import com.sanmer.mrepo.ui.screens.repository.view.items.LicenseItem
import com.sanmer.mrepo.ui.screens.repository.view.items.TagItem
import com.sanmer.mrepo.ui.screens.repository.view.items.TrackItem
import com.sanmer.mrepo.utils.extensions.openUrl
import io.github.z4kn4fein.semver.constraints.toConstraint
import io.github.z4kn4fein.semver.satisfies
import io.github.z4kn4fein.semver.toVersionOrNull

@Composable
fun ViewTopBar(
    online: OnlineModule,
    tracks: List<Pair<Repo, TrackJson>>,
    rootVersionName: String,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController
) = CollapsingTopAppBar(
    title = {
        Text(
            text = online.name,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    },
    content = topBarContent(
        module = online,
        rootVersionName = rootVersionName,
        tracks = tracks
    ),
    navigationIcon = {
        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = null
            )
        }
    },
    scrollBehavior = scrollBehavior,
    colors = CollapsingTopAppBarDefaults.topAppBarColors(
        scrolledContainerColor = MaterialTheme.colorScheme.surface
    )
)

@Composable
private fun topBarContent(
    module: OnlineModule,
    tracks: List<Pair<Repo, TrackJson>>,
    rootVersionName: String,
): @Composable ColumnScope.() -> Unit = {
    val userPreferences = LocalUserPreferences.current
    val repositoryMenu = userPreferences.repositoryMenu

    val context = LocalContext.current
    val hasLicense = module.hasLicense
    val hasDonate = module.donate.isNotBlank()

    val ver = ">=10764"
    val requiredVersion = rootVersionName.replace(Regex(":.*$"), "").toVersionOrNull()

    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        if (repositoryMenu.showIcon) {
            if (module.icon.isNotEmpty()) {
                AsyncImage(
                    model = module.icon,
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape),
                    contentDescription = null
                )
            } else {
                Logo(
                    icon = R.drawable.box,
                    modifier = Modifier.size(55.dp),
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = module.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = module.author,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = buildAnnotatedString {
                    append("Root $ver")
                    if (hasLicense) {
                        append(", ")
                        append("License = ${module.license}")
                    }
                },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
            if (requiredVersion != null) {
                if (!(requiredVersion satisfies ver.toConstraint())) {
                    Text(
                        text = stringResource(R.string.unsupported),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }
    }

    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TrackItem(
            tracks = tracks
        )

        if (hasLicense) {
            LicenseItem(
                licenseId = module.license
            )
        }

        if (hasDonate) {
            TagItem(
                icon = R.drawable.currency_dollar,
                onClick = { context.openUrl(module.donate) }
            )
        }
    }
}