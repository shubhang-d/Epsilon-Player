package com.example.epsilonplayer.presentation.music_screen

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.core.net.toUri
import com.example.epsilonplayer.data.roomdb.MusicEntity
import com.example.epsilonplayer.data.roomdb.MusicRepository
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerEnvironment @Inject constructor(
    @ApplicationContext private val context: Context,
    private val musicRepository: MusicRepository
) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val _allMusics = MutableStateFlow(emptyList<MusicEntity>())
    private val allMusics: StateFlow<List<MusicEntity>> = _allMusics.asStateFlow()

    private val _currentPlayedMusic = MutableStateFlow(MusicEntity.default)
    private val currentPlayedMusic : StateFlow<MusicEntity> = _currentPlayedMusic

    private val _isPlaying = MutableStateFlow(false)
    private val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _playbackMode = MutableStateFlow(PlaybackMode.REPEAT_ALL)
    private val playbackMode: StateFlow<PlaybackMode> = _playbackMode

    private val _hasStopped = MutableStateFlow(false)
    private val hasStopped: StateFlow<Boolean> = _hasStopped

    private val playerHandler: Handler = Handler(Looper.getMainLooper())

    private val exoPlayer = ExoPlayer.Builder(context).build().apply {
        addListener(object: Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if(playbackState == ExoPlayer.STATE_ENDED){
                    when(playbackMode.value){
                        PlaybackMode.REPEAT_ALL -> {
                            val currentIndex = allMusics.value.indexOfFirst {
                                it.audioId == currentPlayedMusic.value.audioId
                            }

                            val nextSong = when{
                                currentIndex == allMusics.value.lastIndex -> allMusics.value[0]
                                currentIndex != -1 -> allMusics.value[currentIndex + 1]
                                else -> allMusics.value[0]
                            }
                        }
                        PlaybackMode.REPEAT_OFF -> {
                            this@apply.stop()
                            _currentPlayedMusic.tryEmit(MusicEntity.default)
                        }
                        PlaybackMode.REPEAT_ONE -> {
                            CoroutineScope(dispatcher).launch { play(currentPlayedMusic.value) }
                        }
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                _isPlaying.tryEmit(isPlaying)
            }
        })
    }
    init {
        CoroutineScope(dispatcher).launch {
            musicRepository.getAllMusics().distinctUntilChanged().collect{
                _allMusics.emit(it)
            }
        }
    }

    fun getAllMusics(): Flow<List<MusicEntity>> {
        return allMusics
    }

    suspend fun play(music:MusicEntity){
        if(music.audioId != MusicEntity.default.audioId){
            _hasStopped.emit(false)
            _currentPlayedMusic.emit(music)

            playerHandler.post {
                exoPlayer.setMediaItem(MediaItem.fromUri(music.audioPath.toUri()))
                exoPlayer.prepare()
                exoPlayer.play()
            }
        }
    }
}


enum class PlaybackMode{
    REPEAT_ONE,
    REPEAT_ALL,
    REPEAT_OFF
}