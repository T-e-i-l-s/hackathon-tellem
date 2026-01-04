package com.teils.main_menu.presentation.screens.tatarPlacesScreen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.ui_components.text.TelUiText

@Composable
fun TatarPlacesScreenView(
    navigateBack: () -> Unit,
    viewModel: TatarPlacesScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val places by viewModel.places.collectAsStateWithLifecycle()

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
                        onClick = navigateBack.debounced(),
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )

            Spacer(Modifier.height(32.dp))

            TelUiText(
                text = stringResource(R.string.common_tatar_places_title),
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(Modifier.height(4.dp))

            TelUiText(
                text = stringResource(R.string.common_tatar_places_description),
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(Modifier.height(16.dp))
        }

        items(places) { item ->
            Column(
                Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(colorResource(R.color.secondary_background))
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, item.url.toUri())
                        context.startActivity(intent)
                    }
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .height(180.dp)
                        .background(colorResource(R.color.ternary_background))
                )

                Spacer(Modifier.height(16.dp))

                TelUiText(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = colorResource(R.color.content)
                )

                Spacer(Modifier.height(4.dp))

                TelUiText(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = colorResource(R.color.content)
                )

                Spacer(Modifier.height(16.dp))
            }
        }

        item {
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}