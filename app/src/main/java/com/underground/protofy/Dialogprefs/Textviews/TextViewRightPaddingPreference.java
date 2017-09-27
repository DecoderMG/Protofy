package com.underground.protofy.Dialogprefs.Textviews;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.Protofy.protofy.R;
import com.gc.materialdesign.views.Slider;
import com.underground.protofy.Preferences.TextViewPreferences;

/**
 * Created by Dakota on 9/30/2015.
 * Handles setting the TextView right padding from dialog preference
 */
public class TextViewRightPaddingPreference extends DialogPreference {

    private TextView prefTextView, prefSliderTextView;
    private Slider prefSlider;
    private TextViewPreferences textViewPrefs;

    public TextViewRightPaddingPreference(Context oContext, AttributeSet attrs) {
        super(oContext, attrs);
        textViewPrefs = new TextViewPreferences(oContext);
    }

    @Override
    public void onBindDialogView(View view) {
        prefTextView = (TextView) view.findViewById(R.id.prefTextView);
        prefTextView.setText("Changing this will change the default starting value of every TextViews right padding value. Which is measured in DP units");
        prefSliderTextView = (TextView) view.findViewById(R.id.prefSliderTextView);
        prefSliderTextView.setText(textViewPrefs.getDefaultTextViewRightPadding() + "dp");

        prefSlider = (Slider) view.findViewById(R.id.prefSlider);
        prefSlider.setMax(textViewPrefs.getMaxTextViewRightPadding());
        prefSlider.setValue(textViewPrefs.getDefaultTextViewRightPadding());
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
            textViewPrefs.setDefaultTextViewRightPadding(prefSlider.getValue() + "");
        }
    }
}
