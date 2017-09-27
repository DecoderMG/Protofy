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
 * Handles setting the Relative Layout bottom padding from dialog preference
 */
public class RelativeLayoutBottomPaddingPreference extends DialogPreference {

    private TextView prefTextView, prefSliderTextView;
    private Slider prefSlider;
    private RelativeLayoutPreferences textViewPrefs;

    public RelativeLayoutBottomPaddingPreference(Context oContext, AttributeSet attrs) {
        super(oContext, attrs);
        textViewPrefs = new RelativeLayoutPreferences(oContext);
    }

    @Override
    public void onBindDialogView(View view) {
        prefTextView = (TextView) view.findViewById(R.id.prefTextView);
        prefTextView.setText("Changing this will change the default starting value of every RelativeLayouts bottom padding value. Which is measured in DP units");
        prefSliderTextView = (TextView) view.findViewById(R.id.prefSliderTextView);
        prefSliderTextView.setText(textViewPrefs.getDefaultRelativeLayoutBottomPadding() + "dp");

        prefSlider = (Slider) view.findViewById(R.id.prefSlider);
        prefSlider.setMax(textViewPrefs.getMaxRelativeLayoutBottomPadding());
        prefSlider.setValue(textViewPrefs.getDefaultRelativeLayoutBottomPadding());
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
            textViewPrefs.setDefaultRelativeLayoutBottomPadding(prefSlider.getValue() + "");
        }
    }
}
