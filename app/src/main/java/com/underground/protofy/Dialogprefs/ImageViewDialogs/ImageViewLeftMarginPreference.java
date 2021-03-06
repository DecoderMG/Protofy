package com.underground.protofy.Dialogprefs.ImageViewDialogs;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.Protofy.protofy.R;
import com.gc.materialdesign.views.Slider;
import com.underground.protofy.Preferences.ImageViewPreferences;

/**
 * Created by Dakota on 10/1/2015.
 * Handles setting the ImageView left margins from dialog preference
 */
public class ImageViewLeftMarginPreference extends DialogPreference {

    private TextView prefTextView, prefSliderTextView;
    private Slider prefSlider;
    private ImageViewPreferences textViewPrefs;

    public ImageViewLeftMarginPreference(Context oContext, AttributeSet attrs) {
        super(oContext, attrs);
        textViewPrefs = new ImageViewPreferences(oContext);
    }

    @Override
    public void onBindDialogView(View view) {
        prefTextView = (TextView) view.findViewById(R.id.prefTextView);
        prefTextView.setText("Changing this will change the default starting value of every ImageViews left margin value. Which is measured in DP units");
        prefSliderTextView = (TextView) view.findViewById(R.id.prefSliderTextView);
        prefSliderTextView.setText(textViewPrefs.getDefaultImageViewLeftMargin() + "dp");

        prefSlider = (Slider) view.findViewById(R.id.prefSlider);
        prefSlider.setMax(textViewPrefs.getMaxImageViewLeftMargin());
        prefSlider.setValue(textViewPrefs.getDefaultImageViewLeftMargin());
        prefSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                prefSliderTextView.setText(value + "dp");
            }
        });

        super.onBindDialogView(view);
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            textViewPrefs.setDefaultImageViewLeftMargin(prefSlider.getValue() + "");
        }
    }
}
