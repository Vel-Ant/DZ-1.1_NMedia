package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val postId: Long,
    val author: String,
    val content: String,
    val published: String,
    val liked: Boolean = false,
    val shared: Boolean = false,
    val likesCount: Int = 0,
    val shareCount: Int = 0,
    val viewingsCount: Int = 0,
    val urlVideo: String? = null
) {
    fun toDto() = Post(
        postId,
        author,
        content,
        published,
        liked,
        shared,
        likesCount,
        shareCount,
        viewingsCount,
        urlVideo
    )

    companion object {
        fun fromDto(post: Post) = with(post) {
            PostEntity(
                postId,
                author,
                content,
                published,
                liked,
                shared,
                likesCount,
                shareCount,
                viewingsCount,
                urlVideo
            )
        }
    }
}