package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SvuBluePrimary

@Composable
fun FooterSection(
    isDark: Boolean,
    isArabic: Boolean,
    onOpenPrivacyModal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val facebookUrl = "https://www.facebook.com/jafarbadran4"
    val instagramUrl = "https://www.instagram.com/jafar_bdran"
    val twitterUrl = "https://x.com/Jafarbdran3"

    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Authentic Thuluth Developer Signature
            ThuluthSignature(isDark = isDark)

            Spacer(modifier = Modifier.height(14.dp))

            // Copyright Text
            Text(
                text = if (isArabic)
                    "جميع الحقوق محفوظة © - تطوير جعفر بدران - مخصص لطلاب الجامعة الافتراضية السورية SVU"
                else
                    "All Rights Reserved © - Developed by Jafar Bdran - Syrian Virtual University (SVU)",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Social Buttons Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Facebook Button
                SocialButton(
                    label = "Facebook",
                    color = Color(0xFF1877F2),
                    onClick = { openUrl(facebookUrl) }
                )

                // Instagram Button
                SocialButton(
                    label = "Instagram",
                    color = Color(0xFFE4405F),
                    onClick = { openUrl(instagramUrl) }
                )

                // X (Twitter) Button
                SocialButton(
                    label = "X (Twitter)",
                    color = if (isDark) Color.White else Color.Black,
                    textColor = if (isDark) Color.Black else Color.White,
                    onClick = { openUrl(twitterUrl) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Privacy Policy & Terms Link
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onOpenPrivacyModal() },
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PrivacyTip,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 4.dp),
                        tint = SvuBluePrimary
                    )
                    Text(
                        text = if (isArabic) "الشروط والأحكام وسياسة الخصوصية" else "Terms & Privacy Policy",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SvuBluePrimary,
                            fontSize = 11.5.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun SocialButton(
    label: String,
    color: Color,
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        color = color
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = 11.sp
            )
        )
    }
}
