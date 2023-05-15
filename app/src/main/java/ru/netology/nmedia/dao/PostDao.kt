package ru.netology.nmedia.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY postId DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Query(
        """
           UPDATE PostEntity SET
               likesCount = likesCount + CASE WHEN liked THEN -1 ELSE 1 END,
               liked = CASE WHEN liked THEN 0 ELSE 1 END
           WHERE postId = :postId;
        """
    )
    fun likeById(postId: Long)

    @Query(
        """
           UPDATE PostEntity SET
               shareCount = shareCount + 1,
               shared = CASE WHEN shared THEN 1 ELSE 1 END
           WHERE postId = :postId;
        """
    )
    fun shareById(postId: Long)

    @Query(
        """
           UPDATE PostEntity SET
               viewingsCount = viewingsCount + 1
           WHERE postId = :postId;
        """
    )
    fun viewingById(postId: Long)

    @Query("DELETE FROM PostEntity WHERE postId = :postId")
    fun removeById(postId: Long)

    @Insert
    fun insert(postId: PostEntity)

    @Query("UPDATE PostEntity SET content = :text WHERE postId = :postId")
    fun updateContentById(postId: Long, text: String)
    fun save(post: PostEntity) =
        if (post.postId == 0L) insert(post) else updateContentById(
            post.postId,
            post.content
        )
}