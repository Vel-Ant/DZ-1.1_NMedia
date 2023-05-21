package ru.netology.nmedia.dto

import androidx.lifecycle.MutableLiveData

data class Post(
    val postId: Long,
    val author: String,
    val content: String,
    val published: String = timeFormat,
    val liked: Boolean = false,
    val shared: Boolean = false,
    val likesCount: Int = 0,
    val shareCount: Int = 0,
    val viewingsCount: Int = 0,
    val urlVideo: String? = null
) {
    companion object {
        val timeFormat = MutableLiveData {
            java.text.SimpleDateFormat("dd.MM.yyyy' в 'HH:mm:ss' '")
                .format(System.currentTimeMillis())
        }.toString()
    }
}