package com.sanmer.mrepo.ui.screens.repository.view.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.mrepo.R
import com.sanmer.mrepo.database.entity.Repo
import com.sanmer.mrepo.model.online.TrackJson
import com.sanmer.mrepo.ui.component.NavigationBarsSpacer
import com.sanmer.mrepo.ui.utils.expandedShape
import com.sanmer.mrepo.utils.extensions.toDateTime

@Composable
fun TrackItem(
    tracks: List<Pair<Repo, TrackJson>>
) = Box {
    var open by rememberSaveable { mutableStateOf(false) }

    TagItem(
        icon = R.drawable.tag,
        onClick = { open = true }
    )

    if (open) {
        ModalBottomSheet(
            onDismissRequest = { open = false },
            shape = BottomSheetDefaults.expandedShape(15.dp),
            windowInsets = WindowInsets(0)
        ) {
            Text(
                text = stringResource(id = R.string.view_module_view_track),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            LazyColumn(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = tracks,
                    key = { it.first.url }
                ) { (repo, track) ->
                    ValueItem(
                        repo = repo,
                        track = track
                    )
                }

                item {
                    NavigationBarsSpacer()
                }
            }
        }
    }
}

@Composable
private fun ValueItem(
    repo: Repo,
    track: TrackJson
) = Surface(
    modifier = Modifier.fillMaxWidth(),
    tonalElevation = 6.dp,
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    shape = RoundedCornerShape(15.dp)
) {
    Row(
        modifier = Modifier.padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.code_asterix),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.view_module_type,
                    track.type.name.replace("_", " ")),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )

            stringResource(id = R.string.view_module_added,
                track.added!!.toDateTime()
            )

            Text(
                text = stringResource(id = R.string.view_module_added,
                    track.added.toDateTime()),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}