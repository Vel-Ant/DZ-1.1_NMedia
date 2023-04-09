package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.sdfFormat
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory

private val empty = Post(
    postId = 0,
    author = "",
    content = "",
    published = sdfFormat,
    liked = false,
    shared = false,
    likesCount = 0,
    shareCount = 0,
    viewingsCount = 0
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()

    val data = repository.getAll()
    fun likeById(postId: Long) = repository.likeById(postId)
    fun shareById(postId: Long) = repository.shareById(postId)
    fun removeById(postId: Long) = repository.removeById(postId)
    fun viewingById(postId: Long) = repository.viewingById(postId)

    val edited = MutableLiveData(empty)
    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun cancelEdit() {
        edited.value = empty
    }

    fun changeContent(content: String) {
        edited.value?.let { post ->
            if (content != post.content) {
                edited.value = post.copy(content = content)
            }
        }
    }
}