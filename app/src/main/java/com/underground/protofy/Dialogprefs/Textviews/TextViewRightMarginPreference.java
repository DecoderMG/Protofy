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
 * Created by Dakota on 10/1/2015.
 * Handles setting the TextView right margin from dialog preference
 */
public class TextViewRightMarginPreference extends DialogPreference {

    private TextView prefTextView, prefSliderTextView;
    private Slider prefSlider;
    private TextViewPreferences textViewPrefs;

    public TextViewRightMarginPreference(Context oContext, AttributeSet attrs) {
        super(oContext, attrs);
        textViewPrefs = new TextViewPreferences(oContext);
    }

    @Override
    public void onBindDialogView(View view) {
        prefTextView = (TextView) view.findViewById(R.id.prefTextView);
        prefTextView.setText("Changing this will change the default starting value of every TextViews right margin value. Which is measured in DP units");
        prefSliderTextView = (TextView) view.findViewById(R.id.prefSliderTextView);
        prefSliderTextView.setText(textViewPrefs.getDefaultTextViewRightMargin() + "dp");

        prefSlider = (Slider) view.findViewById(R.id.prefSlider);
        prefSlider.setMax(textViewPrefs.getMaxTextViewRightMargin());
        prefSlider.setValue(textViewPrefs.getDefaultTextViewRightMargin());
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
            textViewPrefs.setDefaultTextViewRightMargin(prefSlider.getValue() + "");
        }
    }
}
