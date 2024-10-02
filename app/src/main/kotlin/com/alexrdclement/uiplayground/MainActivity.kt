package com.alexrdclement.uiplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.navigation.UiPlaygroundNavHost
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            PlaygroundTheme {
                Surface {
                    UiPlaygroundNavHost()
                }
            }
        }
    }
}
