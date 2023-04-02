package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.service.CounterService

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeClicked: OnLikeClickListener,
    private val onShareClicked: OnShareClickListener,
//    private val onMenuClicked: (Post) -> Unit
) : ViewHolder(binding.root) {

    fun bind(post: Post) {
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

            likes.setOnClickListener {
                onLikeClicked(post)
            }

            share.setOnClickListener {
                onShareClicked(post)
            }

//            menu.setOnClickListener {
//                onMenuClicked(post)
//            }
        }
    }
}