package ru.netology.nmedia.repository


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory(
    private val context: Context,
) : PostRepository {
    companion object {
        private val FILE_NAME = "posts.json"
    }

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private var posts: List<Post> = readPosts()
        set(value) {
            field = value
            sync()
        }
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    private fun sync() {    // запись постов в JSON
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    // чтение постов из JSON
    private fun readPosts(): List<Post> {
        val file = context.filesDir.resolve(FILE_NAME)
        return if (file.exists()) {
            context.openFileInput(FILE_NAME).bufferedReader().use {
                gson.fromJson(it, type)
            }
        } else {
            emptyList()
        }
    }

    override fun removeById(postId: Long) {
        posts = posts.filter { it.postId != postId }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.postId == 0L) {
            posts = listOf(
                post.copy(
                    postId = (posts.firstOrNull()?.postId ?: 0L) + 1,
                    author = "Me"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.postId == post.postId) it.copy(content = post.content) else it
        }
        data.value = posts
        sync()
    }

    override fun likeById(postId: Long) {
        posts = posts.map {
            if (it.postId == postId) it.copy(
                likesCount = if (it.liked) it.likesCount - 1 else it.likesCount + 1,
                liked = !it.liked
            )
            else it
        }
        data.value = posts
        sync()
    }

    override fun shareById(postId: Long) {
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
        sync()
    }

    override fun viewingById(postId: Long) {
        posts = posts.map {
            if (it.postId == postId) it.copy(viewingsCount = it.viewingsCount + 1) else it
        }
        data.value = posts
        sync()
    }
}