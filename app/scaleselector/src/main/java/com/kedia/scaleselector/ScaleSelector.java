package com.kedia.scaleselector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScaleSelector extends FrameLayout implements RecyclerAdapter.OnClick{

    private int stepValue;
    private int defaultTextColor;
    private int selectedTextColor;
    private int pointerColor;
    private int mainLayoutId;
    private int minValue;
    private int backGroundColor;
    private int defaultPointerColor;
    private int maxValue;
    private Boolean showArrowPointer;

    private RecyclerView mRecycler;
    private RecyclerAdapter adapter;

    private LinearLayoutManager linearLayoutManager ;

    public int selectedValue;

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
            backGroundColor = typedArray.getColor(R.styleable.ScaleSelector_backgroundColor, Color.parseColor("#000000"));
            pointerColor = typedArray.getColor(R.styleable.ScaleSelector_selectedPointerColor,getContext().getResources().getColor(R.color.blue));
            minValue = typedArray.getInt(R.styleable.ScaleSelector_minValue, 0);
            maxValue = typedArray.getInt(R.styleable.ScaleSelector_maxValue, 200);
            defaultPointerColor = typedArray.getColor(R.styleable.ScaleSelector_defaultPointerColor, Color.parseColor("#ffffff"));
            defaultTextColor = typedArray.getColor(R.styleable.ScaleSelector_defaultTextColor, Color.parseColor("#ffffff"));
            selectedTextColor = typedArray.getColor(R.styleable.ScaleSelector_selectedTextColor, Color.parseColor("#ffffff"));
            showArrowPointer = typedArray.getBoolean(R.styleable.ScaleSelector_showArrowPointer, false);
        } finally {
            typedArray.recycle();
        }
    }

    private void initLayout(AttributeSet attributeSet) {
        if (isInEditMode())
            return;

        View view = LayoutInflater.from(getContext()).inflate(mainLayoutId, this);

        View recyclerView = view.findViewById(R.id.recycler);
        View arrow = view.findViewById(R.id.arrow);

        if (showArrowPointer) {
            arrow.setVisibility(View.VISIBLE);
        } else {
            arrow.setVisibility(View.GONE);
        }

        if (recyclerView instanceof RecyclerView) {
            mRecycler = (RecyclerView) recyclerView;
        } else {
            throw new IllegalArgumentException("Only works with recyclerView");
        }

       List<Integer> list = new ArrayList();

        if (!(minValue < maxValue)) {
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value");
        }

        for (int i=minValue; i<maxValue; i++)
            list.add(i);



        adapter =  new RecyclerAdapter(list,this,getContext());
        adapter.setPointerColor(pointerColor);
        adapter.setStepValue(stepValue);
        adapter.setBackGroundCardColor(backGroundColor);
        adapter.setDefaultPointerColor(defaultPointerColor);
        adapter.setDefaultTextColor(defaultTextColor);
        adapter.setSelectedTextColor(selectedTextColor);
        mRecycler.setAdapter(adapter);
        RecyclerView.ItemAnimator animator = mRecycler.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
            ((SimpleItemAnimator) animator).setChangeDuration(100);
        }
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

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

    public int getSelectedValue() {
        return selectedValue;
    }

    @Override
    public void onHeightClicked(@NotNull String height, int adapterPosition) {
//        val center = heightRecycler.width /2 - heightRecycler.findViewHolderForAdapterPosition(adapterPosition)?.itemView?.width!! / 2
//        heightLayoutManager.scrollToPositionWithOffset(adapterPosition, center)
        int center = mRecycler.getWidth() / 2 - mRecycler.findViewHolderForAdapterPosition(adapterPosition).itemView.getWidth() / 2;
        linearLayoutManager.scrollToPositionWithOffset(adapterPosition, center);
        selectedValue = Integer.parseInt(height);
    }

}
