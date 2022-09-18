package com.example.videoplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.videoplayer.R
import com.example.videoplayer.model.PlayerStatus
import com.example.videoplayer.ui.theme.VideoPlayerTheme

@Composable
fun Controls(
    modifier: Modifier = Modifier,
    playerStatus: PlayerStatus,
    togglePlayingState: () -> Unit
) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = {
                togglePlayingState()
            },
            enabled = playerStatus != PlayerStatus.LOADING
        ) {
            val (icon, description) = if(playerStatus == PlayerStatus.PLAYING) {
                Pair(Icons.Default.Pause, stringResource(id = R.string.cd_pause))
            } else {
                Pair(Icons.Default.PlayArrow, stringResource(id = R.string.cd_play))
            }
            Icon(
                imageVector = icon,
                contentDescription = description
            )
        }
    }
}

@Preview
@Composable
fun PreviewControls() {
    VideoPlayerTheme {
        Controls(
            modifier = Modifier.fillMaxWidth(),
            playerStatus = PlayerStatus.LOADING
        ) {}
    }
}