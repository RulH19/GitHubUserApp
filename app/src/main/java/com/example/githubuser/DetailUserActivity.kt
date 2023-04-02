package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.useradapter.SectionPagerAdapter
import com.example.githubuser.viewmodel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_NAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_NAME, username)
        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailUserViewModel::class.java]

        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail()
            .observe(this) {
                if (it != null) {
                    binding.apply {
                        textUsername.text = it.login
                        textName.text = it.name
                        followers.text = "${it.followers}Followers"
                        following.text = "${it.following}Following"
                        Glide.with(this@DetailUserActivity)
                            .load(it.avatarUrl)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop()
                            .into(imageView)
                    }
                    showLoading(false)
                }
            }
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE

    }

    companion object {
        const val EXTRA_NAME = "extra_name"
    }
}