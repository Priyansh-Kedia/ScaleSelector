package com.kedia.scaleselector

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CircularRecyclerAdapter(private val list: MutableList<Int>, private val listener: CircularRecyclerAdapter.OnClick, private val context: Context) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var pointerColor: Int = Color.WHITE
    private var selectedPosition = -1
    private var stepValue: Int = 5
    private var backgroundCardColor = Color.BLACK
    private var defaultPointerColor = Color.WHITE
    private var defaultTextColor = Color.WHITE
    private var selectedCardColor = Color.CYAN
    private var selectedTextColor = Color.WHITE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.age_selector_card, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(list[position])
            if (holder.itemView.elevation == 2f)
                selectedPosition = position
        }
    }

    fun setData(list: List<Int>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setStepValue(stepValue: Int) {
        this.stepValue = stepValue
    }

    fun setBackGroundCardColor(color: Int) {
        this.backgroundCardColor = color
    }

    fun setDefaultTextColor(color: Int) {
        this.defaultTextColor = color
    }

    fun setSelectedTextColor(color: Int) {
        this.selectedTextColor = color
    }

    fun setSelectedCardColor(color: Int) {
        this.selectedCardColor = color
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ageCard: CardView = itemView.findViewById(R.id.ageCard)
        private val ageText: TextView = itemView.findViewById(R.id.ageText)

        fun bind(age: Int) {

            ageCard.setCardBackgroundColor(backgroundCardColor)

            if (selectedPosition == adapterPosition) {
                ageCard.apply {
                    scaleX = 1.1f
                    scaleY = 1.1f
                }
                ageText.text = age.toString()
                ageText.setTextColor(selectedTextColor)
                ageCard.setCardBackgroundColor(selectedCardColor)
                itemView.elevation = 2f
            } else {
                ageCard.apply {
                    scaleX = 1f
                    scaleY = 1f
                }
                ageText.text = age.toString()
                ageText.setTextColor(defaultTextColor)
                ageCard.setCardBackgroundColor(backgroundCardColor)
                itemView.elevation = 1f
            }

            itemView.setOnClickListener {
                it.elevation = 2f
                ageCard.scaleX = 1.1f
                ageCard.scaleY = 1.1f
                ageText.setTextColor(selectedTextColor)
                ageCard.setCardBackgroundColor(selectedCardColor)
                if (selectedPosition != -1)
                    notifyItemChanged(selectedPosition)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                listener.onItemClick(adapterPosition, ageText.text.toString().trim())
            }

            ageCard.setOnClickListener {
                itemView.callOnClick()
            }
        }

    }

    interface OnClick {
        fun onItemClick(adapterPosition: Int, ageNumber: String)
    }
}