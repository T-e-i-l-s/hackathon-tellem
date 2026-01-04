package com.teils.tellem.ui_components.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.teils.tellem.ui_components.R
import com.teils.tellem.ui_components.text.TelUiText

/**
 * # BitUI: Custom button component
 *
 * @param text Button label.
 * @param onClick Click action.
 * @param modifier Modifier for styling/positioning.
 * @param containerColor Background color.
 * @param contentColor Text color.
 * @param shape Button shape.
 */
@Composable
fun TelUiButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = colorResource(R.color.green),
    contentColor: Color = colorResource(R.color.white),
    secondText: String? = null,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 32.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TelUiText(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )

            secondText?.let {
                Spacer(Modifier.height(4.dp))

                TelUiText(
                    text = secondText,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor
                )
            }
        }
    }
}
