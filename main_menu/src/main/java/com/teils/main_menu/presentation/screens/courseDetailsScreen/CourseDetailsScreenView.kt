package com.teils.tellem.main.menu.presentation.screens.courseDetailsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teils.main_menu.presentation.views.AiChatView
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.main.menu.presentation.screens.courseDetailsScreen.views.CardView
import com.teils.tellem.ui_components.buttons.TelUiButton
import com.teils.tellem.ui_components.text.TelUiText

@Composable
fun CourseDetailsScreenView(
    courseId: Int,
    navigateToHomeScreen: () -> Unit,
    navigateToLessonScreen: () -> Unit,
    navigateToFlashcardsScreen: () -> Unit,
    navigateToPracticeScreen: () -> Unit,
    navigateToAiChat: () -> Unit,
    viewModel: CourseDetailsScreenViewModel = hiltViewModel()
) {
    val course by viewModel.course.collectAsStateWithLifecycle()
    val lessonPassed by viewModel.lessonPassed.collectAsStateWithLifecycle()
    val flashcardsPassed by viewModel.flashcardsPassed.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadData(courseId)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.green))
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_left),
            contentDescription = null,
            tint = colorResource(R.color.background),
            modifier = Modifier
                .statusBarsPadding()
                .zIndex(1f)
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(40.dp)
                .clip(CircleShape)
                .clickable(onClick = navigateToHomeScreen.debounced())
                .background(colorResource(R.color.content).copy(0.7f))
                .padding(8.dp)
        )

        Image(
            painter = painterResource(R.drawable.kazan_skyline),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .height(252.dp)
        )

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.statusBarsPadding())

            Spacer(Modifier.height(200.dp))

            Column(
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(colorResource(R.color.background))
                    .padding(32.dp)
            ) {

                course?.let { courseSafe ->
                    TelUiText(
                        text = courseSafe.name,
                        style = MaterialTheme.typography.displaySmall
                    )

                    Spacer(Modifier.height(16.dp))

                    TelUiText(
                        text = courseSafe.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(Modifier.height(32.dp))

                TelUiButton(
                    text = stringResource(R.string.course_details_start_lesson),
                    onClick = { navigateToLessonScreen() }.debounced(),
                    modifier = Modifier.fillMaxWidth(),
                    secondText = if (lessonPassed) stringResource(R.string.common_completed) else null,
                    containerColor = if (lessonPassed) colorResource(R.color.green_background)
                    else colorResource(R.color.green),
                    contentColor = if (lessonPassed) colorResource(R.color.content)
                    else colorResource(R.color.white)
                )

                Spacer(Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    CardView(
                        painterResource(R.drawable.flashcards),
                        stringResource(R.string.course_details_flashcards),
                        onClick = navigateToFlashcardsScreen,
                        wasCompleted = flashcardsPassed
                    )

                    Spacer(Modifier.width(16.dp))

                    CardView(
                        painterResource(R.drawable.practice),
                        stringResource(R.string.course_details_practise),
                        onClick = navigateToPracticeScreen
                    )
                }

                Spacer(Modifier.height(32.dp))

                AiChatView(navigateToAiChat)

                Spacer(Modifier.height(160.dp))

                Spacer(Modifier.navigationBarsPadding())
            }
        }
    }
}