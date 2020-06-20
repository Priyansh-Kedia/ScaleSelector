package com.kedia.scaleselector;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CircularScaleSelector extends FrameLayout implements CircularRecyclerAdapter.OnClick {

    private int selectedCircleColor;
    private int defaultTextColor;
    private int selectedTextColor;
    private int pointerColor;
    private int mainLayoutId;
    private int minValue;
    private int backGroundColor;
    private int defaultPointerColor;
    private int maxValue;
    private int arrowPointerTint;
    private Boolean showArrowPointer;

    private RecyclerView mRecycler;
    private CircularRecyclerAdapter adapter;

    private LinearLayoutManager linearLayoutManager ;

    public int selectedValue;

    public CircularScaleSelector(@NonNull Context context) {
        super(context);
    }

    public CircularScaleSelector(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        initLayout(attrs);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.CircularScaleSelector);
        try {
            mainLayoutId = R.layout.circular_recycler_view_layout;
            selectedCircleColor = typedArray.getInt(R.styleable.CircularScaleSelector_selectedCircleColor, 5);
            backGroundColor = typedArray.getColor(R.styleable.CircularScaleSelector_cricleBackgroundColor, Color.parseColor("#000000"));
            minValue = typedArray.getInt(R.styleable.CircularScaleSelector_circleMinValue, 0);
            maxValue = typedArray.getInt(R.styleable.CircularScaleSelector_circleMaxValue, 200);
            defaultTextColor = typedArray.getColor(R.styleable.CircularScaleSelector_circleDefaultTextColor, Color.parseColor("#ffffff"));
            selectedTextColor = typedArray.getColor(R.styleable.CircularScaleSelector_circleSelectedTextColor, Color.parseColor("#ffffff"));
        } finally {
            typedArray.recycle();
        }
    }

    private void initLayout(AttributeSet attributeSet) {
        if (isInEditMode())
            return;

        View view = LayoutInflater.from(getContext()).inflate(mainLayoutId, this);

        View recyclerView = view.findViewById(R.id.ageRecyclerView);


        if (recyclerView instanceof RecyclerView) {
            mRecycler = (RecyclerView) recyclerView;
        } else {
            throw new IllegalArgumentException("Only works with recyclerView");
        }

        List<Integer> list = new ArrayList();

        if (!(minValue < maxValue)) {
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value");
        }

        for (int i=minValue; i<=maxValue; i++)
            list.add(i);



        adapter =  new CircularRecyclerAdapter(list,this,getContext());
        adapter.setSelectedCardColor(selectedCircleColor);
        adapter.setBackGroundCardColor(backGroundColor);
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
    public void onItemClick(int adapterPosition, @NotNull String ageNumber) {
        int center = mRecycler.getWidth() / 2 - mRecycler.findViewHolderForAdapterPosition(adapterPosition).itemView.getWidth() / 2;
        linearLayoutManager.scrollToPositionWithOffset(adapterPosition, center);
        selectedValue = Integer.parseInt(ageNumber);
    }
}
