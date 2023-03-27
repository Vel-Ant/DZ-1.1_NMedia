package ru.netology.nmedia.dto

import java.math.RoundingMode

//  расчет текущего времени
val timestamp = System.currentTimeMillis()
val sdf = java.text.SimpleDateFormat("dd-MM-yyyy' в 'HH:mm:ss' '")
val sdfFormat = sdf.format(timestamp)

data class Post(

    val postId: Long,
    val author: String,
    val content: String,
    val published: String = sdfFormat,
    val liked: Boolean = false,
    val shared: Boolean = false,
    val likesCount: Int = 4_999,
    val shareCount: Int = 5_999_999,
    val viewingsCount: Int = 7_088_000
)