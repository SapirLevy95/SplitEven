package com.example.split_even;

import android.view.View;

public abstract class PositionalOnClickListener implements View.OnClickListener
{
    int position;
    public PositionalOnClickListener(int position) {
        this.position= position;
    }
}
