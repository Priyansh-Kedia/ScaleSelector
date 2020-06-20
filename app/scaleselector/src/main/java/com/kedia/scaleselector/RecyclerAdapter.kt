package com.kedia.scaleselector

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val list: MutableList<Int>, private val listener: OnClick, private val context: Context) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.biometrics_selector_card, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(list[position])
            if (holder.itemView.isSelected)
                selectedPosition = position
        }
    }

    fun setData(list: List<Int>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val selectorLayout: ConstraintLayout = itemView.findViewById(R.id.selectorLayout)
        private val heightText: TextView = itemView.findViewById(R.id.heightText)
        private val heightLine: View = itemView.findViewById(R.id.heightLine)

        fun bind(int: Int) {

            if (int % 5 == 0) {
                heightText.text = int.toString()
                heightText.visibility = View.VISIBLE
            } else {
                heightText.text = int.toString()
                heightText.visibility = View.INVISIBLE
            }

            if (selectedPosition == adapterPosition) {
                heightText.apply {
                    scaleY = 1.2f
                }
                heightLine.scaleY = 2.5f
                itemView.isSelected = true
            } else {
                heightText.apply {
                    scaleY = 1f
                }
                itemView.isSelected = false
                heightLine.scaleY = 1f
                heightLine.setBackgroundColor(Color.parseColor("#ffffff"))
                if (Integer.valueOf(heightText.text.toString().trim()) % 5 != 0)
                    heightText.visibility = View.INVISIBLE
            }

            itemView.setOnClickListener {
                it.isSelected = true
                heightText.scaleY = 1.2f
                heightLine.apply {
                    scaleY = 2.5f
                    setBackgroundColor(Color.parseColor("#ffffff"))
                }
                heightText.visibility = View.VISIBLE
                if (selectedPosition != -1)
                    notifyItemChanged(selectedPosition)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                listener.onHeightClicked(heightText.toString().trim(), adapterPosition)
            }
        }

    }

    interface OnClick {
        fun onHeightClicked(height: String, adapterPosition: Int)
    }

}