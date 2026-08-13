package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ThuluthSignature(
    modifier: Modifier = Modifier,
    isDark: Boolean = true
) {
    // Warm Off-White color for dark theme / Deep Charcoal Slate for light theme
    val warmOffWhiteColor = if (isDark) Color(0xFFFAF8F5) else Color(0xFF1E293B)
    val glassShadowColor = if (isDark) Color(0x80000000) else Color(0x30000000)
    val subTextColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Main Calligraphy Signature Text: "جعفر بدران" in Arabic Thuluth Calligraphy
        Box(contentAlignment = Alignment.Center) {
            // Very subtle glass shadow layer behind letters
            Text(
                text = "جعفر بدران",
                style = TextStyle(
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.2.sp,
                    fontFamily = FontFamily.Serif
                ),
                color = glassShadowColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 1.5.dp, top = 2.dp)
            )

            // Primary Calligraphy Text in Warm Off-White
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Text(
                    text = "جعفر بدران",
                    style = TextStyle(
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.2.sp,
                        fontFamily = FontFamily.Serif,
                        color = warmOffWhiteColor
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Subtitle Calligraphy Flourish label
        Text(
            text = "JAFAR BDRAN",
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.2.sp
            ),
            color = subTextColor,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

