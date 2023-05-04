package ru.netology.nmedia.adapter

import android.media.MediaMetadataRetriever
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.view.ContextThemeWrapper
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
            likes.text = CounterService.modifyQuantityDisplay(post.likesCount)
            share.text = CounterService.modifyQuantityDisplay(post.shareCount)
            viewingsCount.text = CounterService.modifyQuantityDisplay(post.viewingsCount)

            likes.isChecked = post.liked

            share.isChecked = post.shared

            if (!post.urlVideo.isNullOrBlank()) {
                videoScreen.visibility = View.VISIBLE

//TODO: разобраться с исключением
// Код позволяющий автоматом вставлять картинку из видео в videoImage
//                val retriever = MediaMetadataRetriever()
//                retriever.setDataSource(post.urlVideo, HashMap())
//                val time = 2000L // время в миллисекундах
//                val bitmap = retriever.getFrameAtTime(time)
//                if (bitmap != null) {
//                    videoImage.visibility = View.VISIBLE
//                    videoImage.setImageBitmap(bitmap)
//                } else videoImage.visibility = View.GONE

                playButton.setOnClickListener {
                    listener.onPlayVideo(post)
                }

                videoView.setOnClickListener {
                    listener.onPlayVideo(post)
                }
            } else {
                videoScreen.visibility = View.GONE
            }

            likes.setOnClickListener {
                listener.onLike(post)
            }

            share.setOnClickListener {
                listener.onShare(post)
            }

            content.setOnClickListener {
                listener.onViewPost(post)
            }

            menu.setOnClickListener {
                PopupMenu(ContextThemeWrapper(it.context, R.style.widgetPopupMenu), it).apply {
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