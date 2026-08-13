package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SvuBluePrimary

@Composable
fun PrivacyPolicyModal(
    isArabic: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val privacyWebUrl = "https://sites.google.com/view/jbmediagroub/الشروط-والأحكام-وسياسة-الخصوصية"

    val localPolicyText = if (isArabic) {
        "تطبيق JB MARK SVU يعمل أوفلاين دون جمع أو معالجة أي بيانات شخصية. العلامات والدرجات التي يدخلها الطالب تُحسب محلياً على جهازه ولا تُنقل لأي سيرفر خارجي."
    } else {
        "The JB MARK SVU app operates 100% offline without collecting or processing any personal data. All marks entered are calculated locally on your device and are never transmitted to external servers."
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.PrivacyTip,
                    contentDescription = null,
                    tint = SvuBluePrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isArabic) "الشروط والأحكام وسياسة الخصوصية" else "Terms & Privacy Policy",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        text = {
            Column {
                Text(
                    text = localPolicyText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isArabic) "تطوير: جعفر بدران" else "Developed by: Jafar Bdran",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(privacyWebUrl))
                    context.startActivity(intent)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SvuBluePrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.Launch,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = if (isArabic) "فتح الموقع الرسمي للسياسة" else "Open Official Policy Site",
                    fontSize = 12.sp
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = if (isArabic) "إغلاق" else "Close",
                    fontSize = 12.sp
                )
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}
