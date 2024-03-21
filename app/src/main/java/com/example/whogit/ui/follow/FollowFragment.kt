package com.example.whogit.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whogit.data.model.UserModel
import com.example.whogit.databinding.FragmentFollowBinding
import com.example.whogit.ui.detail.DetailViewModel
import com.example.whogit.ui.main.UserAdapter
import com.example.whogit.ui.utils.Result

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { UserAdapter { } }
    private val viewModel by activityViewModels<DetailViewModel>()
    private var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollows.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }
        when (type) {
            FOLLOWERS -> {
                viewModel.resultFollowers.observe(viewLifecycleOwner, this::manageResultFollows)
            }
            FOLLOWING -> {
                viewModel.resultFollowing.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
    }

    private fun manageResultFollows(state: Result) {
        when (state) {
            is Result.Success<*> -> {
                adapter.setData(state.data as MutableList<UserModel.ItemsItem>)
            }
            is Result.Error -> {
                Toast.makeText(requireContext(), state.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Result.Loading -> {
                binding.progressBar.isVisible = state.isLoading
            }
        }
    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101

        fun newInstance(type: Int) = FollowFragment().apply {
            this.type = type
        }
    }
}
