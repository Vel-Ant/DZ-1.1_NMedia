package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.LongArg
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    val viewModel: PostViewModel by activityViewModels()

    companion object {
        var Bundle.longArg: Long by LongArg
    }

    val adapter = PostAdapter(
        object : PostListener {
            override fun onLike(post: Post) = viewModel.likeById(post.postId)

            override fun onRemove(post: Post) = viewModel.removeById(post.postId)

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = post.content })
            }

            override fun onPlayVideo(post: Post) {
                viewModel.viewingById(post.postId)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.urlVideo))
                startActivity(intent)
            }

            override fun onViewPost(post: Post) {
                viewModel.viewingById(post.postId)
                findNavController().navigate(
                    R.id.action_feedFragment2_to_viewPostFragment,
                    Bundle().apply { longArg = post.postId })
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.postId)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentFeedBinding.inflate(layoutInflater, container, false)

        startPostViewModel(binding)

        addOnClickListener(binding)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.cancelEdit()
    }

    fun startPostViewModel(binding: FragmentFeedBinding) {
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
    }

    fun addOnClickListener(binding: FragmentFeedBinding) {
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
    }
}
