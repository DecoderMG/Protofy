package com.underground.protofy.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

/**
 * Created by Dakota on 9/30/2015.
 * Handles default button preferences.
 */
public class ButtonPreferences {
    public static final String KEY_PREF_BUTTON_TEXT = "button_default_text";
    public static final String KEY_PREF_BUTTON_FONT = "pref_key_button_default_font";
    public static final String KEY_PREF_BUTTON_EMPHASIS = "pref_key_button_default_emphasis";
    public static final String KEY_PREF_BUTTON_MAX_TEXT_SIZE = "button_default_max_text_size";
    public static final String KEY_PREF_BUTTON_DEFAULT_TEXT_SIZE = "button_default_text_size";
    public static final String KEY_PREF_BUTTON_MAX_LINE_SPACING = "button_default_max_line_spacing";
    public static final String KEY_PREF_BUTTON_DEFAULT_LINE_SPACING = "button_default_line_spacing";
    public static final String KEY_PREF_BUTTON_TEXT_COLOR = "button_default_text_color";
    public static final String KEY_PREF_BUTTON_BACKGROUND_COLOR = "button_default_background_color";
    public static final String KEY_PREF_BUTTON_MAX_TOP_PADDING = "button_default_max_top_padding";
    public static final String KEY_PREF_BUTTON_MAX_BOTTOM_PADDING = "button_default_max_bottom_padding";
    public static final String KEY_PREF_BUTTON_MAX_LEFT_PADDING = "button_default_max_left_padding";
    public static final String KEY_PREF_BUTTON_MAX_RIGHT_PADDING = "button_default_max_right_padding";
    public static final String KEY_PREF_BUTTON_DEFAULT_TOP_PADDING = "button_default_top_padding";
    public static final String KEY_PREF_BUTTON_DEFAULT_BOTTOM_PADDING = "button_default_bottom_padding";
    public static final String KEY_PREF_BUTTON_DEFAULT_LEFT_PADDING = "button_default_left_padding";
    public static final String KEY_PREF_BUTTON_DEFAULT_RIGHT_PADDING = "button_default_right_padding";
    public static final String KEY_PREF_BUTTON_PARENT_RELATION = "button_default_parent_relation";
    public static final String KEY_PREF_BUTTON_MAX_TOP_MARGIN = "button_default_max_top_margin";
    public static final String KEY_PREF_BUTTON_MAX_BOTTOM_MARGIN = "button_default_max_bottom_margin";
    public static final String KEY_PREF_BUTTON_MAX_LEFT_MARGIN = "button_default_max_left_margin";
    public static final String KEY_PREF_BUTTON_MAX_RIGHT_MARGIN = "button_default_max_right_margin";
    public static final String KEY_PREF_BUTTON_DEFAULT_TOP_MARGIN = "button_default_top_margin";
    public static final String KEY_PREF_BUTTON_DEFAULT_BOTTOM_MARGIN = "button_default_bottom_margin";
    public static final String KEY_PREF_BUTTON_DEFAULT_LEFT_MARGIN = "button_default_left_margin";
    public static final String KEY_PREF_BUTTON_DEFAULT_RIGHT_MARGIN = "button_default_right_margin";
    public static final String KEY_PREF_BUTTON_LAYOUT_WRAPPING_WIDTH = "button_default_width_wrapping";
    public static final String KEY_PREF_BUTTON_LAYOUT_WRAPPING_HEIGHT = "button_default_height_wrapping";
    public static final String KEY_PREF_BUTTON_MAX_WIDTH = "button_default_max_width";
    public static final String KEY_PREF_BUTTON_MAX_HEIGHT = "button_default_max_height";

    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    public ButtonPreferences(Context context) {
        this._sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this._prefsEditor = _sharedPrefs.edit();
    }

    //Button Setting Options
    public String getDefaultButtonText() {
        return _sharedPrefs.getString(KEY_PREF_BUTTON_TEXT, "Double-click to see options");
    }

    public String getDefaultButtonFont() {
        return _sharedPrefs.getString(KEY_PREF_BUTTON_FONT, "0");
    }

