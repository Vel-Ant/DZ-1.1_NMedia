package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.service.CounterService
import ru.netology.nmedia.viewmodel.PostViewModel
import java.security.Provider.Service

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
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
            }
        }

        binding.root.setOnClickListener {
            Log.d("stuff", "stuff")
        }

        binding.avatar.setOnClickListener {
            Log.d("stuff", "avatar")
        }

        binding.likes.setOnClickListener {
            Log.d("stuff", "like")
            viewModel.like()
        }

        binding.share.setOnClickListener {
            Log.d("stuff", "share")
            viewModel.share()
        }
    }
}