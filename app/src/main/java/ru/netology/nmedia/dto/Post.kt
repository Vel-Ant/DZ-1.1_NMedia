package ru.netology.nmedia.dto

data class Post(
    val postId: Long,
    val author: String,
    val content: String,
    val published: String,
    val liked: Boolean = false,
    val shared: Boolean = false,
    val likesCount: Int = 0,
    val shareCount: Int = 0,
    val viewingsCount: Int = 0,
    val urlVideo: String? = null
)