package com.example.videoplayer.model

sealed class VideoEvent {
    object ToggleStatus: VideoEvent()
    object VideoLoaded: VideoEvent()
    object VideoError: VideoEvent()
}