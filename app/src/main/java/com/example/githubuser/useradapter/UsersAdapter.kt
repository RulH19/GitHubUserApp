package com.example.githubuser.useradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemListBinding
import com.example.githubuser.response.User

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    private val list = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(user: ArrayList<User>) {
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemCliked(user)
            }
            binding.apply {
                imgItemPhoto.load(user.avatar_url) {
                    transformations(CircleCropTransformation())
                }
                textUsername.text = user.login
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
        holder.binding.textUsername.text = list[position].login
        Glide.with(holder.itemView.context)
            .load(list[position].avatar_url)
            .into(holder.binding.imgItemPhoto)
    }

    interface OnItemClickCallback {
        fun onItemCliked(data: User)
    }
}
