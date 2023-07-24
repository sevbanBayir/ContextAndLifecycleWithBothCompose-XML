package com.sevban.contextandlifecycle.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sevban.contextandlifecycle.R

// Set of Material typography styles to start with
val Montserrat = FontFamily(
    Font(R.font.montserrat_regular)
)

val Roboto = FontFamily(
    Font(R.font.font_roboto_regular, weight = FontWeight.Normal),
    Font(R.font.font_roboto_thin, weight = FontWeight.Thin),
    Font(R.font.font_roboto_medium, weight = FontWeight.Medium),
    Font(R.font.font_roboto_bold, weight = FontWeight.Bold),
)

val Typography = Typography(
    defaultFontFamily = Roboto,
    body1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)