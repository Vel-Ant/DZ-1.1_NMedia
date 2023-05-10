package ru.netology.nmedia.dao

import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(postId: Long)
    fun shareById(postId: Long)
    fun viewingById(postId: Long)
    fun removeById(postId: Long)
    fun save(post: Post): Post
}