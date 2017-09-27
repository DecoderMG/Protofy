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
 * Handles setting the Button line spacing from dialog preference
 */
public class ButtonLineSpacingPreference extends DialogPreference {

    private TextView prefTextView, prefSliderTextView;
    private Slider prefSlider;
    private ButtonPreferences textViewPrefs;

    public ButtonLineSpacingPreference(Context oContext, AttributeSet attrs) {
        super(oContext, attrs);
        textViewPrefs = new ButtonPreferences(oContext);
    }

    @Override
    public void onBindDialogView(View view) {
        prefTextView = (TextView) view.findViewById(R.id.prefTextView);
        prefTextView.setText("Changing this value will change the default starting value of every Buttons line spacing. This modifies the Addition portion of the line spacing algorithm.");
        prefSliderTextView = (TextView) view.findViewById(R.id.prefSliderTextView);
        prefSliderTextView.setText(textViewPrefs.getDefaultButtonLineSpacing() + "dp");

        prefSlider = (Slider) view.findViewById(R.id.prefSlider);
        prefSlider.setMax(textViewPrefs.getMaxButtonLineSpacing());
        prefSlider.setValue(textViewPrefs.getDefaultButtonLineSpacing());
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
            textViewPrefs.setDefaultButtonLineSpacing(prefSlider.getValue() + "");
        }
    }
}
