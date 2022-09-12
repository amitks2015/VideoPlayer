package com.example.videoplayer.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videoplayer.VideoViewModel

@Composable
fun Video(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val viewModel: VideoViewModel = viewModel()

    VideoPlayer(
        lifecycleOwner = lifecycleOwner,
        videoState = viewModel.uiState.collectAsState().value,
        handleEvent = viewModel::handleEvent,
        modifier = Modifier.fillMaxSize()
    )
}