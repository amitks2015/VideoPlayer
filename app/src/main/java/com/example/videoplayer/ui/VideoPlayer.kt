package com.example.videoplayer.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import com.example.videoplayer.model.PlayerStatus
import com.example.videoplayer.model.VideoEvent
import com.example.videoplayer.model.VideoState
import com.example.videoplayer.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

@Composable
fun VideoPlayer(
    lifecycleOwner: LifecycleOwner,
    videoState: VideoState,
    handleEvent: (event: VideoEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        val mediaItem =
            MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    if(playbackState == Player.STATE_READY) {
                        handleEvent(VideoEvent.VideoLoaded)
                    }
                }
            })
        }
    }

    Box(
        modifier = Modifier.background(Color.Black)
    ) {
        var controlVisible by remember {
            mutableStateOf(true)
        }

        val controlsClickLabel = if (controlVisible) {
            stringResource(id = R.string.cd_hide_controls)
        } else {
            stringResource(id = R.string.cd_display_controls)
        }

        val alphaAnimation by animateFloatAsState(
            targetValue = if(controlVisible) 0.7f else 0f,
            animationSpec = if(controlVisible) tween(0) else tween(750)
        )

        Playback(
            exoPlayer = exoPlayer,
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClickLabel = controlsClickLabel
                ) {
                    controlVisible = !controlVisible
                },
            lifecycleOwner = lifecycleOwner,
            state = videoState.playerStatus
        )

        Controls(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .alpha(alphaAnimation),
            playerStatus = videoState.playerStatus,
            togglePlayingState = {
                handleEvent(VideoEvent.ToggleStatus)
                if(videoState.playerStatus != PlayerStatus.PLAYING) {
                    controlVisible = false
                }
            }
        )
    }
}