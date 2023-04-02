package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.service.CounterService
import ru.netology.nmedia.viewmodel.PostViewModel
import java.security.Provider.Service

class MainActivity : AppCompatActivity() {

    val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(activityMainBinding.root)

        startPostViewModel(activityMainBinding)

    }

    fun startPostViewModel(activityMainBinding: ActivityMainBinding) {
        viewModel.data.observe(this) { posts ->
            activityMainBinding.container.removeAllViews()
            val views = posts.map { post ->
                val cardPostBinding =
                    CardPostBinding.inflate(layoutInflater, activityMainBinding.root, false)
                with(cardPostBinding) {
                    author.text = post.author
                    content.text = post.content
                    published.text = post.published
                    likesCount.text = CounterService.modifyQuantityDisplay(post.likesCount)
                    shareCount.text = CounterService.modifyQuantityDisplay(post.shareCount)
                    viewingsCount.text = CounterService.modifyQuantityDisplay(post.viewingsCount)

                    if (post.liked) {
                        likes.setImageResource(R.drawable.iliked_24)
                    } else {
                        likes.setImageResource(R.drawable.like_24)
                    }

                    if (post.shared) {
                        share.setImageResource(R.drawable.ishared_24)
                    }

                    startSetOnClickListener(cardPostBinding)
                }

                cardPostBinding
            }

            views.forEach {
                activityMainBinding.container.addView(it.root)
            }
        }
    }

    fun startSetOnClickListener(cardPostBinding: CardPostBinding) {
        viewModel.data.observe(this) { posts ->
            posts.map { post ->

                cardPostBinding.likes.setOnClickListener {
                    viewModel.likeById(post.postId)
                }

                cardPostBinding.share.setOnClickListener {
                    viewModel.shareById(post.postId)
                }

//                cardPostBinding.menu.setOnClickListener {
//                    viewModel.menu(post.postId)
//                }
            }
        }
    }
}