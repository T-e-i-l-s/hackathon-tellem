package com.teils.main_menu.presentation.screens.gamificationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.ui_components.text.TelUiText
import com.teils.tellem.ui_components.text.TelUiTextAnimations

@Composable
fun GamificationScreenView(
    navigateBack: () -> Unit,
    viewModel: GamificationScreenViewModel = hiltViewModel()
) {
    val xp by viewModel.xp.collectAsStateWithLifecycle()

    val maxXp = remember(xp) {
        maxOf(xp.grammar, xp.communication, xp.vocabulary, 1) * 1.3f
    }

    var daysStreak by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        daysStreak = 1
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        colorResource(R.color.background),
                        colorResource(R.color.orange),
                        colorResource(R.color.orange)
                    )
                )
            )
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = null,
                tint = colorResource(R.color.content),
                modifier = Modifier
                    .size(28.dp)
                    .clickable(
                        onClick = navigateBack.debounced(),
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )
        }

        Spacer(Modifier.height(32.dp))

        TelUiText(
            text = daysStreak.toString(),
            textAnimation = TelUiTextAnimations.Vertical(),
            style = MaterialTheme.typography.displayLarge,
            color = colorResource(R.color.content),
        )

        Spacer(Modifier.height(16.dp))

        TelUiText(
            text = stringResource(R.string.gamification_days_streak),
            textAnimation = TelUiTextAnimations.Vertical(),
            style = MaterialTheme.typography.titleMedium,
            color = colorResource(R.color.content)
        )

        Spacer(Modifier.height(32.dp))

        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(colorResource(R.color.background))
                .padding(32.dp)
        ) {
            TelUiText(
                text = stringResource(R.string.gamification_experience),
                textAnimation = TelUiTextAnimations.Vertical(),
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(R.color.content)
            )

            Spacer(Modifier.height(32.dp))

            TelUiText(
                text = stringResource(R.string.gamification_experience_grammar),
                textAnimation = TelUiTextAnimations.Vertical(),
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(R.color.content)
            )

            Spacer(Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { xp.grammar / maxXp },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = colorResource(R.color.green),
                trackColor = colorResource(R.color.secondary_background),
                strokeCap = StrokeCap.Round,
                gapSize = (-12).dp,
                drawStopIndicator = {},
            )

            Spacer(Modifier.height(16.dp))

            TelUiText(
                text = xp.grammar.toString() + " xp",
                textAnimation = TelUiTextAnimations.Vertical(),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(R.color.content)
            )

            // -----------------------------------------

            Spacer(Modifier.height(32.dp))

            TelUiText(
                text = stringResource(R.string.gamification_experience_vocabulary),
                textAnimation = TelUiTextAnimations.Vertical(),
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(R.color.content)
            )

            Spacer(Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { xp.vocabulary / maxXp },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = colorResource(R.color.orange),
                trackColor = colorResource(R.color.secondary_background),
                strokeCap = StrokeCap.Round,
                gapSize = (-12).dp,
                drawStopIndicator = {},
            )

            Spacer(Modifier.height(16.dp))

            TelUiText(
                text = xp.vocabulary.toString() + " xp",
                textAnimation = TelUiTextAnimations.Vertical(),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(R.color.content)
            )

            // -----------------------------------------

            Spacer(Modifier.height(32.dp))

            TelUiText(
                text = stringResource(R.string.gamification_experience_communication),
                textAnimation = TelUiTextAnimations.Vertical(),
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(R.color.content)
            )

            Spacer(Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { xp.communication / maxXp },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = colorResource(R.color.blue),
                trackColor = colorResource(R.color.secondary_background),
                strokeCap = StrokeCap.Round,
                gapSize = (-12).dp,
                drawStopIndicator = {},
            )

            Spacer(Modifier.height(16.dp))

            TelUiText(
                text = xp.communication.toString() + " xp",
                textAnimation = TelUiTextAnimations.Vertical(),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(R.color.content)
            )
        }
    }
}