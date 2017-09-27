package com.underground.protofy.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

/**
 * Created by Dakota on 9/30/2015.
 * Handles the default TextView preferences.
 */
public class TextViewPreferences {
    public static final String KEY_PREF_TEXTVIEW_TEXT = "textview_default_text";
    public static final String KEY_PREF_TEXTVIEW_FONT = "pref_key_textview_default_font";
    public static final String KEY_PREF_TEXTVIEW_EMPHASIS = "pref_key_textview_default_emphasis";
    public static final String KEY_PREF_TEXTVIEW_MAX_TEXT_SIZE = "textview_default_max_text_size";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_TEXT_SIZE = "textview_default_text_size";
    public static final String KEY_PREF_TEXTVIEW_MAX_LINE_SPACING = "textview_default_max_line_spacing";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_LINE_SPACING = "textview_default_line_spacing";
    public static final String KEY_PREF_TEXTVIEW_TEXT_COLOR = "textview_default_text_color";
    public static final String KEY_PREF_TEXTVIEW_BACKGROUND_COLOR = "textview_default_background_color";
    public static final String KEY_PREF_TEXTVIEW_MAX_TOP_PADDING = "textview_default_max_top_padding";
    public static final String KEY_PREF_TEXTVIEW_MAX_BOTTOM_PADDING = "textview_default_max_bottom_padding";
    public static final String KEY_PREF_TEXTVIEW_MAX_LEFT_PADDING = "textview_default_max_left_padding";
    public static final String KEY_PREF_TEXTVIEW_MAX_RIGHT_PADDING = "textview_default_max_right_padding";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_TOP_PADDING = "textview_default_top_padding";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_BOTTOM_PADDING = "textview_default_bottom_padding";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_LEFT_PADDING = "textview_default_left_padding";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_RIGHT_PADDING = "textview_default_right_padding";
    public static final String KEY_PREF_TEXTVIEW_PARENT_RELATION = "textview_default_parent_relation";
    public static final String KEY_PREF_TEXTVIEW_MAX_TOP_MARGIN = "textview_default_max_top_margin";
    public static final String KEY_PREF_TEXTVIEW_MAX_BOTTOM_MARGIN = "textview_default_max_bottom_margin";
    public static final String KEY_PREF_TEXTVIEW_MAX_LEFT_MARGIN = "textview_default_max_left_margin";
    public static final String KEY_PREF_TEXTVIEW_MAX_RIGHT_MARGIN = "textview_default_max_right_margin";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_TOP_MARGIN = "textview_default_top_margin";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_BOTTOM_MARGIN = "textview_default_bottom_margin";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_LEFT_MARGIN = "textview_default_left_margin";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_RIGHT_MARGIN = "textview_default_right_margin";
    public static final String KEY_PREF_TEXTVIEW_LAYOUT_WRAPPING_WIDTH = "textview_default_width_wrapping";
    public static final String KEY_PREF_TEXTVIEW_LAYOUT_WRAPPING_HEIGHT = "textview_default_height_wrapping";
    public static final String KEY_PREF_TEXTVIEW_MAX_WIDTH = "textview_default_max_width";
    public static final String KEY_PREF_TEXTVIEW_MAX_HEIGHT = "textview_default_max_height";
    public static final String KEY_PREF_TEXTVIEW_DEFAULT_TINT_MODE = "textview_default_tint_mode";

    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    public TextViewPreferences(Context context) {
        this._sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this._prefsEditor = _sharedPrefs.edit();
    }

    //TextView Setting Options
    public String getDefaultTextViewText() {
        return _sharedPrefs.getString(KEY_PREF_TEXTVIEW_TEXT, "Double-click to see options");
    }

    public String getDefaultTextViewFont() {
        return _sharedPrefs.getString(KEY_PREF_TEXTVIEW_FONT, "0");
    }

    public String getDefaultTextViewEmphasis() {
        return _sharedPrefs.getString(KEY_PREF_TEXTVIEW_EMPHASIS, "0");
    }

