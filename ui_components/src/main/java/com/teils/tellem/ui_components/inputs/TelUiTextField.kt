package com.teils.tellem.ui_components.inputs

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.teils.tellem.ui_components.R

/**
 * Custom text field implementation.
 * @param value indicates text inside field.
 * @param onValueChange indicates what to do on input value update.
 * @param placeholder indicates text to show by default.
 * @param imeAction indicates which button to show in the bottom right corner of keyboard.
 * @param keyboardType indicates which keyboard to show.
 * @param onKeyboardAction indicates what to do on keyboard imeAction.
 */
@Composable
fun TelUiTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    shape: Shape = RoundedCornerShape(16.dp),
    imeAction: ImeAction = ImeAction.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    onKeyboardAction: () -> Unit = {},
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    secureInput: Boolean = false
) {
    var isVisible by remember { mutableStateOf(!secureInput) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = colorResource(id = R.color.secondary_content),
                style = textStyle
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = colorResource(id = R.color.content),
            focusedTextColor = colorResource(id = R.color.content),
            errorTextColor = colorResource(id = R.color.content),
            cursorColor = colorResource(id = R.color.content),
            errorCursorColor = colorResource(id = R.color.content),
            focusedContainerColor = colorResource(R.color.ternary_background),
            unfocusedContainerColor = colorResource(R.color.ternary_background),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            unfocusedPlaceholderColor = colorResource(id = R.color.secondary_content),
            focusedPlaceholderColor = colorResource(id = R.color.secondary_content),
            errorPlaceholderColor = colorResource(id = R.color.secondary_content),
            selectionColors = TextSelectionColors(
                handleColor = colorResource(id = R.color.content),
                backgroundColor = colorResource(id = R.color.content).copy(0.4f)
            )
        ),
        modifier = modifier,
        textStyle = textStyle,
        shape = shape,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(onAny = { onKeyboardAction() }),
        readOnly = readOnly,
        singleLine = singleLine,
        visualTransformation = if (!isVisible) PasswordVisualTransformation() else VisualTransformation.None
    )
}