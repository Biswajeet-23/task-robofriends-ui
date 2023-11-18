package com.example.task_robofriends.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task_robofriends.R
import com.example.task_robofriends.databinding.ItemLayoutBinding
import com.example.task_robofriends.model.Users

class UserListAdapter(private val context: Context, private val list: MutableList<Users>): RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    var image = arrayOf<Int>(
        R.drawable.pexels,
        R.drawable.pexels3,
        R.drawable.pexels,
        R.drawable.pexels3,
        R.drawable.pexels,
        R.drawable.pexels3,
        R.drawable.pexels,
        R.drawable.pexels3,
        R.drawable.pexels,
        R.drawable.pexels3,
    )

    inner class UserListViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bindData(data: Users, position: Int) {
            binding.name.text = data.name
            binding.email.text = data.email
            binding.image.setImageResource(image[position])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return UserListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val data = list[position]
        holder.bindData(data, position)
    }
}