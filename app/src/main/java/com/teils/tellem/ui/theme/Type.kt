package com.teils.tellem.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.teils.tellem.alpha.R

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.chiron)),
        fontWeight = FontWeight.Black,
        fontSize = 60.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.chiron)),
        fontWeight = FontWeight.Black,
        fontSize = 36.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.chiron)),
        fontWeight = FontWeight.Black,
        fontSize = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.chiron)),
        fontWeight = FontWeight.Black,
        fontSize = 23.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.chiron)),
        fontWeight = FontWeight.Black,
        fontSize = 19.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.chiron)),
        fontWeight = FontWeight.Black,
        fontSize = 15.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.chiron)),
        fontWeight = FontWeight.Normal,
        fontSize = 23.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.chiron)),
        fontWeight = FontWeight.Normal,
        fontSize = 19.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.chiron)),
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    )
)