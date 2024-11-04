package com.example.epsilonplayer

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.epsilonplayer.presentation.music_screen.MusicScreen
import com.example.epsilonplayer.presentation.permission.checkAndRequestPermissions
import com.example.epsilonplayer.ui.theme.EpsilonPlayerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listOfPermission = mutableListOf<String>().apply{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                add(android.Manifest.permission.READ_MEDIA_AUDIO)
                add(android.Manifest.permission.POST_NOTIFICATIONS)
            }else{
                add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        enableEdgeToEdge()
        setContent {
            EpsilonPlayerTheme {
                checkAndRequestPermissions(permissions = listOfPermission){
                    MusicScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EpsilonPlayerTheme {
        Greeting("Android")
    }
}