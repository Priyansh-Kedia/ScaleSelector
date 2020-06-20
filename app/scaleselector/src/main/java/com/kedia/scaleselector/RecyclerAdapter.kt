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

    private var pointerColor: Int = Color.WHITE
    private var selectedPosition = -1
    private var stepValue: Int = 5
    private var backgroundCardColor = Color.BLACK
    private var defaultPointerColor = Color.WHITE
    private var defaultTextColor = Color.WHITE
    private var selectedTextColor = Color.WHITE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.biometrics_selector_card, parent, false))
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

    fun setPointerColor(color: Int) {
        this.pointerColor = color
    }

    fun setStepValue(stepValue: Int) {
        this.stepValue = stepValue
    }

    fun setBackGroundCardColor(color: Int) {
        this.backgroundCardColor = color
    }

    fun setDefaultPointerColor(color: Int) {
        this.defaultPointerColor = color
    }

    fun setDefaultTextColor(color: Int) {
        this.defaultTextColor = color
    }

    fun setSelectedTextColor(color: Int) {
        this.selectedTextColor = color
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val selectorLayout: ConstraintLayout = itemView.findViewById(R.id.selectorLayout)
        private val heightText: TextView = itemView.findViewById(R.id.heightText)
        private val heightLine: View = itemView.findViewById(R.id.heightLine)

        fun bind(int: Int) {

            selectorLayout.setBackgroundColor(backgroundCardColor)

            if (int % stepValue == 0) {
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
                itemView.elevation = 2f
            } else {
                heightText.apply {
                    scaleY = 1f
                    setTextColor(defaultTextColor)
                }
                itemView.elevation = 1f
                heightLine.scaleY = 1f
                heightLine.setBackgroundColor(defaultPointerColor)
                if (Integer.valueOf(heightText.text.toString().trim()) % stepValue != 0)
                    heightText.visibility = View.INVISIBLE
            }

            itemView.setOnClickListener {
                it.elevation = 2f
                heightText.scaleY = 1.2f
                heightText.setTextColor(selectedTextColor)
                heightLine.apply {
                    scaleY = 2.5f
                    setBackgroundColor(pointerColor)
                }
                heightText.visibility = View.VISIBLE
                if (selectedPosition != -1)
                    notifyItemChanged(selectedPosition)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                listener.onHeightClicked(heightText.text.toString().trim(), adapterPosition)
            }
        }

    }

    interface OnClick {
        fun onHeightClicked(height: String, adapterPosition: Int)
    }

}