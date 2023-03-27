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
            likesCount = if (post.liked) post.likesCount - 1 else post.likesCount + 1,
            liked = !post.liked
        )
        data.value = post
    }

    override fun share() {
        post = post.copy(
            shareCount = post.shareCount + 1,
            shared = !post.shared
        )
        data.value = post
    }

    override fun viewing() {
        post = post.copy(
            viewingsCount = post.viewingsCount + 1
        )
        data.value = post
    }
}