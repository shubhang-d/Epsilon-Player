package com.example.epsilonplayer.data.roomdb

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicRepository @Inject constructor(
    db: MusicDB
) {
    private val musicDao = db.MusicDAO()

    fun getAllMusics(): Flow<List<MusicEntity>> =
        musicDao.getAllMusics()

    suspend fun insertMusic(music: MusicEntity) =
        musicDao.insert(music)

    suspend fun insertMusics(vararg music: MusicEntity) =
        musicDao.insert(*music)

    suspend fun deleteMusics(vararg music: MusicEntity) =
        musicDao.delete(*music)
}