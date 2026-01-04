package com.teils.main_menu.presentation.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teils.main_menu.presentation.screens.homeScreen.views.CourseView
import com.teils.main_menu.presentation.views.AiChatView
import com.teils.main_menu.presentation.views.TatarPlacesView
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.ui_components.text.TelUiText

@Composable
fun HomeScreenView(
    navigateToCourseDetailsScreen: (courseId: Int) -> Unit,
    navigateToGamificationScreen: () -> Unit,
    navigateToAiChat: () -> Unit,
    navigateToTatarPlaces: () -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val courses by viewModel.courses.collectAsStateWithLifecycle()
    val xp by viewModel.xp.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background)),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Spacer(Modifier.statusBarsPadding())

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = navigateToGamificationScreen.debounced())
                    ) {
                        TelUiText(
                            text = "1",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.width(8.dp))

                        Icon(
                            painter = painterResource(R.drawable.fire),
                            contentDescription = null,
                            tint = colorResource(R.color.orange),
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(Modifier.width(32.dp))

                        TelUiText(
                            text = xp.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.width(8.dp))

                        Icon(
                            painter = painterResource(R.drawable.coin),
                            contentDescription = null,
                            tint = colorResource(R.color.blue),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))

                TelUiText(
                    text = stringResource(R.string.home_services),
                    style = MaterialTheme.typography.displayMedium
                )

                Spacer(Modifier.height(32.dp))

                AiChatView(navigateToAiChat)

                Spacer(Modifier.height(16.dp))

                TatarPlacesView(navigateToTatarPlaces)

                Spacer(Modifier.height(16.dp))
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                TelUiText(
                    text = stringResource(R.string.home_courses),
                    style = MaterialTheme.typography.displayMedium
                )

                Spacer(Modifier.height(32.dp))

                TelUiText(
                    text = stringResource(R.string.home_block_1),
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(Modifier.height(4.dp))

                TelUiText(
                    text = stringResource(R.string.home_block_1_description),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        if (courses.size >= 7) {
            items(courses.subList(0, 3)) { course ->
                CourseView(
                    course = course,
                    onClick = { navigateToCourseDetailsScreen(course.id) }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    Spacer(Modifier.height(16.dp))

                    TelUiText(
                        text = stringResource(R.string.home_block_2),
                        style = MaterialTheme.typography.titleLarge,
                    )

                    Spacer(Modifier.height(4.dp))

                    TelUiText(
                        text = stringResource(R.string.home_block_2_description),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            items(courses.subList(3, 5)) { course ->
                CourseView(
                    course = course,
                    onClick = { navigateToCourseDetailsScreen(course.id) }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    Spacer(Modifier.height(16.dp))

                    TelUiText(
                        text = stringResource(R.string.home_block_3),
                        style = MaterialTheme.typography.titleLarge,
                    )

                    Spacer(Modifier.height(4.dp))

                    TelUiText(
                        text = stringResource(R.string.home_block_3_description),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            items(courses.subList(5, 7)) { course ->
                CourseView(
                    course = course,
                    onClick = { navigateToCourseDetailsScreen(course.id) }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(Modifier.navigationBarsPadding())
            }
        }
    }
}