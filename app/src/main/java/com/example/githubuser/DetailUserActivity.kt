package com.example.githubuser


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.useradapter.SectionPagerAdapter
import com.example.githubuser.viewmodel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_NAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val bundle = Bundle()
        bundle.putString(EXTRA_NAME, username)
        showLoading(true)
        viewModel = ViewModelProvider(
            this
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
        var _isChecked  = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if(count > 0){
                        binding.favorite.isChecked = true
                        _isChecked = true
                    }else {
                        binding.favorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }
        binding.favorite.setOnClickListener{
            _isChecked = !_isChecked
            if(_isChecked){
                viewModel.addToFavorite(username, id)
                Toast.makeText(this, "Berhasil Menambahkan User ke Favorite", Toast.LENGTH_LONG).show()
            }else{
                viewModel.removeFromFavorite(id)
                Toast.makeText(this, "Berhasil Meremove User dari Favorite", Toast.LENGTH_LONG).show()
            }
            binding.favorite.isChecked = _isChecked
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE

    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_ID = "extra_id"
    }
}