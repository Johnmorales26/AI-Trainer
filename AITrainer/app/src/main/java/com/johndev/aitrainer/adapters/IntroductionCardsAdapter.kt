package com.johndev.aitrainer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johndev.aitrainer.Interfaces.OnIntroductionListener
import com.johndev.aitrainer.Models.Introduction
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.ItemIntroductionCardsBinding

class IntroductionCardsAdapter(private val introductionList: MutableList<Introduction>, private val listener: OnIntroductionListener):
    RecyclerView.Adapter<IntroductionCardsAdapter.ViewHolder>(){

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroductionCardsAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_introduction_cards, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: IntroductionCardsAdapter.ViewHolder, position: Int) {
        val introduction = introductionList[position]
        var isVisible = 0
        with(holder){
            binding.apply {
                tvTitle.text = introduction.title.toString().trim()
                tvExplain.text = introduction.description.toString().trim()
                imgDescription.setImageURI(introduction.img)
                binding.btnClose.setOnClickListener {
                    if ((isVisible % 2) == 0){
                        btnClose.setImageResource(R.drawable.ic_arrow_drop_down)
                        imgDescription.visibility = GONE
                        tvExplain.visibility = GONE
                        isVisible++
                    } else {
                        btnClose.setImageResource(R.drawable.ic_arrow_drop_up)
                        imgDescription.visibility = VISIBLE
                        tvExplain.visibility = VISIBLE
                        isVisible++
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = introductionList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemIntroductionCardsBinding.bind(view)
    }

}