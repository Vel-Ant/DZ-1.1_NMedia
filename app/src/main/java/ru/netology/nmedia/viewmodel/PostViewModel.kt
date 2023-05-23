package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositorySQLiteImpl

private val empty = Post(
    postId = 0,
    author = "",
    content = "",
    published = "",
    liked = false,
    shared = false,
    likesCount = 0,
    shareCount = 0,
    viewingsCount = 0,
    urlVideo = null
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )

    val data = repository.getAll()

    val edited = MutableLiveData(empty)
    fun likeById(postId: Long) = repository.likeById(postId)
    fun shareById(postId: Long) = repository.shareById(postId)
    fun removeById(postId: Long) = repository.removeById(postId)
    fun viewingById(postId: Long) = repository.viewingById(postId)

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
                edited.value = post.copy(
                    content = content,
                    published = java.text.SimpleDateFormat("dd.MM.yyyy' в 'HH:mm:ss' '")
                        .format(System.currentTimeMillis())
                )
            }
        }
    }
}