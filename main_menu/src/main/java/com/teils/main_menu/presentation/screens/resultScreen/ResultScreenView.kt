package com.teils.main_menu.presentation.screens.resultScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.ui_components.buttons.TelUiButton
import com.teils.tellem.ui_components.text.TelUiText
import com.teils.tellem.ui_components.text.TelUiTextAnimations

@Composable
fun ResultScreenView(
    type: ResultType,
    navigateToCourseDetails: () -> Unit,
    viewModel: ResultScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.giveXp(type)
    }

    BackHandler {
        navigateToCourseDetails()
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .background(
                Brush.verticalGradient(
                    listOf(
                        colorResource(R.color.background),
                        colorResource(R.color.blue).copy(0.2f)
                    )
                )
            )
            .systemBarsPadding(),
    ) {
        Icon(
            painter = painterResource(R.drawable.cross),
            contentDescription = null,
            tint = colorResource(R.color.content),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(28.dp)
                .clickable(
                    onClick = navigateToCourseDetails.debounced(),
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        )

        Row(
            Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TelUiText(
                text = when (type) {
                    ResultType.LESSON -> "+15"
                    ResultType.FLASHCARDS -> "+10"
                    else -> "+30"
                },
                textAnimation = TelUiTextAnimations.Vertical(),
                style = MaterialTheme.typography.displayLarge,
                color = colorResource(R.color.content),
            )

            Spacer(Modifier.width(20.dp))

            Icon(
                painter = painterResource(R.drawable.coin),
                contentDescription = null,
                tint = colorResource(R.color.blue),
                modifier = Modifier.size(51.dp)
            )
        }

        TelUiButton(
            text = "Закрыть",
            onClick = navigateToCourseDetails.debounced(),
            containerColor = colorResource(R.color.content),
            contentColor = colorResource(R.color.background),
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}