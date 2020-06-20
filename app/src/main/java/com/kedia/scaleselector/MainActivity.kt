package com.kedia.scaleselector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CircularScaleSelector.onClick, ScaleSelector.onClick {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TAG!!!",demoId.selectedValue.toString() + "asdfasd " + testRecycler.selectedValue.toString())
        demoId.setListener(this)
        testRecycler.setListener(this)
    }

    override fun onClick(selectedValue: Int) {
        Log.d("TAG!!!",selectedValue.toString())
    }

    override fun onScaleClicked(selectedValue: Int) {
        Log.d("TAG!!!",selectedValue.toString())
    }
}