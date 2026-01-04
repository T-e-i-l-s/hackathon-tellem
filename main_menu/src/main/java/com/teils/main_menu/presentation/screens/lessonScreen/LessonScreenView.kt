package com.teils.tellem.main.menu.presentation.screens.lessonScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.ui_components.buttons.TelUiButton
import com.teils.tellem.ui_components.text.TelUiText

@Composable
fun LessonScreenView(
    courseId: Int,
    onLessonCompleted: (passedBefore: Boolean) -> Unit,
    navigateToCourseDetails: () -> Unit,
    viewModel: LessonScreenViewModel = hiltViewModel()
) {
    val course by viewModel.course.collectAsStateWithLifecycle()
    val currentStep by viewModel.currentStep.collectAsStateWithLifecycle()
    val isAnswerCorrect by viewModel.isAnswerCorrect.collectAsStateWithLifecycle()
    val selectedAnswer by viewModel.selectedAnswer.collectAsStateWithLifecycle()


    val progressAnimation by animateFloatAsState(
        targetValue = (currentStep + if (selectedAnswer != null) 1f else 0f) /
                (course?.lesson?.size ?: 1),
    )

    LaunchedEffect(Unit) {
        viewModel.loadData(courseId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
    ) {
        course?.let { courseSafe ->
            val options =
                remember(currentStep) { courseSafe.lesson[currentStep].options.shuffled() }

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

            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 16.dp)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TelUiText(
                    text = course?.name ?: "",
                    style = MaterialTheme.typography.titleSmall,
                    color = colorResource(R.color.secondary_content)
                )

                Spacer(Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = { progressAnimation },
                    modifier = Modifier
                        .width(80.dp)
                        .height(12.dp),
                    color = colorResource(R.color.green),
                    trackColor = colorResource(R.color.secondary_background),
                    strokeCap = StrokeCap.Round,
                    gapSize = (-12).dp,
                    drawStopIndicator = {},
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.salavat),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(36.dp),
                        contentScale = ContentScale.FillBounds
                    )

                    Spacer(Modifier.width(8.dp))

                    TelUiText(
                        text = stringResource(R.string.lesson_salavat),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(8.dp))

                TelUiText(
                    text = courseSafe.lesson[currentStep].phrase,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topEnd = 16.dp,
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            )
                        )
                        .background(colorResource(R.color.secondary_background))
                        .padding(16.dp)
                )

                Spacer(Modifier.height(32.dp))

                AnimatedVisibility(
                    visible = selectedAnswer != null,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TelUiText(
                            text = selectedAnswer ?: "",
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        bottomStart = 16.dp,
                                        bottomEnd = 16.dp
                                    )
                                )
                                .background(colorResource(R.color.green_background))
                                .padding(16.dp)
                                .animateContentSize()
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = selectedAnswer == null,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .navigationBarsPadding()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    options.forEach { option ->
                        TelUiText(
                            text = option,
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(16.dp)
                                )
                                .clickable { viewModel.checkAnswer(option) }
                                .background(colorResource(R.color.green_background))
                                .padding(16.dp)
                        )
                    }
                }
            }


            AnimatedVisibility(
                visible = isAnswerCorrect == true,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Column(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    colorResource(R.color.secondary_background),
                                    colorResource(R.color.green_background)
                                )
                            )
                        )
                        .navigationBarsPadding()
                        .padding(32.dp)
                ) {
                    TelUiText(
                        text = stringResource(R.string.lesson_correct),
                        style = MaterialTheme.typography.displaySmall
                    )

                    Spacer(Modifier.height(32.dp))

                    TelUiButton(
                        text = stringResource(R.string.lesson_next),
                        onClick = { viewModel.nextStep(onLessonCompleted) }.debounced(),
                        containerColor = colorResource(R.color.content),
                        contentColor = colorResource(R.color.background),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }


            AnimatedVisibility(
                visible = isAnswerCorrect == false,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Column(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    colorResource(R.color.secondary_background),
                                    colorResource(R.color.red_background)
                                )
                            )
                        )
                        .navigationBarsPadding()
                        .padding(32.dp)
                ) {
                    TelUiText(
                        text = stringResource(R.string.lesson_incorrect),
                        style = MaterialTheme.typography.displaySmall
                    )

                    Spacer(Modifier.height(16.dp))

                    TelUiText(
                        text = courseSafe.lesson[currentStep].hint,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(Modifier.height(32.dp))

                    TelUiButton(
                        text = stringResource(R.string.lesson_next),
                        onClick = { viewModel.nextStep(onLessonCompleted) }.debounced(),
                        containerColor = colorResource(R.color.content),
                        contentColor = colorResource(R.color.background),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}