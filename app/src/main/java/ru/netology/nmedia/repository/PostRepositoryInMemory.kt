package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory: PostRepository {

    private var post = Post(
        0,
        "Нетология. Университет интернет-профессий будущего",
        "Привет, это новая Нетология! \nКогда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила,которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен\n → http://netolo.gy/fyb"
    )

    private val data = MutableLiveData(post)


    override fun getData(): LiveData<Post> = data

    override fun like() {
        post = post.copy(
            likes_count = if (post.iLiked) post.likes_count - 1 else post.likes_count + 1,
            iLiked = !post.iLiked
        )
        data.value = post
    }

    override fun share() {
        post = post.copy(
            share_count = post.share_count + 1,
            iShared = !post.iShared
        )
        data.value = post
    }

    override fun viewing() {
        post = post.copy(
            viewings_count = post.viewings_count + 1
        )
        data.value = post
    }
}