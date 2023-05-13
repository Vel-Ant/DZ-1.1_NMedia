package ru.netology.nmedia.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dto.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_POST_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKED} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARED} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKES_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARE_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIEWINGS_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_URL_VIDEO} TEXT DEFAULT NULL
        );
        """.trimIndent()
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_POST_ID = "postId"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_LIKED = "liked"
        const val COLUMN_SHARED = "shared"
        const val COLUMN_LIKES_COUNT = "likesCount"
        const val COLUMN_SHARE_COUNT = "shareCount"
        const val COLUMN_VIEWINGS_COUNT = "viewingsCount"
        const val COLUMN_URL_VIDEO = "urlVideo"
        val ALL_COLUMNS = arrayOf(
            COLUMN_POST_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_LIKED,
            COLUMN_SHARED,
            COLUMN_LIKES_COUNT,
            COLUMN_SHARE_COUNT,
            COLUMN_VIEWINGS_COUNT,
            COLUMN_URL_VIDEO
        )
    }

    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_POST_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            // TODO: remove hardcoded values
            put(PostColumns.COLUMN_AUTHOR, "Me")
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, post.published)
        }
        val id = if (post.postId != 0L) {
            db.update(
                PostColumns.TABLE,
                values,
                "${PostColumns.COLUMN_POST_ID} = ?",
                arrayOf(post.postId.toString()),
            )
            post.postId
        } else {
            db.insert(PostColumns.TABLE, null, values)
        }
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_POST_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun likeById(postId: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               likesCount = likesCount + CASE WHEN liked THEN -1 ELSE 1 END,
               liked = CASE WHEN liked THEN 0 ELSE 1 END
           WHERE postId = ?;
        """.trimIndent(), arrayOf(postId)
        )
    }

    override fun shareById(postId: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               shareCount = shareCount + 1,
               shared = CASE WHEN shared THEN 1 ELSE 1 END
           WHERE postId = ?;
        """.trimIndent(), arrayOf(postId)
        )
    }

    override fun viewingById(postId: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               viewingsCount = viewingsCount + 1
           WHERE postId = ?;
        """.trimIndent(), arrayOf(postId)
        )
    }

    override fun removeById(postId: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_POST_ID} = ?",
            arrayOf(postId.toString())
        )
    }

    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                postId = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_POST_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                liked = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED)) != 0,
                shared = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARED)) != 0,
                likesCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES_COUNT)),
                shareCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARE_COUNT)),
                viewingsCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_VIEWINGS_COUNT)),
                urlVideo = getString(getColumnIndexOrThrow(PostColumns.COLUMN_URL_VIDEO)),
            )
        }
    }
}