package com.shahriar.vn.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme

private val Ink = Color(0xFF090B10)
private val Surface = Color(0xFF11151D)
private val Surface2 = Color(0xFF171D27)
private val Gold = Color(0xFFD6B36A)
private val Cyan = Color(0xFF62D8E8)
private val Text = Color(0xFFF1F3F6)
private val Muted = Color(0xFF9BA6B5)

private val DarkColors = darkColorScheme(
    primary = Gold,
    secondary = Cyan,
    background = Ink,
    surface = Surface,
    surfaceVariant = Surface2,
    onPrimary = Ink,
    onBackground = Text,
    onSurface = Text,
    onSurfaceVariant = Muted
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF75571E),
    secondary = Color(0xFF006977),
    background = Color(0xFFF6F5F1),
    surface = Color.White,
    surfaceVariant = Color(0xFFE9E8E3),
    onBackground = Color(0xFF17181A),
    onSurface = Color(0xFF17181A),
    onSurfaceVariant = Color(0xFF5E6268)
)

@Composable
fun ShahriarVNTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
