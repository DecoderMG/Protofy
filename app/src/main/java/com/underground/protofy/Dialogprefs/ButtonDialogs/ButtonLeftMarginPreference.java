package com.underground.protofy.Dialogprefs.ButtonDialogs;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.Protofy.protofy.R;
import com.gc.materialdesign.views.Slider;
import com.underground.protofy.Preferences.ButtonPreferences;

/**
 * Created by Dakota on 10/1/2015.
 * Handles setting the Button left margins from dialog preference
 */
public class ButtonLeftMarginPreference extends DialogPreference {

    private TextView prefTextView, prefSliderTextView;
    private Slider prefSlider;
    private ButtonPreferences textViewPrefs;

    public ButtonLeftMarginPreference(Context oContext, AttributeSet attrs) {
        super(oContext, attrs);
        textViewPrefs = new ButtonPreferences(oContext);
    }

    @Override
    public void onBindDialogView(View view) {
        prefTextView = (TextView) view.findViewById(R.id.prefTextView);
        prefTextView.setText("Changing this will change the default starting value of every Buttons left margin value. Which is measured in DP units");
        prefSliderTextView = (TextView) view.findViewById(R.id.prefSliderTextView);
        prefSliderTextView.setText(textViewPrefs.getDefaultButtonLeftMargin() + "dp");

        prefSlider = (Slider) view.findViewById(R.id.prefSlider);
        prefSlider.setMax(textViewPrefs.getMaxButtonLeftMargin());
        prefSlider.setValue(textViewPrefs.getDefaultButtonLeftMargin());
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
            textViewPrefs.setDefaultButtonLeftMargin(prefSlider.getValue() + "");
        }
    }
}
