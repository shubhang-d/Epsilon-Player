package com.example.epsilonplayer.presentation.Component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import com.example.epsilonplayer.R
import com.example.epsilonplayer.data.roomdb.MusicEntity

@Composable
fun BottomMusicPlayer(
    currentMusic: MusicEntity,
    currentDuration: Long,
    isPlaying: Boolean,
    onClick: () -> Unit,
    onPlayPausedClicked: (isPlaying: Boolean) -> Unit
){
    val progress = remember(currentDuration, currentMusic.duration){
        currentDuration.toFloat() / currentMusic.duration.toFloat()
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier.clickable { onClick() }
            .height(BottomMusicPlayerHeight.value)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            AlbumImage(albumPath = currentMusic.albumPath)

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    maxLines = 2,
                    text = currentMusic.title,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    maxLines = 2,
                    text = currentMusic.artist,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            PlayPauseButton(
                progress = progress,
                isPlaying  = isPlaying
            ){

            }
        }
    }
}

@Composable
fun PlayPauseButton(
    progress: Float,
    isPlaying: Boolean,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable { onClick() }
    ){
        Icon(
            painter = painterResource(
                id = if (isPlaying) R.drawable.ic_pause_filled_rounded else R.drawable.ic_play_filled_rounded
            ),
            contentDescription = null
        )
    }
}

@Composable
fun AlbumImage(albumPath: String){
    Card(
        shape = RoundedCornerShape(100.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.size(56.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(MaterialTheme.colorScheme.onSurface)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(100.dp)
                )
                .align(Alignment.CenterHorizontally)
                .zIndex(2f)
        ) {
            Image(painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(albumPath.toUri())
                    .error(R.drawable.ic_music_unknown)
                    .placeholder(R.drawable.ic_music_unknown)
                    .build()
            ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize())
        }
    }
}

object BottomMusicPlayerHeight{
    val value = 96.dp
}