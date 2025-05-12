package com.attt.vazitaapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = Color(0xFF1A3C6C),
    primary = Color(0xFF2C5B9B),
    secondary = Color(0xFF4E7AB3),
    tertiary = Color(0xFFEDF1F7),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    onTertiary = Color(0xFF1A3C6C),
    onBackground = Color(0xFFFFFFFF),
)




private val LightColorScheme = lightColorScheme(
    background = Color(0xFFF5F8FC),       // Soft light blue/gray
    primary = Color(0xFF2C5B9B),          // Same as dark mode for brand consistency
    secondary = Color(0xFF4E7AB3),        // Slightly less saturated for light background
    tertiary = Color(0xFF1A3C6C),         // Deeper color for accents
    onPrimary = Color(0xFFFFFFFF),        // White text on primary
    onSecondary = Color(0xFFFFFFFF),      // White text on secondary
    onTertiary = Color(0xFFFFFFFF),       // White text on tertiary
    onBackground = Color(0xFF000000),     // Dark text on light background
)

@Composable
fun VazitaappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}