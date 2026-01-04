package com.teils.tellem.ui_components.text

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.teils.tellem.ui_components.R

/**
 * # BitUI: Custom text component
 *
 * Wraps [Text] and animates content changes using [AnimatedContent]
 * if [textAnimation] is provided. Otherwise, shows plain text.
 *
 * @param text Text to display.
 * @param color Text color (default from [R.color.content]).
 * @param style Text style (default from [MaterialTheme.typography]).
 * @param modifier Modifier for layout or styling.
 * @param textAlign Alignment of the text inside its container.
 * @param textAnimation Optional animation for text changes. If `null`, no animation is applied.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TelUiText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = colorResource(R.color.content),
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: TextAlign = TextAlign.Start,
    textAnimation: TelUiTextAnimations? = null
) {
    if (textAnimation != null) {
        AnimatedContent(
            targetState = text,
            transitionSpec = textAnimation.animation
        ) { targetText ->
            Text(
                text = targetText,
                color = color,
                style = style,
                modifier = modifier,
                textAlign = textAlign
            )
        }
    } else {
        Text(
            text = text,
            color = color,
            style = style,
            modifier = modifier,
            textAlign = textAlign
        )
    }
}