package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    val viewModel: PostViewModel by viewModels()

    val adapter = PostAdapter(
        object : PostListener {
            override fun onLike(post: Post) = viewModel.likeById(post.postId)

            override fun onShare(post: Post) = viewModel.shareById(post.postId)

            override fun onRemove(post: Post) = viewModel.removeById(post.postId)

            override fun onEdit(post: Post) = viewModel.edit(post)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        startPostViewModel(activityMainBinding)

        saveOnClickListener(activityMainBinding)

        editViewModel(activityMainBinding)

        cancelEditOnClickListener(activityMainBinding)
    }

    fun startPostViewModel(activityMainBinding: ActivityMainBinding) {
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        activityMainBinding.list.adapter = adapter
    }

    fun editViewModel(activityMainBinding: ActivityMainBinding) {
        viewModel.edited.observe(this) {
            if (it.postId == 0L) {
                return@observe
            }

            activityMainBinding.cancelGroup.visibility = View.VISIBLE
            activityMainBinding.content.requestFocus()
            activityMainBinding.content.setText(it.content)
        }
    }

    fun saveOnClickListener(activityMainBinding: ActivityMainBinding) {
        activityMainBinding.save.setOnClickListener {
            with(activityMainBinding.content) {
                val content = text?.toString()
                if (content.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.empty_post_error,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(content)
                viewModel.save()
                activityMainBinding.cancelGroup.visibility = View.GONE

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }

    fun cancelEditOnClickListener(activityMainBinding: ActivityMainBinding) {
        activityMainBinding.clearEdit.setOnClickListener {
            with(activityMainBinding.content) {
                val content = text.toString()
                viewModel.cancelEdit(content)
            }
        }
    }
}