package com.alexrdclement.uiplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.alexrdclement.uiplayground.navigation.UiPlaygroundNavHost
import com.alexrdclement.uiplayground.ui.theme.UiPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            UiPlaygroundTheme {
                Surface {
                    UiPlaygroundNavHost()
                }
            }
        }
    }
}
