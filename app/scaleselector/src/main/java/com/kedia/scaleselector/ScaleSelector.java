package com.kedia.scaleselector;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScaleSelector extends FrameLayout implements RecyclerAdapter.OnClick{

    private int stepValue;
    private int orientation;
    private int pointerPosition;
    private int pointerColor;
    private int mainLayoutId;

    private RecyclerView mRecycler;

    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

    public ScaleSelector(Context context) {
        super(context);
    }

    public ScaleSelector(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
        initLayout(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.ScaleSelector);
        try {
            mainLayoutId = R.layout.main_recycler_view_layout;
            stepValue = typedArray.getInt(R.styleable.ScaleSelector_stepValue, 5);
            orientation = typedArray.getInt(R.styleable.ScaleSelector_orientation, 1);
            pointerPosition = typedArray.getInt(R.styleable.ScaleSelector_pointerPosition, 0);
            pointerColor = typedArray.getColor(R.styleable.ScaleSelector_pointerColor,getContext().getResources().getColor(R.color.blue));

        } finally {
            typedArray.recycle();
        }
    }

    private void initLayout(AttributeSet attributeSet) {
        if (isInEditMode())
            return;

        View view = LayoutInflater.from(getContext()).inflate(mainLayoutId, this);

        View recyclerView = view.findViewById(R.id.recycler);
        if (recyclerView instanceof RecyclerView) {
            mRecycler = (RecyclerView) recyclerView;
        } else {
            throw new IllegalArgumentException("Only works with recyclerView");
        }

       List<Integer> list = new ArrayList();
        for (int i=0; i<200; i++)
            list.add(i);

        mRecycler.setAdapter(new RecyclerAdapter(list,this,getContext()));
        mRecycler.setLayoutManager(linearLayoutManager);
    }


    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        if (manager instanceof LinearLayoutManager) {
            mRecycler.setLayoutManager(manager);
        } else {
            throw new IllegalStateException("Only linear layout manager is allowed");
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        setAdapterInternal(adapter, false, true);
    }

    private void setAdapterInternal(RecyclerView.Adapter adapter, boolean compatibleWithPrevious,
                                    boolean removeAndRecycleExistingViews) {
        if (compatibleWithPrevious)
            mRecycler.swapAdapter(adapter, removeAndRecycleExistingViews);
        else
            mRecycler.setAdapter(adapter);
    }

    @Override
    public void onHeightClicked(@NotNull String height, int adapterPosition) {
//        val center = heightRecycler.width /2 - heightRecycler.findViewHolderForAdapterPosition(adapterPosition)?.itemView?.width!! / 2
//        heightLayoutManager.scrollToPositionWithOffset(adapterPosition, center)
        int center = mRecycler.getWidth() / 2 - mRecycler.findViewHolderForAdapterPosition(adapterPosition).itemView.getWidth() / 2;
        linearLayoutManager.scrollToPositionWithOffset(adapterPosition, center);
    }
}
