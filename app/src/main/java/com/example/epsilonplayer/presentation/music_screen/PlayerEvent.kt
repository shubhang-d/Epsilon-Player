package com.example.epsilonplayer.presentation.music_screen

import com.example.epsilonplayer.data.roomdb.MusicEntity

sealed interface PlayerEvent {
    data class Play(val music:MusicEntity): PlayerEvent
    data class PlayPause(val isPlaying:Boolean): PlayerEvent
    data class SetShowBottomPlayer(val isShowed:Boolean): PlayerEvent

    object RefreshMusicList: PlayerEvent
}