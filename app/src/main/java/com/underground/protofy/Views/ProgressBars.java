package com.underground.protofy.Views;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

public class ProgressBars extends ProgressBar {
    public ProgressBars(Context p) {
        super(p);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }
}