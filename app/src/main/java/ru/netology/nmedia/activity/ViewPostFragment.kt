package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FeedFragment.Companion.argPostId
import ru.netology.nmedia.databinding.FragmentViewPostBinding
import ru.netology.nmedia.service.CounterService
import ru.netology.nmedia.viewmodel.PostViewModel

class ViewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: PostViewModel by activityViewModels()
        val postId = arguments?.argPostId
        val binding = FragmentViewPostBinding.inflate(layoutInflater, container, false)

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            binding.viewPost.apply {
                posts.map { post ->
                    if (post.postId == postId) {
                        author.text = post.author
                        content.text = post.content
                        published.text = post.published
                        likes.text = CounterService.modifyQuantityDisplay(post.likesCount)
                        share.text = CounterService.modifyQuantityDisplay(post.shareCount)
                        viewingsCount.text =
                            CounterService.modifyQuantityDisplay(post.viewingsCount)

                        likes.isChecked = post.liked

                        share.isChecked = post.shared

                        if (!post.urlVideo.isNullOrBlank()) {
                            videoScreen.visibility = View.VISIBLE

                            playButton.setOnClickListener {
                                viewModel.viewingById(post.postId)
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.urlVideo))
                                startActivity(intent)
                            }

                            videoView.setOnClickListener {
                                viewModel.viewingById(post.postId)
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.urlVideo))
                                startActivity(intent)
                            }
                        } else {
                            videoScreen.visibility = View.GONE
                        }

                        likes.setOnClickListener {
                            viewModel.likeById(post.postId)
                        }

                        share.setOnClickListener {
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

                        menu.setOnClickListener {
                            PopupMenu(
                                ContextThemeWrapper(it.context, R.style.widgetPopupMenu),
                                it
                            ).apply {
                                inflate(R.menu.options_post)
                                setOnMenuItemClickListener { item ->
                                    when (item.itemId) {
                                        R.id.remove -> {
                                            viewModel.removeById(post.postId)
                                            findNavController().navigateUp()
                                            true
                                        }
                                        R.id.edit -> {
                                            viewModel.edit(post)
                                            findNavController().navigate(
                                                R.id.action_viewPostFragment_to_newPostFragment
                                            )
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
        }
        return binding.root
    }
}
