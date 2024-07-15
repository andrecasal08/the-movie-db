package com.acdevs.themoviedb.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.acdevs.themoviedb.R

object AppFont {
    val poppinsFont = FontFamily(
        Font(R.font.poppins_regular, ),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_semi_bold, FontWeight.Medium),
        Font(R.font.poppins_black, FontWeight.Black),
    )
}