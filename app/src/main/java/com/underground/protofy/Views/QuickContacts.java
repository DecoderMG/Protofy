package com.underground.protofy.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.QuickContactBadge;

public class QuickContacts extends QuickContactBadge {
    @SuppressLint("ResourceAsColor")
    @SuppressWarnings("deprecation")
    public QuickContacts(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    }
}
