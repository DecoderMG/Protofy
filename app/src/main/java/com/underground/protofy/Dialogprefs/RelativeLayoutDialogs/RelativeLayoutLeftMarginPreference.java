package com.underground.protofy.Dialogprefs.RelativeLayoutDialogs;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.Protofy.protofy.R;
import com.gc.materialdesign.views.Slider;
import com.underground.protofy.Preferences.RelativeLayoutPreferences;

/**
 * Created by Dakota on 10/1/2015.
 * Handles setting the Relative Layout left margins from dialog preference
 */
public class RelativeLayoutLeftMarginPreference extends DialogPreference {

    private TextView prefTextView, prefSliderTextView;
    private Slider prefSlider;
    private RelativeLayoutPreferences textViewPrefs;

    public RelativeLayoutLeftMarginPreference(Context oContext, AttributeSet attrs) {
        super(oContext, attrs);
        textViewPrefs = new RelativeLayoutPreferences(oContext);
    }

    @Override
    public void onBindDialogView(View view) {
        prefTextView = (TextView) view.findViewById(R.id.prefTextView);
        prefTextView.setText("Changing this will change the default starting value of every RelativeLayouts left margin value. Which is measured in DP units");
        prefSliderTextView = (TextView) view.findViewById(R.id.prefSliderTextView);
        prefSliderTextView.setText(textViewPrefs.getDefaultRelativeLayoutLeftMargin() + "dp");

        prefSlider = (Slider) view.findViewById(R.id.prefSlider);
        prefSlider.setMax(textViewPrefs.getMaxRelativeLayoutLeftMargin());
        prefSlider.setValue(textViewPrefs.getDefaultRelativeLayoutLeftMargin());
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
            textViewPrefs.setDefaultRelativeLayoutLeftMargin(prefSlider.getValue() + "");
        }
    }
}
