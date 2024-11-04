package com.example.epsilonplayer.presentation.Component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import coil3.toUri
import com.example.epsilonplayer.R
import com.example.epsilonplayer.data.roomdb.MusicEntity
import com.example.epsilonplayer.ui.theme.Inter

@Composable
fun MusicItem(
    music: MusicEntity,
    selected: Boolean,
    isMusicPlaying: Boolean,
    onClick: () -> Unit
){
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
                .height(72.dp)
        ){
            Image(painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(music.albumPath.toUri())
                    .error(R.drawable.ic_music_unknown)
                    .placeholder(R.drawable.ic_music_unknown)
                    .build()
            ),
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium)
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = music.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if(selected) MaterialTheme.colorScheme.primary
                        else LocalContentColor.current,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = music.artist,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if(selected) MaterialTheme.colorScheme.primary
                        else LocalContentColor.current.copy(alpha = 0.7f),
                        fontFamily = Inter
                    )
                )

                AnimatedVisibility(
                    modifier = Modifier.padding(8.dp),
                    visible = selected,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    AudioWave(isMusicPlaying = isMusicPlaying)
                }
            }

        }
    }

}