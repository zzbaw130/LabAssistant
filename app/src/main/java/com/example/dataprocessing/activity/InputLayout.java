package com.example.dataprocessing.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
public class InputLayout extends LinearLayout {

    public InputLayout(Context context) {
        this(context,null);
    }

    public InputLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InputLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
}
