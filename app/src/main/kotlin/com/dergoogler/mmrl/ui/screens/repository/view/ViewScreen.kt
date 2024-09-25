package com.dergoogler.mmrl.ui.screens.repository.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dergoogler.mmrl.model.online.VersionItem
import com.dergoogler.mmrl.ui.activity.InstallActivity
import com.dergoogler.mmrl.ui.component.CollapsingTopAppBarDefaults
import com.dergoogler.mmrl.ui.screens.repository.view.pages.AboutPage
import com.dergoogler.mmrl.ui.screens.repository.view.pages.OverviewPage
import com.dergoogler.mmrl.ui.screens.repository.view.pages.VersionsPage
import com.dergoogler.mmrl.ui.utils.none
import com.dergoogler.mmrl.viewmodel.ModuleViewModel

@Composable
fun ViewScreen(
    navController: NavController,
    viewModel: ModuleViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val scrollBehavior = CollapsingTopAppBarDefaults.scrollBehavior()
    val pagerState = rememberPagerState { if (viewModel.isEmptyAbout) 2 else 3 }

    val download: (VersionItem, Boolean) -> Unit = { item, install ->
        viewModel.downloader(context, item) {
            if (install) {
                InstallActivity.start(
                    context = context,
                    uri = it.toUri()
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ViewTopBar(
                online = viewModel.online,
                tracks = viewModel.tracks,
                rootVersionName = viewModel.version,
                scrollBehavior = scrollBehavior,
                navController = navController
            )
        },
        contentWindowInsets = WindowInsets.none
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            ViewTab(
                state = pagerState,
                updatableSize = viewModel.updatableSize,
                hasAbout = !viewModel.isEmptyAbout
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIndex ->
                when (pageIndex) {
                    0 -> OverviewPage(
                        online = viewModel.online,
                        item = viewModel.lastVersionItem,
                        local = viewModel.local,
                        notifyUpdates = viewModel.notifyUpdates,
                        isProviderAlive = viewModel.isProviderAlive,
                        setUpdatesTag = viewModel::setUpdatesTag,
                        onInstall = { download(it, true) },
                    )
                    1 -> VersionsPage(
                        versions = viewModel.versions,
                        localVersionCode = viewModel.localVersionCode,
                        isProviderAlive = viewModel.isProviderAlive,
                        getProgress = { viewModel.getProgress(it) },
                        onDownload = download
                    )
                    2 -> AboutPage(
                        online = viewModel.online
                    )
                }
            }
        }
    }
}