package ru.netology.nmedia.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun removeById(postId: Long) {
        dao.removeById(postId)
        posts = posts.filter { it.postId != postId }
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.postId
        val saved = dao.save(post)

        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map { if (it.postId != id) it else saved }
        }
        data.value = posts
    }

    override fun likeById(postId: Long) {
        dao.likeById(postId)
        posts = posts.map {
            if (it.postId == postId) it.copy(
                likesCount = if (it.liked) it.likesCount - 1 else it.likesCount + 1,
                liked = !it.liked
            )
            else it
        }
        data.value = posts
    }

    override fun shareById(postId: Long) {
        dao.shareById(postId)
        posts = posts.map {
            if (it.postId == postId && it.shared) {
                it.copy(
                    shareCount = it.shareCount + 1,
                )
            } else if (it.postId == postId) {
                it.copy(
                    shareCount = it.shareCount + 1,
                    shared = !it.shared
                )
            } else it
        }
        data.value = posts
    }

    override fun viewingById(postId: Long) {
        dao.viewingById(postId)
        posts = posts.map {
            if (it.postId == postId) it.copy(viewingsCount = it.viewingsCount + 1) else it
        }
        data.value = posts
    }
}