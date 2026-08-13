package com.example.ui.theme

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
    primary = SvuBluePrimaryDark,
    onPrimary = Color(0xFF003355),
    primaryContainer = Color(0xFF004B75),
    onPrimaryContainer = Color(0xFFCBE6FF),
    secondary = SvuCyanSecondaryDark,
    onSecondary = Color(0xFF00363F),
    background = SlateBackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SlateSurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SlateSurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    error = CrimsonFail
)

private val LightColorScheme = lightColorScheme(
    primary = SvuBluePrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD0E8FF),
    onPrimaryContainer = Color(0xFF001D33),
    secondary = SvuCyanSecondary,
    onSecondary = Color.White,
    background = SlateBackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SlateSurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SlateSurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    error = CrimsonFail
)

@Composable
fun JbMarkSvuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set false to preserve SVU branded dark navy aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
