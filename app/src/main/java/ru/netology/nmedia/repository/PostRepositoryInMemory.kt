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
    //        Post(
//            postId = ++nextId,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Освоение новой профессии — это не только открывающиеся возможности и перспективы, но и настоящий вызов самому себе. Приходится выходить из зоны комфорта и перестраивать привычный образ жизни: \nенять распорядок дня, искать время для занятий, быть готовым к возможным неудачам в начале пути. В блоге рассказали, как избежать стресса на курсах профпереподготовки \n→ http://netolo.gy/fPD",
//            published = "23 сентября в 10:12",
//            likesCount = 999,
//            shareCount = 9_999,
//            viewingsCount = 15_000
//        ),
//        Post(
//            postId = ++nextId,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
//            published = "22 сентября в 10:14",
//            likesCount = 4_999,
//            shareCount = 5_999_999,
//            viewingsCount = 7_088_000
//        ),
//        Post(
//            postId = ++nextId,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Таймбоксинг — отличный способ навести порядок в своём календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип — на каждое дело заранее выделяется определённый отрезок времени. В это время вы работаете только над одной задачей, не переключаясь на другие. Собрали советы, которые помогут внедрить таймбоксинг \uD83D\uDC47\uD83C\uDFFB",
//            published = "22 сентября в 10:12",
//            likesCount = 1_099,
//            shareCount = 100_999,
//            viewingsCount = 1_000_000
//        ),
//        Post(
//            postId = ++nextId,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "\uD83D\uDE80 24 сентября стартует новый поток бесплатного курса «Диджитал-старт: первый шаг к востребованной профессии» — за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам → http://netolo.gy/fQ",
//            published = "21 сентября в 10:12",
//            likesCount = 55_782,
//            shareCount = 502_999,
//            viewingsCount = 4_649_055
//        ),
//        Post(
//            postId = ++nextId,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Диджитал давно стал частью нашей жизни: \nмы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
//            published = "20 сентября в 10:14",
//            likesCount = 125,
//            shareCount = 1_909,
//            viewingsCount = 11_600
//        ),
//        Post(
//            postId = ++nextId,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Большая афиша мероприятий осени: \nконференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \uD83D\uDE09",
//            published = "19 сентября в 14:12",
//            likesCount = 425,
//            shareCount = 4_589,
//            viewingsCount = 10_500_000
//        ),
//        Post(
//            postId = ++nextId,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
//            published = "19 сентября в 10:24",
//            likesCount = 1_099,
//            shareCount = 100_999,
//            viewingsCount = 1_000_000
//        ),
//        Post(
//            postId = ++nextId,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Знаний хватит на всех: \nна следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
//            published = "18 сентября в 10:12",
//            likesCount = 999,
//            shareCount = 9_999,
//            viewingsCount = 15_000
//        ),
//        Post(
//            postId = ++nextId,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Привет, это новая Нетология! \nКогда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен \n→ http://netolo.gy/fyb",
//            published = "21 мая в 18:36",
//            likesCount = 4_999,
//            shareCount = 5_999_999,
//            viewingsCount = 7_088_000
//        ),
//        Post(
//            postId = ++nextId,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Ссылка на видео в Youtube",
//            published = "23 сентября в 10:12",
//            likesCount = 0,
//            shareCount = 0,
//            viewingsCount = 0,
//            urlVideo = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
//        ),
//    ).reversed()
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
            return
        }

        posts = posts.map {
            if (it.postId == post.postId) it.copy(content = post.content) else it
        }
        data.value = posts
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
    }

    override fun viewingById(postId: Long) {
        posts = posts.map {
            if (it.postId == postId) it.copy(viewingsCount = it.viewingsCount + 1) else it
        }
        data.value = posts
    }
}