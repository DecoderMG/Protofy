package com.underground.protofy.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

/**
 * Created by Dakota on 10/1/2015.
 * Handles EditText default preferences.
 */
public class EditTextPreferences {
    public static final String KEY_PREF_EDITTEXT_TEXT = "edittext_default_text";
    public static final String KEY_PREF_EDITTEXT_FONT = "pref_key_edittext_default_font";
    public static final String KEY_PREF_EDITTEXT_EMPHASIS = "pref_key_edittext_default_emphasis";
    public static final String KEY_PREF_EDITTEXT_MAX_TEXT_SIZE = "edittext_default_max_text_size";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_TEXT_SIZE = "edittext_default_text_size";
    public static final String KEY_PREF_EDITTEXT_MAX_LINE_SPACING = "edittext_default_max_line_spacing";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_LINE_SPACING = "edittext_default_line_spacing";
    public static final String KEY_PREF_EDITTEXT_TEXT_COLOR = "edittext_default_text_color";
    public static final String KEY_PREF_EDITTEXT_BACKGROUND_COLOR = "edittext_default_background_color";
    public static final String KEY_PREF_EDITTEXT_MAX_TOP_PADDING = "edittext_default_max_top_padding";
    public static final String KEY_PREF_EDITTEXT_MAX_BOTTOM_PADDING = "edittext_default_max_bottom_padding";
    public static final String KEY_PREF_EDITTEXT_MAX_LEFT_PADDING = "edittext_default_max_left_padding";
    public static final String KEY_PREF_EDITTEXT_MAX_RIGHT_PADDING = "edittext_default_max_right_padding";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_TOP_PADDING = "edittext_default_top_padding";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_BOTTOM_PADDING = "edittext_default_bottom_padding";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_LEFT_PADDING = "edittext_default_left_padding";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_RIGHT_PADDING = "edittext_default_right_padding";
    public static final String KEY_PREF_EDITTEXT_PARENT_RELATION = "edittext_default_parent_relation";
    public static final String KEY_PREF_EDITTEXT_MAX_TOP_MARGIN = "edittext_default_max_top_margin";
    public static final String KEY_PREF_EDITTEXT_MAX_BOTTOM_MARGIN = "edittext_default_max_bottom_margin";
    public static final String KEY_PREF_EDITTEXT_MAX_LEFT_MARGIN = "edittext_default_max_left_margin";
    public static final String KEY_PREF_EDITTEXT_MAX_RIGHT_MARGIN = "edittext_default_max_right_margin";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_TOP_MARGIN = "edittext_default_top_margin";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_BOTTOM_MARGIN = "edittext_default_bottom_margin";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_LEFT_MARGIN = "edittext_default_left_margin";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_RIGHT_MARGIN = "edittext_default_right_margin";
    public static final String KEY_PREF_EDITTEXT_LAYOUT_WRAPPING_WIDTH = "edittext_default_width_wrapping";
    public static final String KEY_PREF_EDITTEXT_LAYOUT_WRAPPING_HEIGHT = "edittext_default_height_wrapping";
    public static final String KEY_PREF_EDITTEXT_MAX_WIDTH = "edittext_default_max_width";
    public static final String KEY_PREF_EDITTEXT_MAX_HEIGHT = "edittext_default_max_height";
    public static final String KEY_PREF_EDITTEXT_DEFAULT_TINT_MODE = "edittext_default_tint_mode";

    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    public EditTextPreferences(Context context) {
        this._sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this._prefsEditor = _sharedPrefs.edit();
    }

    //EditText Setting Options
    public String getDefaultEditTextText() {
        return _sharedPrefs.getString(KEY_PREF_EDITTEXT_TEXT, "Double-click to see options");
    }

    public String getDefaultEditTextFont() {
        return _sharedPrefs.getString(KEY_PREF_EDITTEXT_FONT, "0");
    }

    public String getDefaultEditTextEmphasis() {
        return _sharedPrefs.getString(KEY_PREF_EDITTEXT_EMPHASIS, "0");
    }

