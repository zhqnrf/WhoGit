package com.example.whogit.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.whogit.data.model.UserModel
import com.example.whogit.databinding.ItemLayoutBinding

class UserAdapter(
    private val listener: (UserModel.ItemsItem) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var data: MutableList<UserModel.ItemsItem> = mutableListOf()

    fun setData(newData: List<UserModel.ItemsItem>) {
        val diffCallback = UserDiffCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    class UserViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserModel.ItemsItem) {
            binding.imgProfile.load(item.avatar_url) {
                transformations(CircleCropTransformation())
            }
            binding.tvUsername.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    class UserDiffCallback(
        private val oldList: List<UserModel.ItemsItem>,
        private val newList: List<UserModel.ItemsItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
