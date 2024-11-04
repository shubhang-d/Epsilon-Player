package com.example.epsilonplayer.presentation.music_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.epsilonplayer.data.roomdb.MusicEntity
import com.example.epsilonplayer.presentation.Component.MusicItem
import com.example.epsilonplayer.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicScreen(
    playerVM: PlayerViewModel = hiltViewModel()
){
    val context = LocalContext.current

    val musicUiState by playerVM.uiState.collectAsState()

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ){
        Column {
            Spacer(modifier = Modifier.height(Dimens.one))
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "Epsilon Music Player",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            )
            MusicListContent(musicUiState = musicUiState){ music ->
                //
            }
        }
    }
}

@Composable
fun MusicListContent(musicUiState: MusicUiState, onSelectedMusic: (music: MusicEntity) -> Unit){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val currentAudioId = musicUiState.currentPlayedMusic.audioId
        itemsIndexed(musicUiState.musicList){_, music ->
            MusicItem(
                music = music,
                selected = (music.audioId == currentAudioId),
                isMusicPlaying = musicUiState.isPlaying,
                onClick = {onSelectedMusic.invoke(music)}
            )
        }
    }
}
