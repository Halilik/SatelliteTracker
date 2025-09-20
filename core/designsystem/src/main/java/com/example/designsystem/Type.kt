package com.example.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = BoldFont,
        fontSize = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = SemiBoldFont,
        fontSize = 18.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = RegularFont,
        fontSize = 18.sp,
    )
)