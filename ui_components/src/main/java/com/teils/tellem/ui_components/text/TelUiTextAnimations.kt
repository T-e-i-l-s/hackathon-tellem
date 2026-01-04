package com.teils.tellem.ui_components.text

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith

sealed class TelUiTextAnimations(
    val animation: AnimatedContentTransitionScope<String>.() -> ContentTransform
) {
    class Vertical : TelUiTextAnimations(
        {
            (slideInVertically { it } + fadeIn())
                .togetherWith(slideOutVertically { -it } + fadeOut())
        }
    )

    class Horizontal : TelUiTextAnimations(
        {
            (slideInHorizontally { it } + fadeIn())
                .togetherWith(slideOutHorizontally { -it } + fadeOut())
        }
    )
}