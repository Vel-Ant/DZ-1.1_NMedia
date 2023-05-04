package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.netology.nmedia.viewmodel.PostViewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.utils.TextArg

class NewPostFragment : Fragment() {

    val viewModel: PostViewModel by activityViewModels()

    companion object {
        var Bundle.textArg: String? by TextArg
    }

        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater)

        getSharedText(binding)

        getPostContent(binding)

        okSetOnClickListener(binding)

        return binding.root
    }

    fun okSetOnClickListener(binding: FragmentNewPostBinding) {
        binding.ok.setOnClickListener {
            if (!binding.edit.text.isNullOrBlank()) {
                val text = binding.edit.text.toString()
                viewModel.changeContent(text)
                viewModel.save()
            }
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
    }

    fun getPostContent(binding: FragmentNewPostBinding) {
        viewModel.edited.observe(viewLifecycleOwner) {
            with(binding.edit) {
                setText(it.content)
            }
        }
    }

    fun getSharedText(binding: FragmentNewPostBinding) {
        arguments?.let {
            val text = it.textArg
            binding.edit.setText(text)
        }
    }
}