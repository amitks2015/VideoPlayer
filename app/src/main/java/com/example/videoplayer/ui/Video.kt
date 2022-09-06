package com.example.videoplayer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videoplayer.VideoViewModel

@Composable
fun Video() {
    val viewModel: VideoViewModel = viewModel()

    VideoPlayer(
        videoState = viewModel.uiState.collectAsState().value,
        handleEvent = viewModel::handleEvent
    )
}