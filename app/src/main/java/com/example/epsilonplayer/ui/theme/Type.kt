package com.example.epsilonplayer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.epsilonplayer.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Inter = FontFamily(
    Font(R.font.inter_thin,
        weight = FontWeight.Thin),
    Font(R.font.inter_extra_light,
        weight = FontWeight.ExtraLight),
    Font(R.font.inter_light,
        weight = FontWeight.Light),
    Font(R.font.inter_regular,
        weight = FontWeight.Normal),
    Font(R.font.inter_medium,
        weight = FontWeight.Medium),
    Font(R.font.inter_semi_bold,
        weight = FontWeight.SemiBold),
    Font(R.font.inter_bold,
        weight = FontWeight.Bold),
    Font(R.font.inter_extra_bold,
        weight = FontWeight.ExtraBold)


)