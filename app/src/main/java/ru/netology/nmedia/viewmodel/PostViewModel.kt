package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()

    val data: LiveData<List<Post>> = repository.getData()
    fun likeById(postId: Long) = repository.likeById(postId)
    fun shareById(postId: Long) = repository.shareById(postId)
    fun viewingById(postId: Long) = repository.viewingById(postId)

}