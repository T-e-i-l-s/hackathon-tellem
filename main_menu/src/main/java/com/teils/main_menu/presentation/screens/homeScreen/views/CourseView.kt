package com.teils.main_menu.presentation.screens.homeScreen.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.core.utils.courses.CourseModel
import com.teils.tellem.main.menu.R
import com.teils.tellem.ui_components.text.TelUiText

@Composable
fun CourseView(
    course: CourseModel,
    onClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .width(180.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(32.dp))
            .clickable(onClick = onClick.debounced())
            .background(colorResource(R.color.secondary_background))
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TelUiText(
            text = course.name,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(16.dp))

        Column {
            Row {
                repeat(course.difficulty) {
                    Image(
                        painter = painterResource(R.drawable.star),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            TelUiText(
                text = pluralStringResource(
                    R.plurals.home_screen_duration_minutes,
                    course.durationMinutes,
                    course.durationMinutes
                ),
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(R.color.secondary_content)
            )
        }
    }
}