package com.teils.main_menu.presentation.screens.aiChatScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teils.tellem.core.utils.compose.debounced
import com.teils.tellem.main.menu.R
import com.teils.tellem.ui_components.inputs.TelUiTextField
import com.teils.tellem.ui_components.text.TelUiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private suspend fun LazyListState.scrollToEnd() {
    if (layoutInfo.totalItemsCount > 0) {
        scrollToItem(layoutInfo.totalItemsCount - 1)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiChatScreenView(
    navigateBack: () -> Unit,
    viewModel: AiChatScreenViewModel = hiltViewModel()
) {
    val inputText by viewModel.inputText.collectAsStateWithLifecycle()
    val feedback by viewModel.feedback.collectAsStateWithLifecycle()
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val sendAvailable by viewModel.sendAvailable.collectAsStateWithLifecycle()
    val messagesShown by viewModel.messagesShown.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()

    val isImeVisible = WindowInsets.isImeVisible

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isImeVisible) {
        println(isImeVisible)
        coroutineScope.launch {
            if (isImeVisible) {
                delay(100)
                if (messages.isNotEmpty()) {
                    listState.scrollToEnd()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.addShownMessage(messages.size + 2)
        listState.scrollToEnd()
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size)
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .imePadding()
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_left),
            contentDescription = null,
            tint = colorResource(R.color.content),
            modifier = Modifier
                .systemBarsPadding()
                .padding(16.dp)
                .clip(CircleShape)
                .zIndex(1f)
                .size(40.dp)
                .background(colorResource(R.color.ternary_background).copy(0.95f))
                .clickable(
                    onClick = navigateBack.debounced(),
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(8.dp)
        )

        Column(
            Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
                .imePadding(),
        ) {
            if (messages.isEmpty()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.salavat),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp),
                        contentScale = ContentScale.FillBounds
                    )

                    Spacer(Modifier.height(32.dp))

                    TelUiText(
                        text = stringResource(R.string.salavat),
                        style = MaterialTheme.typography.titleMedium,
                        color = colorResource(R.color.content)
                    )

                    Spacer(Modifier.height(8.dp))

                    TelUiText(
                        text = stringResource(R.string.salavat_start_text),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(R.color.secondary_content),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Spacer(Modifier.statusBarsPadding())
                    }

                    itemsIndexed(messages) { index, message ->
                        var visible by remember { mutableStateOf(index < messagesShown - 1) }

                        LaunchedEffect(Unit) {
                            visible = true
                            if (index >= messagesShown - 1) {
                                viewModel.addShownMessage()
                            }
                        }

                        AnimatedVisibility(
                            visible = visible,
                            enter = slideInHorizontally { if (message.role == "assistant") -it else it } + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            if (message.role == "assistant") {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(R.drawable.salavat),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .size(36.dp)
                                        )

                                        Spacer(Modifier.width(8.dp))

                                        TelUiText(
                                            text = stringResource(R.string.salavat),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }

                                    Spacer(Modifier.height(8.dp))

                                    TelUiText(
                                        text = message.text,
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
                                }
                            } else if (message.role == "user") {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    var expanded by remember { mutableStateOf(false) }

                                    feedback.getOrNull(index / 2 - 1)?.let { text ->
                                        Box {
                                            IconButton(onClick = {
                                                expanded = !expanded
                                            }) {
                                                Icon(
                                                    painter = painterResource(R.drawable.info),
                                                    contentDescription = null,
                                                    tint = colorResource(R.color.green)
                                                )
                                            }

                                            DropdownMenu(
                                                expanded = expanded,
                                                onDismissRequest = { expanded = false },
                                                containerColor = colorResource(R.color.ternary_background),
                                                shape = RoundedCornerShape(16.dp),
                                                modifier = Modifier
                                                    .widthIn(max = 300.dp)
                                                    .padding(horizontal = 16.dp),
                                                properties = PopupProperties(focusable = false)
                                            ) {
                                                feedback.getOrNull(index / 2 - 1)?.let { text ->
                                                    TelUiText(
                                                        text = text,
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        color = colorResource(R.color.content),
                                                        modifier = Modifier.padding(16.dp),
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    Spacer(Modifier.width(4.dp))

                                    TelUiText(
                                        text = message.text,
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
                    }

                    if (!sendAvailable) {
                        item {
                            var visible by remember { mutableStateOf(false) }

                            LaunchedEffect(Unit) {
                                delay(500)
                                visible = true
                            }

                            AnimatedVisibility(
                                visible = visible,
                                enter = fadeIn(animationSpec = tween(500)),
                                exit = fadeOut()
                            ) {
                                val infiniteTransition = rememberInfiniteTransition(label = "")
                                val alpha by infiniteTransition.animateFloat(
                                    initialValue = 1f,
                                    targetValue = 0.3f,
                                    animationSpec = infiniteRepeatable(
                                        animation = tween(
                                            durationMillis = 800,
                                            easing = LinearEasing
                                        ),
                                        repeatMode = RepeatMode.Reverse
                                    ),
                                    label = ""
                                )

                                TelUiText(
                                    text = stringResource(R.string.salavat_is_writing),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = colorResource(R.color.secondary_content),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp)
                                        .alpha(alpha)
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.secondary_background))
                    .navigationBarsPadding()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TelUiTextField(
                    value = inputText,
                    onValueChange = viewModel::updateInputText,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = stringResource(R.string.salavat_input_placeholder)
                )

                Spacer(modifier = Modifier.width(16.dp))

                if (sendAvailable) {
                    Icon(
                        painter = painterResource(R.drawable.send),
                        contentDescription = null,
                        tint = colorResource(R.color.white),
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(colorResource(R.color.green))
                            .clickable(
                                onClick = viewModel::sendMessage.debounced(),
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            )
                            .padding(10.dp)
                    )
                } else {
                    Box(
                        Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(colorResource(R.color.secondary_background))
                            .padding(12.dp)
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(R.color.content),
                            trackColor = Color.Transparent,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(32.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }
    }
}