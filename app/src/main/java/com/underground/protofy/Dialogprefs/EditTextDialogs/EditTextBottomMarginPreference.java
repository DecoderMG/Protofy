package com.underground.protofy.Dialogprefs.EditTextDialogs;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.Protofy.protofy.R;
import com.gc.materialdesign.views.Slider;
import com.underground.protofy.Preferences.EditTextPreferences;

/**
 * Created by Dakota on 10/1/2015.
 * Handles setting the EditText bottom margins from dialog preference
 */
public class EditTextBottomMarginPreference extends DialogPreference {

    private TextView prefTextView, prefSliderTextView;
    private Slider prefSlider;
    private EditTextPreferences editTextPrefs;

    public EditTextBottomMarginPreference(Context oContext, AttributeSet attrs) {
        super(oContext, attrs);
        editTextPrefs = new EditTextPreferences(oContext);
    }

    @Override
    public void onBindDialogView(View view) {
        prefTextView = (TextView) view.findViewById(R.id.prefTextView);
        prefTextView.setText("Changing this will change the default starting value of every EditTexts bottom margin value. Which is measured in DP units");
        prefSliderTextView = (TextView) view.findViewById(R.id.prefSliderTextView);
        prefSliderTextView.setText(editTextPrefs.getDefaultEditTextBottomMargin() + "dp");

        prefSlider = (Slider) view.findViewById(R.id.prefSlider);
        prefSlider.setMax(editTextPrefs.getMaxEditTextBottomMargin());
        prefSlider.setValue(editTextPrefs.getDefaultEditTextBottomMargin());
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
            editTextPrefs.setDefaultEditTextBottomMargin(prefSlider.getValue() + "");
        }
    }
}
