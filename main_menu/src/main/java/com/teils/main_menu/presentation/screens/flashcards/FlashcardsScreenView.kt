package com.teils.tellem.main.menu.presentation.screens.flashcards

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.main.menu.presentation.screens.flashcards.views.Flashcard
import com.teils.tellem.ui_components.text.TelUiText
import kotlin.math.abs

@Composable
fun FlashcardsScreenView(
    courseId: Int,
    onLessonCompleted: (passedBefore: Boolean) -> Unit,
    navigateToCourseDetails: () -> Unit,
    viewModel: FlashcardsScreenViewModel = hiltViewModel()
) {
    val flashcards by viewModel.flashcards.collectAsStateWithLifecycle()

    var flashcardOffsetX by remember { mutableFloatStateOf(0f) }

    val leftColorTarget = if (flashcardOffsetX < -300)
        colorResource(R.color.red_fade)
    else
        colorResource(R.color.background)

    val rightColorTarget = if (flashcardOffsetX > 300)
        colorResource(R.color.green_fade)
    else
        colorResource(R.color.background)

    val leftColor by animateColorAsState(targetValue = leftColorTarget)
    val rightColor by animateColorAsState(targetValue = rightColorTarget)


    var showHint by remember { mutableStateOf(true) }

    val infiniteTransition = rememberInfiniteTransition(label = "hintBlink")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alphaAnim"
    )

    LaunchedEffect(Unit) {
        viewModel.loadData(courseId)
        kotlinx.coroutines.delay(4000)
        showHint = false
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        leftColor,
                        colorResource(R.color.background),
                        colorResource(R.color.background),
                        rightColor,
                    )
                )
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.cross),
            contentDescription = null,
            tint = colorResource(R.color.content),
            modifier = Modifier
                .systemBarsPadding()
                .padding(16.dp)
                .size(32.dp)
                .clickable(
                    onClick = navigateToCourseDetails.debounced(),
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        )

        if (showHint) {
            TelUiText(
                text = "Перетаскивайте карту влево или вправо",
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(R.color.content),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(horizontal = 60.dp, vertical = 40.dp)
                    .alpha(alpha)
            )
        }

        flashcards?.let { flashcardsSafe ->
            if (flashcardsSafe.isNotEmpty()) {
                Flashcard(
                    flashcardsSafe[0].front,
                    flashcardsSafe[0].back,
                    onSwipeLeft = { viewModel.dontRememberCard() },
                    onSwipeRight = { viewModel.rememberCard(onLessonCompleted) },
                    onOffsetUpdate = { flashcardOffsetX = it },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .zIndex(2f)
                )
            }

            if (flashcardsSafe.size > 1) {
                val progress = (abs(flashcardOffsetX) / 300f).coerceIn(0f, 1f)
                val secondCardScale = lerp(0.6f, 1f, progress)

                Flashcard(
                    flashcardsSafe[1].front,
                    flashcardsSafe[1].back,
                    onSwipeLeft = { },
                    onSwipeRight = { },
                    onOffsetUpdate = { },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .scale(secondCardScale)
                        .zIndex(1f),
                    disabled = true
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(32.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TelUiText(
                text = "Нужно повторить",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(R.color.content)
            )

            Spacer(Modifier.width(16.dp))

            TelUiText(
                text = "Хорошо помню",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(R.color.content)
            )
        }
    }
}