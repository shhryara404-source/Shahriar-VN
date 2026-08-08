package com.shahriar.vn.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Colors = darkColorScheme(
    primary = Color(0xFFF0C86F),
    onPrimary = Color(0xFF17120A),
    secondary = Color(0xFF67C8D6),
    background = Color(0xFF050609),
    onBackground = Color(0xFFF5F2EA),
    surface = Color(0xFF0E1016),
    onSurface = Color(0xFFF5F2EA),
    surfaceVariant = Color(0xFF171A21),
    onSurfaceVariant = Color(0xFFBFC0C7)
)

@Composable
fun ShahriarVNTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colors, typography = Typography(), content = content)
}