    public int getMaxEditTextTextSize() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_TEXT_SIZE, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultEditTextTextSize() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_TEXT_SIZE, "25");
        return Integer.valueOf(value);
    }

    public void setDefaultEditTextTextSize(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_TEXT_SIZE, text);
        _prefsEditor.commit();
    }

    public int getMaxEditTextLineSpacing() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_LINE_SPACING, "30");
        return Integer.valueOf(value);
    }

    public int getDefaultEditTextLineSpacing() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_LINE_SPACING, "10");
        return Integer.valueOf(value);
    }

    public void setDefaultEditTextLineSpacing(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_LINE_SPACING, text);
        _prefsEditor.commit();
    }

    public int getEditTextTextColor() {
        return _sharedPrefs.getInt(KEY_PREF_EDITTEXT_TEXT_COLOR, Color.parseColor("#24aaf2"));
    }

    public int getEditTextBackgroundColor() {
        return _sharedPrefs.getInt(KEY_PREF_EDITTEXT_BACKGROUND_COLOR, Color.parseColor("#333333"));
    }

    public int getMaxEditTextTopPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_TOP_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxEditTextBottomPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_BOTTOM_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxEditTextLeftPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_LEFT_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxEditTextRightPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_RIGHT_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultEditTextTopPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_TOP_PADDING, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultEditTextTopPadding(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_TOP_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultEditTextBottomPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_BOTTOM_PADDING, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultEditTextBottomPadding(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_BOTTOM_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultEditTextLeftPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_LEFT_PADDING, "24");
        return Integer.valueOf(value);
    }

    public void setDefaultEditTextLeftPadding(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_LEFT_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultEditTextRightPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_RIGHT_PADDING, "24");
        return Integer.valueOf(value);
    }

    public void setDefaultEditTextRightPadding(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_RIGHT_PADDING, text);
        _prefsEditor.commit();
    }

    public String getEditTextParentRelation() {
        return _sharedPrefs.getString(KEY_PREF_EDITTEXT_PARENT_RELATION, "0");
    }

    public int getMaxEditTextTopMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_TOP_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxEditTextBottomMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_BOTTOM_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxEditTextLeftMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_LEFT_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxEditTextRightMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_RIGHT_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultEditTextTopMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_TOP_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultEditTextTopMargin(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_TOP_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultEditTextBottomMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_BOTTOM_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultEditTextBottomMargin(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_BOTTOM_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultEditTextLeftMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_LEFT_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultEditTextLeftMargin(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_LEFT_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultEditTextRightMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_RIGHT_MARGIN, "16");
        return Integer.valueOf(value);
    }

    public void setDefaultEditTextRightMargin(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_RIGHT_MARGIN, text);
        _prefsEditor.commit();
    }

    public String getEditTextLayoutWrappingWidth() {
        return _sharedPrefs.getString(KEY_PREF_EDITTEXT_LAYOUT_WRAPPING_WIDTH, "0");
    }

    public String getEditTextLayoutWrappingHeight() {
        return _sharedPrefs.getString(KEY_PREF_EDITTEXT_LAYOUT_WRAPPING_HEIGHT, "0");
    }

    public void setMaxWidth(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_MAX_WIDTH, text);
        _prefsEditor.commit();
    }
    public int getMaxWidth() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_WIDTH, "500");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setMaxHeight(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_MAX_HEIGHT, text);
        _prefsEditor.commit();
    }
    public int getMaxHeight() {
        String value = _sharedPrefs.getString(KEY_PREF_EDITTEXT_MAX_HEIGHT, "500");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setDefaultTintMode(String text) {
        _prefsEditor.putString(KEY_PREF_EDITTEXT_DEFAULT_TINT_MODE, text);
        _prefsEditor.commit();
    }
    public String getDefaultTintMode() {
        return _sharedPrefs.getString(KEY_PREF_EDITTEXT_DEFAULT_TINT_MODE, "0");
    }
}
