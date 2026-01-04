package com.teils.tellem.main.menu.presentation.screens.practiceScreen.views

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.teils.tellem.core.utils.courses.PracticeModel
import com.teils.tellem.main.menu.R
import com.teils.tellem.ui_components.text.TelUiText

@Composable
fun PracticeListItemView(index: Int, practice: PracticeModel) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, practice.url.toUri())
                context.startActivity(intent)
            }
            .background(colorResource(R.color.secondary_background))
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(colorResource(R.color.green_background))
        ) {
            TelUiText(
                text = (index + 1).toString(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(Modifier.width(24.dp))

        Column {
            TelUiText(
                text = practice.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(4.dp))

            TelUiText(
                text = practice.description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}