    public int getMaxTextViewTextSize() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_TEXT_SIZE, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultTextViewTextSize() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_TEXT_SIZE, "25");
        return Integer.valueOf(value);
    }

    public void setDefaultTextViewTextSize(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_TEXT_SIZE, text);
        _prefsEditor.commit();
    }

    public int getMaxTextViewLineSpacing() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_LINE_SPACING, "30");
        return Integer.valueOf(value);
    }

    public int getDefaultTextViewLineSpacing() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_LINE_SPACING, "10");
        return Integer.valueOf(value);
    }

    public void setDefaultTextViewLineSpacing(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_LINE_SPACING, text);
        _prefsEditor.commit();
    }

    public int getTextViewTextColor() {
        return _sharedPrefs.getInt(KEY_PREF_TEXTVIEW_TEXT_COLOR, Color.parseColor("#333333"));
    }

    public int getTextViewBackgroundColor() {
        return _sharedPrefs.getInt(KEY_PREF_TEXTVIEW_BACKGROUND_COLOR, Color.parseColor("#00000000"));
    }

    public int getMaxTextViewTopPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_TOP_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxTextViewBottomPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_BOTTOM_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxTextViewLeftPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_LEFT_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getMaxTextViewRightPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_RIGHT_PADDING, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultTextViewTopPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_TOP_PADDING, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultTextViewTopPadding(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_TOP_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultTextViewBottomPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_BOTTOM_PADDING, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultTextViewBottomPadding(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_BOTTOM_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultTextViewLeftPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_LEFT_PADDING, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultTextViewLeftPadding(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_LEFT_PADDING, text);
        _prefsEditor.commit();
    }

    public int getDefaultTextViewRightPadding() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_RIGHT_PADDING, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultTextViewRightPadding(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_RIGHT_PADDING, text);
        _prefsEditor.commit();
    }

    public String getTextViewParentRelation() {
        return _sharedPrefs.getString(KEY_PREF_TEXTVIEW_PARENT_RELATION, "0");
    }

    public int getMaxTextViewTopMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_TOP_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxTextViewBottomMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_BOTTOM_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxTextViewLeftMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_LEFT_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getMaxTextViewRightMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_RIGHT_MARGIN, "50");
        return Integer.valueOf(value);
    }

    public int getDefaultTextViewTopMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_TOP_MARGIN, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultTextViewTopMargin(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_TOP_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultTextViewBottomMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_BOTTOM_MARGIN, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultTextViewBottomMargin(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_BOTTOM_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultTextViewLeftMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_LEFT_MARGIN, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultTextViewLeftMargin(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_LEFT_MARGIN, text);
        _prefsEditor.commit();
    }

    public int getDefaultTextViewRightMargin() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_RIGHT_MARGIN, "0");
        return Integer.valueOf(value);
    }

    public void setDefaultTextViewRightMargin(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_RIGHT_MARGIN, text);
        _prefsEditor.commit();
    }

    public String getTextViewLayoutWrappingWidth() {
        return _sharedPrefs.getString(KEY_PREF_TEXTVIEW_LAYOUT_WRAPPING_WIDTH, "0");
    }

    public String getTextViewLayoutWrappingHeight() {
        return _sharedPrefs.getString(KEY_PREF_TEXTVIEW_LAYOUT_WRAPPING_HEIGHT, "0");
    }

    public void setMaxWidth(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_MAX_WIDTH, text);
        _prefsEditor.commit();
    }
    public int getMaxWidth() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_WIDTH, "500");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setMaxHeight(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_MAX_HEIGHT, text);
        _prefsEditor.commit();
    }
    public int getMaxHeight() {
        String value = _sharedPrefs.getString(KEY_PREF_TEXTVIEW_MAX_HEIGHT, "500");
        return Integer.valueOf(Math.round(Float.parseFloat(value)));
    }
    public void setDefaultTintMode(String text) {
        _prefsEditor.putString(KEY_PREF_TEXTVIEW_DEFAULT_TINT_MODE, text);
        _prefsEditor.commit();
    }
    public String getDefaultTintMode() {
        return _sharedPrefs.getString(KEY_PREF_TEXTVIEW_DEFAULT_TINT_MODE, "0");
    }
}
