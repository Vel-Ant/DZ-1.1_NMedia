package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.CounterService
import ru.netology.nmedia.viewmodel.PostViewModel

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
                likesCount.text = CounterService.ModifyQuantityDisplay(post.likes_count)
                shareCount.text = CounterService.ModifyQuantityDisplay(post.share_count)
                viewingsCount.text = CounterService.ModifyQuantityDisplay(post.viewings_count)

                if (post.iLiked) {
                    likes.setImageResource(R.drawable.iliked_24)
                } else {
                    likes.setImageResource(R.drawable.like_24)
                }

                if (post.iShared) {
                    share.setImageResource(R.drawable.ishared_24)
                }
            }
        }

        binding.likes.setOnClickListener {
            viewModel.like()
        }

        binding.share.setOnClickListener {
            viewModel.share()
        }
    }
}