package com.example.videoplayer

import androidx.lifecycle.ViewModel
import com.example.videoplayer.model.PlayerStatus
import com.example.videoplayer.model.VideoEvent
import com.example.videoplayer.model.VideoState
import kotlinx.coroutines.flow.MutableStateFlow

class VideoViewModel: ViewModel() {

    val uiState = MutableStateFlow(VideoState())

    fun handleEvent(event: VideoEvent) {
        when(event) {
            is VideoEvent.VideoLoaded -> {
                uiState.value = uiState.value.copy(playerStatus = PlayerStatus.IDLE)
            }
            is VideoEvent.VideoError -> {
                uiState.value = uiState.value.copy(playerStatus = PlayerStatus.ERROR)
            }
            is VideoEvent.ToggleStatus -> {
                togglePlayerStatus()
            }
        }
    }

    private fun togglePlayerStatus() {
        val currentPlayerStatus = uiState.value.playerStatus
        val newPlayerStatus = if(currentPlayerStatus != PlayerStatus.PLAYING)  {
            PlayerStatus.PLAYING
        } else {
            PlayerStatus.PAUSE
        }
        uiState.value = uiState.value.copy(playerStatus = newPlayerStatus)
    }
}