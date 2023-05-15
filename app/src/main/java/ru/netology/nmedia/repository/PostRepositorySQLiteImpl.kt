package ru.netology.nmedia.repository

import androidx.lifecycle.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositorySQLiteImpl(
    private val dao: PostDao,
) : PostRepository {

    override fun getAll() = dao.getAll().map { list ->
        list.map { it.toDto() }
    }

    override fun removeById(postId: Long) = dao.removeById(postId)
    override fun save(post: Post) = dao.save(PostEntity.fromDto(post))
    override fun likeById(postId: Long) = dao.likeById(postId)
    override fun shareById(postId: Long) = dao.shareById(postId)
    override fun viewingById(postId: Long) = dao.viewingById(postId)
}