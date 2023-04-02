package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getData(): LiveData<List<Post>>
    fun likeById(postId: Long)
    fun shareById(postId: Long)
    fun viewingById(postId: Long)
}