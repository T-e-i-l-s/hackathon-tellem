package com.teils.tellem.main.menu.presentation.screens.practiceScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.main.menu.presentation.screens.practiceScreen.views.PracticeListItemView
import com.teils.tellem.ui_components.text.TelUiText

@Composable
fun PracticeScreenView(
    courseId: Int,
    navigateToCourseDetails: () -> Unit,
    viewModel: PracticeScreenViewModel = hiltViewModel()
) {
    val course by viewModel.course.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadData(courseId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(
                Modifier
                    .statusBarsPadding()
                    .height(16.dp)
            )

            Icon(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = null,
                tint = colorResource(R.color.content),
                modifier = Modifier
                    .size(28.dp)
                    .clickable(
                        onClick = navigateToCourseDetails.debounced(),
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )

            Spacer(Modifier.height(32.dp))

            TelUiText(
                text = stringResource(R.string.practice_title),
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(Modifier.height(4.dp))

            TelUiText(
                text = course?.name ?: "",
                style = MaterialTheme.typography.titleSmall
            )
        }

        itemsIndexed(course?.practice ?: emptyList()) { index, item ->
            PracticeListItemView(
                index = index,
                practice = item
            )
        }
        item {
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}