package com.teils.main_menu.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.ui_components.text.TelUiText

@Composable
fun AiChatView(navigateToAiChat: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .clickable(onClick = navigateToAiChat.debounced())
            .background(colorResource(R.color.secondary_background))
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.stars),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(colorResource(R.color.green_background))
                .padding(8.dp),
            tint = colorResource(R.color.green)
        )

        Spacer(Modifier.width(16.dp))

        Column {
            TelUiText(
                text = stringResource(R.string.common_ai_chat_title),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(4.dp))

            TelUiText(
                text = stringResource(R.string.common_ai_chat_description),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}