package com.example.githubuser.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.DetailUserActivity
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.useradapter.UsersAdapter
import com.example.githubuser.viewmodel.FollowersViewModel

class FollowersFragment : Fragment(R.layout.fragment_follow) {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UsersAdapter
    private lateinit var username: String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_NAME).toString()
        _binding = FragmentFollowBinding.bind(view)
        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()
        binding?.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE
    }
}