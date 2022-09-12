package com.example.videoplayer.ui

import android.view.ViewGroup
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.videoplayer.model.PlayerStatus
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun Playback(
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    state: PlayerStatus = PlayerStatus.IDLE
) {
    LaunchedEffect(
        key1 = exoPlayer,
        block = {
            exoPlayer.prepare()
        }
    )

    DisposableEffect(
        key1 = AndroidView(
            modifier = modifier,
            factory = { context ->
                StyledPlayerView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    hideController()
                    useController = false
                    player = exoPlayer
                }
            },
            update = { playerView ->
                when(state) {
                    PlayerStatus.PLAYING -> playerView.player?.play()
                    PlayerStatus.PAUSE -> playerView.player?.pause()
                    else -> {}
                }
            }
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    val currentPlayerState by rememberUpdatedState(newValue = state)
    DisposableEffect(key1 = lifecycleOwner){
        val observer = LifecycleEventObserver { _, event ->
            if(currentPlayerState == PlayerStatus.PLAYING) {
                if(event == Lifecycle.Event.ON_RESUME) {
                    exoPlayer.play()
                } else if(event == Lifecycle.Event.ON_PAUSE) {
                    exoPlayer.pause()
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}