    public String getDefaultButtonEmphasis() {
        return _sharedPrefs.getString(KEY_PREF_BUTTON_EMPHASIS, "0");
    }

    public int getMaxButtonTextSize() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_TEXT_SIZE, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultButtonTextSize() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_DEFAULT_TEXT_SIZE, "25");
        return Integer.valueOf(value);
    }

    public void setDefaultButtonTextSize(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_DEFAULT_TEXT_SIZE, text);
        _prefsEditor.commit();
    }

    public int getMaxButtonLineSpacing() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_LINE_SPACING, "30");
        return Integer.valueOf(value);
    }

    public int getDefaultButtonLineSpacing() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_DEFAULT_LINE_SPACING, "10");
        return Integer.valueOf(value);
    }

    public void setDefaultButtonLineSpacing(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_DEFAULT_LINE_SPACING, text);
        _prefsEditor.commit();
    }

    public int getButtonTextColor() {
        return _sharedPrefs.getInt(KEY_PREF_BUTTON_TEXT_COLOR, Color.parseColor("#24aaf2"));
    }

    public int getButtonBackgroundColor() {
        return _sharedPrefs.getInt(KEY_PREF_BUTTON_BACKGROUND_COLOR, Color.parseColor("#333333"));
    }

    public int getMaxButtonTopPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_TOP_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxButtonBottomPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_BOTTOM_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxButtonLeftPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_LEFT_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxButtonRightPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_RIGHT_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultButtonTopPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_DEFAULT_TOP_PADDING, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultButtonTopPadding(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_DEFAULT_TOP_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultButtonBottomPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_DEFAULT_BOTTOM_PADDING, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultButtonBottomPadding(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_DEFAULT_BOTTOM_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultButtonLeftPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_DEFAULT_LEFT_PADDING, "24");
        return Integer.valueOf(value);
    }

    public void setDefaultButtonLeftPadding(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_DEFAULT_LEFT_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultButtonRightPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_DEFAULT_RIGHT_PADDING, "24");
        return Integer.valueOf(value);
    }

    public void setDefaultButtonRightPadding(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_DEFAULT_RIGHT_PADDING, text);
        _prefsEditor.commit();
    }

    public String getButtonParentRelation() {
        return _sharedPrefs.getString(KEY_PREF_BUTTON_PARENT_RELATION, "0");
    }

    public int getMaxButtonTopMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_TOP_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxButtonBottomMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_BOTTOM_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxButtonLeftMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_LEFT_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxButtonRightMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_RIGHT_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultButtonTopMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_DEFAULT_TOP_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultButtonTopMargin(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_DEFAULT_TOP_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultButtonBottomMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_DEFAULT_BOTTOM_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultButtonBottomMargin(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_DEFAULT_BOTTOM_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultButtonLeftMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_DEFAULT_LEFT_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultButtonLeftMargin(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_DEFAULT_LEFT_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultButtonRightMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_DEFAULT_RIGHT_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultButtonRightMargin(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_DEFAULT_RIGHT_MARGIN, text);
        _prefsEditor.commit();
    }

    public String getButtonLayoutWrappingWidth() {
        return _sharedPrefs.getString(KEY_PREF_BUTTON_LAYOUT_WRAPPING_WIDTH, "0");
    }

    public String getButtonLayoutWrappingHeight() {
        return _sharedPrefs.getString(KEY_PREF_BUTTON_LAYOUT_WRAPPING_HEIGHT, "0");
    }

    public void setMaxWidth(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_MAX_WIDTH, text);
        _prefsEditor.commit();
    }
    public int getMaxWidth() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_WIDTH, "500");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setMaxHeight(String text) {
        _prefsEditor.putString(KEY_PREF_BUTTON_MAX_HEIGHT, text);
        _prefsEditor.commit();
    }
    public int getMaxHeight() {
        String value = _sharedPrefs.getString(KEY_PREF_BUTTON_MAX_HEIGHT, "500");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
}