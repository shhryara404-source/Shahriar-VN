package com.shahriar.vn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.shahriar.vn.ui.ShahriarVNApp
import com.shahriar.vn.ui.theme.ShahriarVNTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkMode by rememberSaveable { mutableStateOf(true) }
            ShahriarVNTheme(darkTheme = darkMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ShahriarVNApp(
                        darkMode = darkMode,
                        onToggleTheme = { darkMode = !darkMode }
                    )
                }
            }
        }
    }
}
