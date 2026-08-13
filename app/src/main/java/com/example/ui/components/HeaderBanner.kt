package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SvuBluePrimary
import com.example.ui.theme.SvuBluePrimaryDark

@Composable
fun HeaderBanner(
    savedCount: Int,
    isDark: Boolean,
    isArabic: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    onOpenGradebook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (isDark) listOf(
                            Color(0xFF0F172A),
                            Color(0xFF1E293B)
                        ) else listOf(
                            Color(0xFFF8FAFC),
                            Color(0xFFE2E8F0)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Header Brand Logo & App Name
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // JB Badge Icon - 3D Glassmorphic Badge
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF0F172A), // Royal Dark Navy
                                        Color(0xFF1E3A8A), // Deep Royal Navy
                                        Color(0xFF2563EB)  // Electric Blue Accent
                                    )
                                )
                            )
                            .border(
                                width = 1.2.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.6f),
                                        Color(0xFF60A5FA).copy(alpha = 0.4f),
                                        Color.White.copy(alpha = 0.2f)
                                    )
                                ),
                                shape = RoundedCornerShape(13.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            // 3D Shadow for Chrome Text
                            Text(
                                text = "JB",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = Color(0x99000000),
                                    fontSize = 19.sp,
                                    letterSpacing = 1.sp,
                                    fontFamily = FontFamily.SansSerif
                                ),
                                modifier = Modifier.padding(start = 1.dp, top = 1.dp)
                            )

                            // Chrome Silver Text
                            Text(
                                text = "JB",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFFFFFFF), // Pure Chrome White
                                            Color(0xFFE2E8F0), // Platinum Silver
                                            Color(0xFFCBD5E1)  // Cool Silver
                                        )
                                    ),
                                    fontSize = 19.sp,
                                    letterSpacing = 1.sp,
                                    fontFamily = FontFamily.SansSerif
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "JB MARK",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDark) SvuBluePrimaryDark else SvuBluePrimary,
                                    fontSize = 17.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "SVU",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Text(
                            text = if (isArabic) "حاسبة درجات الجامعة الافتراضية السورية" else "Syrian Virtual University Grade Calculator",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.5.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                // Right Actions: Language, Theme, Saved Gradebook
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Saved Subjects Button
                    IconButton(
                        onClick = onOpenGradebook,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                    ) {
                        BadgedBox(
                            badge = {
                                if (savedCount > 0) {
                                    Badge(
                                        containerColor = SvuBluePrimary,
                                        contentColor = Color.White
                                    ) {
                                        Text(text = "$savedCount", fontSize = 10.sp)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = if (isArabic) "سجل الدرجات المحفوظة" else "Saved Gradebook",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Language Toggle
                    IconButton(
                        onClick = onToggleLanguage,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = if (isArabic) "EN" else "عربي",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 12.sp
                            )
                        )
                    }

                    // Theme Toggle
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = if (isArabic) "تغيير الوضع" else "Toggle Theme",
                            tint = if (isDark) Color(0xFFFFB703) else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
