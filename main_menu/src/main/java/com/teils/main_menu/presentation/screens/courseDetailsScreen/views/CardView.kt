package com.teils.tellem.main.menu.presentation.screens.courseDetailsScreen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.ui_components.text.TelUiText

@Composable
fun RowScope.CardView(
    image: Painter,
    text: String,
    wasCompleted: Boolean? = null,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .height(200.dp)
            .weight(1f)
            .clip(RoundedCornerShape(32.dp))
            .clickable(onClick = onClick.debounced())
            .background(colorResource(R.color.secondary_background))
            .padding(24.dp)
    ) {
        Icon(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .height(80.dp),
            tint = colorResource(R.color.secondary_content)
        )

        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            TelUiText(
                text = text,
                color = colorResource(R.color.content)
            )

            if (wasCompleted == true) {
                Spacer(Modifier.height(4.dp))

                TelUiText(
                    text = stringResource(R.string.common_completed),
                    color = colorResource(R.color.secondary_content),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}