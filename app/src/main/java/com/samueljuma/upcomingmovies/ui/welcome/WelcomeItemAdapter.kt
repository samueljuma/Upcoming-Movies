package com.samueljuma.upcomingmovies.ui.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samueljuma.upcomingmovies.data.WelcomeItem
import com.samueljuma.upcomingmovies.databinding.WelcomeItemLayoutBinding

class WelcomeItemAdapter: ListAdapter<WelcomeItem, WelcomeItemAdapter.ViewHolder>(WelcomeItemDiffCallBack()) {

    class ViewHolder (private val binding:WelcomeItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(welcomeItem: WelcomeItem){
            binding.welcomeItem = welcomeItem
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WelcomeItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WelcomeItemDiffCallBack: ItemCallback<WelcomeItem>() {
    override fun areItemsTheSame(oldItem: WelcomeItem, newItem: WelcomeItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WelcomeItem, newItem: WelcomeItem): Boolean {
        return oldItem == newItem
    }

}
