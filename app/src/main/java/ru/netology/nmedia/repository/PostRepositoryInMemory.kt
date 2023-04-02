package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {

    private var post = listOf(
        Post(
            postId = 9,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Освоение новой профессии — это не только открывающиеся возможности и перспективы, но и настоящий вызов самому себе. Приходится выходить из зоны комфорта и перестраивать привычный образ жизни: \nенять распорядок дня, искать время для занятий, быть готовым к возможным неудачам в начале пути. В блоге рассказали, как избежать стресса на курсах профпереподготовки \n→ http://netolo.gy/fPD",
            published = "23 сентября в 10:12",
            likesCount = 999,
            shareCount = 9_999,
            viewingsCount = 15_000
        ),
        Post(
            postId = 8,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
            published = "22 сентября в 10:14",
            likesCount = 4_999,
            shareCount = 5_999_999,
            viewingsCount = 7_088_000
        ),
        Post(
            postId = 7,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Таймбоксинг — отличный способ навести порядок в своём календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип — на каждое дело заранее выделяется определённый отрезок времени. В это время вы работаете только над одной задачей, не переключаясь на другие. Собрали советы, которые помогут внедрить таймбоксинг \uD83D\uDC47\uD83C\uDFFB",
            published = "22 сентября в 10:12",
            likesCount = 1_099,
            shareCount = 100_999,
            viewingsCount = 1_000_000
        ),
        Post(
            postId = 6,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "\uD83D\uDE80 24 сентября стартует новый поток бесплатного курса «Диджитал-старт: первый шаг к востребованной профессии» — за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам → http://netolo.gy/fQ",
            published = "21 сентября в 10:12",
            likesCount = 55_782,
            shareCount = 502_999,
            viewingsCount = 4_649_055
        ),
        Post(
            postId = 5,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Диджитал давно стал частью нашей жизни: \nмы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
            published = "20 сентября в 10:14",
            likesCount = 125,
            shareCount = 1_909,
            viewingsCount = 11_600
        ),
        Post(
            postId = 4,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Большая афиша мероприятий осени: \nконференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \uD83D\uDE09",
            published = "19 сентября в 14:12",
            likesCount = 425,
            shareCount = 4_589,
            viewingsCount = 10_500_000
        ),
        Post(
            postId = 3,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
            published = "19 сентября в 10:24",
            likesCount = 1_099,
            shareCount = 100_999,
            viewingsCount = 1_000_000
        ),
        Post(
            postId = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Знаний хватит на всех: \nна следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
            published = "18 сентября в 10:12",
            likesCount = 999,
            shareCount = 9_999,
            viewingsCount = 15_000
        ),
        Post(
            postId = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! \nКогда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен \n→ http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likesCount = 4_999,
            shareCount = 5_999_999,
            viewingsCount = 7_088_000
        ),
    ).reversed()

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