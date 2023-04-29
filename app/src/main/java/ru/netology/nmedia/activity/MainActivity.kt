package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    val viewModel: PostViewModel by viewModels()

    val newPostContract = registerForActivityResult(NewPostActivity.Contact) { result ->
        result ?: return@registerForActivityResult
        viewModel.changeContent(result)
        viewModel.save()
    }

    val adapter = PostAdapter(
        object : PostListener {
            override fun onLike(post: Post) = viewModel.likeById(post.postId)

            override fun onRemove(post: Post) = viewModel.removeById(post.postId)

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                newPostContract.launch(post.content)
            }

            override fun onPlayVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.urlVideo))
                startActivity(intent)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val startShareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(startShareIntent)
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startPostViewModel(binding)

        addOnClickListener(binding)
    }

    override fun onResume() {
        super.onResume()
        viewModel.cancelEdit()
    }

    fun startPostViewModel(binding: ActivityMainBinding) {
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.list.adapter = adapter
    }

    fun addOnClickListener(binding: ActivityMainBinding) {
        binding.add.setOnClickListener {
            newPostContract.launch(null)
        }
    }
}
