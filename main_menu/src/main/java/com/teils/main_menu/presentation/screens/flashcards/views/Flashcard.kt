package com.teils.tellem.main.menu.presentation.screens.flashcards.views

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teils.tellem.main.menu.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Flashcard(
    front: String,
    back: String,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    onOffsetUpdate: (Float) -> Unit,
    disabled: Boolean = false,
    modifier: Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    var flipped by remember { mutableStateOf(false) }
    val rotationY by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        label = "flipAnimation"
    )

    val offsetX = remember { Animatable(0f) }

    LaunchedEffect(front) {
        flipped = false
    }

    val swipeThreshold = 300f
    Box(
        modifier = modifier
            .graphicsLayer {
                this.rotationY = rotationY
                cameraDistance = 8 * density
                translationX = offsetX.value
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    if (disabled) return@detectTapGestures
                    flipped = !flipped
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (disabled) return@detectDragGestures
                        coroutineScope.launch {
                            when {
                                offsetX.value > swipeThreshold -> {
                                    offsetX.animateTo(2000f)
                                    delay(50)
                                    onSwipeRight()
                                    offsetX.snapTo(0f)
                                }

                                offsetX.value < -swipeThreshold -> {
                                    offsetX.animateTo(-2000f)
                                    delay(50)
                                    onSwipeLeft()
                                    offsetX.snapTo(0f)
                                }

                                else -> {
                                    offsetX.animateTo(0f)
                                }
                            }

                            onOffsetUpdate(0f)
                        }
                    }
                ) { change, dragAmount ->
                    if (disabled) return@detectDragGestures
                    change.consume()
                    val newOffset = offsetX.value + dragAmount.x * if (rotationY > 90) -1 else 1
                    coroutineScope.launch {
                        onOffsetUpdate(newOffset)
                        offsetX.snapTo(newOffset)
                    }
                }
            }
            .width(300.dp)
            .height(500.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(colorResource(R.color.secondary_background).copy(if (disabled) 0.5f else 1f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { flipped = !flipped }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    if (rotationY > 90f) {
                        this.rotationY = 180f
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (rotationY <= 90f) {
                Text(
                    text = front,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.content)
                )
            } else {
                Text(
                    text = back,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.content)
                )
            }
        }
    }
}
