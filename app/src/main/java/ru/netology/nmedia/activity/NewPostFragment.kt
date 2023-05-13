package ru.netology.nmedia.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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

        okSetOnClickListener(binding)

        arguments?.textArg
            ?.let(binding.edit::setText)

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callbackPressed(binding)
                }
            }
        )

            return binding.root
    }

    fun okSetOnClickListener(binding: FragmentNewPostBinding) {
        val prefs = getActivity()?.getPreferences(Context.MODE_PRIVATE)
        prefs?.edit()?.apply {      // об_NULL_яет prefs
            putString("cash", null)
            apply()
        }

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

    fun callbackPressed(binding: FragmentNewPostBinding) {
        val prefs = getActivity()?.getPreferences(Context.MODE_PRIVATE)
        if (!binding.edit.text.isNullOrBlank()) {
            val textPrefs = binding.edit.text.toString()
            viewModel.edited.observe(viewLifecycleOwner) {
                if (it.postId == 0L) {
                    prefs?.edit()?.apply {  // записывает текст в prefs
                        putString("cash", textPrefs)
                        apply()
                    }
                } else {
                    prefs?.edit()?.apply {  // об_NULL_яет prefs
                        putString("cash", null)
                        apply()
                    }
                }
            }
        }
        AndroidUtils.hideKeyboard(requireView())
        findNavController().navigateUp()
    }
}