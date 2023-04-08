package ru.netology.nmedia.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.service.CounterService

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener
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
                listener.onLike(post)
            }

            share.setOnClickListener {
                listener.onShare(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}