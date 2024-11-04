package com.example.epsilonplayer.data.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.epsilonplayer.R
import com.example.epsilonplayer.data.roomdb.MusicEntity
import kotlin.time.Duration.Companion.milliseconds

object MusicUtil {
    fun fetchMusicFromDevice(
        context: Context,
        isTrackSmallerThan100KBSkipped: Boolean = true,
        isTrackSmallerThan60SecondsSkipped: Boolean = true
    ): List<MusicEntity>{
        val musicList = mutableListOf<MusicEntity>()

        val audioUriExternal = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val musicProjection = listOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.SIZE
        )

        val cursorIndexSongId: Int
        val cursorIndexSongTitle: Int
        val cursorIndexSongArtist: Int
        val cursorIndexSongDuration: Int
        val cursorIndexSongAlbumId: Int
        val cursorIndexSongSize: Int

        val songCursor = context.contentResolver.query(
            audioUriExternal,
            musicProjection.toTypedArray(),
            null,
            null,
            null
        )

        if(songCursor != null){
            cursorIndexSongId = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            cursorIndexSongTitle = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            cursorIndexSongArtist = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            cursorIndexSongDuration = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            cursorIndexSongAlbumId = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            cursorIndexSongSize = songCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)

            while(songCursor.moveToNext()){
                val audioId = songCursor.getLong(cursorIndexSongId)
                val title = songCursor.getString(cursorIndexSongTitle)
                val artist = songCursor.getString(cursorIndexSongArtist)
                val duration = songCursor.getLong(cursorIndexSongDuration)
                val albumId = songCursor.getString(cursorIndexSongAlbumId)
                val size = songCursor.getLong(cursorIndexSongSize)
                val albumPath = Uri.withAppendedPath(Uri.parse("content://media/external/audio/albumart"), albumId)
                val musicPath = Uri.withAppendedPath(audioUriExternal,"" + audioId)
                val durationGreaterThan60Sec = duration.milliseconds.inWholeMilliseconds > 60
                val sizeGreaterThan100KB = (size/1024) >100

                val music = MusicEntity(
                    audioId = audioId,
                    title = title,
                    artist = if(artist.equals("<unknown>", ignoreCase = true)) context.getString(R.string.unknown) else artist,
                    duration = duration,
                    albumPath = albumPath.toString(),
                    audioPath = musicPath.toString()
                )

                when{
                    isTrackSmallerThan100KBSkipped && isTrackSmallerThan60SecondsSkipped -> {
                        if(sizeGreaterThan100KB && durationGreaterThan60Sec) musicList.add(music)
                    }
                    !isTrackSmallerThan100KBSkipped && isTrackSmallerThan60SecondsSkipped -> {
                        if(durationGreaterThan60Sec) musicList.add(music)
                    }
                    isTrackSmallerThan100KBSkipped && !isTrackSmallerThan60SecondsSkipped -> {
                        if(sizeGreaterThan100KB) musicList.add(music)
                    }
                    !isTrackSmallerThan100KBSkipped && !isTrackSmallerThan60SecondsSkipped -> {
                        musicList.add(music)
                    }
                }
            }
            songCursor.close()
        }

        return musicList
    }
}