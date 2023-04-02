package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {

    private var post = listOf(
        Post(
            2,
            "Нетология. Университет интернет-профессий будущего",
            "Привет, это второй пост и новая Нетология! \nКогда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила,которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен\n → http://netolo.gy/fyb"
        ), Post(
            1,
            "Нетология. Университет интернет-профессий будущего",
            "Привет, это новая Нетология! \nКогда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила,которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен\n → http://netolo.gy/fyb"
        )
    )

    private val data = MutableLiveData(post)

    override fun getData(): LiveData<List<Post>> = data

    override fun likeById(postId: Long) {
        post = post.map { post ->
            if (post.postId == postId) {
                post.copy(
                    likesCount = if (post.liked) post.likesCount - 1 else post.likesCount + 1,
                    liked = !post.liked
                )
            } else {
                post
            }
        }

        data.value = post
    }

    override fun shareById(postId: Long) {
        post = post.map { post ->
            if (post.postId == postId) {
                post.copy(
                    shareCount = post.shareCount + 1,
                    shared = !post.shared
                )
            } else {
                post
            }
        }

        data.value = post
    }

    override fun viewingById(postId: Long) {
        post = post.map { post ->
            if (post.postId == postId) {
                post.copy(
                    viewingsCount = post.viewingsCount + 1
                )
            } else {
                post
            }
        }

        data.value = post
    }
}