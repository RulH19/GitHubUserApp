package com.example.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.local.FavoriteUser
import com.example.githubuser.useradapter.UsersAdapter
import com.example.githubuser.viewmodel.FavoriteViewModel
import com.example.githubuser.response.User

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemCliked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_NAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = map(it)
                adapter.setList(list)

            }
        }
    }
    private fun map(users:List<FavoriteUser>): ArrayList<User>{
        val list = ArrayList<User>()
        for(user in users){
            val map = User(
                user.id,
               user.login,
                user.avatar_url
            )
            list.add(map)
        }
        return list
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}