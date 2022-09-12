package com.example.videoplayer.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import com.example.videoplayer.model.VideoEvent
import com.example.videoplayer.model.VideoState
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

    Playback(
        exoPlayer = exoPlayer,
        modifier = Modifier.fillMaxSize(),
        lifecycleOwner = lifecycleOwner,
        state = videoState.playerStatus
    )
}