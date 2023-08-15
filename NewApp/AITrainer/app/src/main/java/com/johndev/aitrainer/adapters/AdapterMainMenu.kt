package com.johndev.aitrainer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.johndev.aitrainer.R
import com.johndev.aitrainer.common.entities.MainMenuEntity
import com.johndev.aitrainer.databinding.ItemMenuMainBinding
import com.johndev.aitrainer.interfaces.OnMainMenu
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AdapterMainMenu @Inject constructor(
    @ApplicationContext private val context: Context,
    private val listener: OnMainMenu
) :
    ListAdapter<MainMenuEntity, RecyclerView.ViewHolder>(MainMenuDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_menu_main, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mainMenu = getItem(position)
        with((holder as ViewHolder).binding) {
            holder.setListener(mainMenu)
            titleItem.text = context.getString(mainMenu.title).trim()
            descriptionItem.text = context.getString(mainMenu.description).trim()
            imgItem.load(mainMenu.image) {
                crossfade(true)
                placeholder(R.drawable.ic_load)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMenuMainBinding.bind(view)

        fun setListener(mainMenu: MainMenuEntity){
            binding.btnItem.setOnClickListener {
                listener.onClick(mainMenu)
            }
        }
    }

    private class MainMenuDiff : DiffUtil.ItemCallback<MainMenuEntity>() {

        override fun areItemsTheSame(oldItem: MainMenuEntity, newItem: MainMenuEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MainMenuEntity, newItem: MainMenuEntity): Boolean =
            oldItem == newItem
    }

}