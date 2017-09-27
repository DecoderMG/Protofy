package com.underground.protofy.Whiteboard;

/*Android dependent imports*/

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Protofy.protofy.R;
import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.provider.SmallImageCardProvider;
import com.dexafree.materialList.listeners.OnDismissCallback;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListAdapter;
import com.dexafree.materialList.view.MaterialListView;
import com.gc.bordermenu.activities.BorderMenuActivity;
import com.gc.bordermenu.views.IconItem;
import com.gc.bordermenu.views.TextItem;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.Slider;
import com.gc.materialdesign.widgets.SnackBar;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;
import com.rarepebble.colorpicker.ColorPickerView;
import com.underground.protofy.CustomDataStructures.UIelementList;
import com.underground.protofy.CustomDataStructures.UIidDataStructure;
import com.underground.protofy.Preferences.AppPreferences;
import com.underground.protofy.Preferences.ButtonPreferences;
import com.underground.protofy.Preferences.EditTextPreferences;
import com.underground.protofy.Preferences.ImageViewPreferences;
import com.underground.protofy.Preferences.RelativeLayoutPreferences;
import com.underground.protofy.Preferences.TextViewPreferences;
import com.underground.protofy.Views.mButton;
import com.underground.protofy.Views.mEditText;
import com.underground.protofy.Views.mImageView;
import com.underground.protofy.Views.mRelativeLayout;
import com.underground.protofy.Views.mTextView;
import com.underground.protofy.XMLParser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;

import tourguide.tourguide.TourGuide;


/**
 * Class for handling all dialog options within the activity.
 */

// Suggested improvements is to apply a more OOP to each Dialog.
public class Dialogs extends BorderMenuActivity {
    /*
    Start dialog switch case identifiers
     */
    protected static final int SET_TEXT = 0;
    protected static final int TEXT_STYLE = 1;
    protected static final int SET_TEXT_SIZE = 2;
    protected static final int COLOR = 3;
    protected static final int SET_PADDING = 4;
    protected static final int SET_LINE_SPACING = 5;
    protected static final int PIXEL_LAYOUT_MENU = 6;
    protected static final int SCALE = 7;
    protected static final int PARAMETERS = 8;
    protected static final int MARGINS_MENU = 9;
    protected static final int SPINNER_OPTIONS = 10;
    protected static final int SET_HINT = 11;
    protected static final int RATINGBAR_OPTIONS = 12;
    protected static final int PROGRESSBAR_OPTIONS = 13;
    protected static final int SEEKBAR_OPTIONS = 14;
    protected static final int LINEARLAYOUT_OPTIONS = 15;
    protected static final int SET_GRAVITY = 16;
    protected static final int SHADOW_LAYER = 17;
    protected static final int SET_ID = 18;
    protected static final int LAYOUT_PARENT_POSITIONING = 19;
    protected static final int LAYOUT_OTHER_VIEW_POSITIONING = 20;
    protected static final int LIST_OF_CURRENT_VIEW_IDS = 21;
    protected static final int ADD_VIEW = 22;
    protected static final int GENERATE_XML = 23;
    protected static final int REMOVE_VIEW = 24;
    protected static final int REMOVE_PARAMS = 25;
    protected static final int ARE_YOU_SURE_DIALOG = 26;
    protected static final int EXIT_WHITEBOARD = 27;
    protected static final int SET_VISIBILITY = 28;
    protected static final int SET_VIEW_SIZE = 29;
    protected static final int SET_BACKGROUND_TINT = 30;
    protected static final int SET_IMAGE_CHECK = 31;
    protected static final int SET_IMAGE_SCALING_TYPE = 32;
    protected static final int SET_CHILD_FRONT_BACK = 33;
    /*
    End dialog switch case identifiers
     */

    /*
    View positional temp variables
     */
    private static final int PLACE_ABOVE_VIEW = 200;
    private static final int PLACE_BELOW_VIEW = 201;
    private static final int PLACE_LEFT_OF_VIEW = 202;
    private static final int PLACE_RIGHT_OF_VIEW = 203;
    private static final int ALIGN_BASELINE = 204;
    private static final int ALIGN_END = 205;
    private static final int ALIGN_LEFT_EDGES = 206;
    private static final int ALIGN_RIGHT_EDGES = 207;
    private static final int ALIGN_TOP = 208;
    private static final int ALIGN_START = 209;


    private static final int REQUEST_WRITE_STORAGE = 1;

    /*
    Outside app Intent variables
     */
    private static final int SELECT_PICTURE = 1000;

    protected Bundle savedInstanceStateSecond;
    protected String[] menuarray = {};
    protected TypedArray menuGraphics;
    protected String menuOnDisplay;
    protected ToolTipRelativeLayout mToolTipFrameLayout;
    protected ToolTip toolTip;
    //protected int shadowColor;
    protected BigInteger tempViewId;
    protected UIidDataStructure itList;
    protected UIelementList uiList;
    protected mRelativeLayout baseLayout;
    protected AppPreferences appPrefs;
    protected boolean tutorialState;
    protected ArrayList<String> viewIdHolderForCheck;
    protected TourGuide mTourGuideHandler;
    protected TourGuide mWhiteboardSettingsGuideHandler;
    protected boolean tutorialCompleted = false, hasStoragePermission = false;
    protected Activity mActivity;
    RelativeLayout.LayoutParams previewLayoutParams;
    private int layoutPositioningSelection = 0;
    private boolean setViewColor;
    private View currentView;
    protected View currentLayoutView;
    private Object viewTag;
    private Bundle onCreateBundle;
    private ArrayList<String> tagStringList;
    private int colorSelection = 0;

    private String stringParam = "";
    private int paramData = 0;

    protected Context mContext;

    protected String currentXMLFileName = "";
    private String viewDeletedStringId = "";

    /*
    View preference variables (used to access each view's default variables).
     */
    private TextViewPreferences textViewPrefs;
    private ButtonPreferences buttonPrefs;
    private EditTextPreferences editTextPrefs;
    private ImageViewPreferences imageViewPrefs;
    private RelativeLayoutPreferences relativeLayoutPrefs;


    /**
     * Set current working index to the current view user wants to edit.
     * @param view - View user wants to edit
     * @param ob - tag identifier of the current working view
     * @param bundle - Current working bundle.
     */
    public void setIndexValue(View view, Object ob, Bundle bundle) {
        currentView = view;
        if(view.getTag() != "BASE_LAYOUT" || view.getTag() != "RELATIVE_LAYOUT") {
            setCurrentLayoutView(view);
        }
        viewTag = ob;
        onCreateBundle = bundle;
    }

    /**
     * Set current working layout view that the user wants to edit
     * @param view - View user is editing
     * @param bundle - Current working bundle
     */
    public void setLayoutIndexValue(View view, Bundle bundle) {
        currentLayoutView = view;
        currentLayoutView.setId(view.getId());
        onCreateBundle = bundle;
    }

    /**
     * Create copy of currently selected view user wants to edit.
     * @param view - View user is currently working on
     */
    public void setCurrentLayoutView(View view) {
        String parentIdentifier = "";
        if(view.getTag().equals("TEXTVIEW")) {
            mTextView tempTextView = new mTextView(this);
            tempTextView.initCopyView((mTextView) view, "set id as text");
            parentIdentifier = tempTextView.getParentIdentifier();
        } else if(view.getTag().equals("BUTTON")) {
            mButton tempButton = new mButton(this);
            tempButton.initCopyView((mButton) view, "set id as text");
            parentIdentifier = tempButton.getParentIdentifier();
        } else if(view.getTag().equals("EDITTEXT")) {
            mEditText tempEditText = new mEditText(this);
            tempEditText.initCopyView((mEditText) view, "set id as text");
            parentIdentifier = tempEditText.getParentIdentifier();
        } else if(view.getTag().equals("IMAGEVIEW")) {
            mImageView tempImageView = new mImageView(this);
            tempImageView.initCopyView((mImageView) view, "set id");
            parentIdentifier = tempImageView.getParentIdentifier();
        }

        for(int i = 0; i < uiList.getSize(); i++){
            mRelativeLayout tempLayout = (mRelativeLayout) uiList.getView(i);
            if(tempLayout.getStringID().equals(parentIdentifier))
                currentLayoutView.setId(tempLayout.getId());
        }
    }

    /**
     * Pass view to dialogs for view attribute access
     * @param idView - View passed for dialog
     * @param ob - View identifier tag
     * @param bundle - Current working bundle
     */
    public void setDialogView(View idView, Object ob, Bundle bundle) {
        currentView = idView;
        viewTag = ob;
        onCreateBundle = bundle;
    }

    /**
     * Adds new ID to main view id tracker and prints a log of current view IDs held. Will initialize a new tracker if tracker doesn't exist.
     * @param stringID - ID of view user specified
     * @param hexID - hex representation of string ID for android
     */
    public void logCurrentViewIds(String stringID, String hexID) {
        if (itList == null) {
            itList = new UIidDataStructure();
            itList.initializeUIidDataStructure();
            tagStringList = new ArrayList<>();
        }
        itList.addId(hexID);
        tagStringList.add(tagStringList.size(), stringID);
        for (int i = 0; i < itList.getSize(); i++) {
            Log.v("id's in List: ", itList.getHexId(i));
        }
    }

    /**
     * Display alert dialog based on which dialog needs to be displayed. All dialogs present the user with a preview before changes are committed and all dialog boxes handle applying new properties to views.
     * @param id - Dialog we want to display
     * @return - Returns and displays requested dialog box.
     */
    @SuppressWarnings("deprecation")
    //@Override
    protected Dialog onCreateDialog(int id) {
        /*
        Initialize each view that will be used as a view preview in the dialogs. This is needed as
        views in Android CAN NOT share the same parent view, making a simple pointer to a view not viable.
        Preview views will inherent the view that needs to be displayed's variables via initCopyView(ViewType viewtype, String copyOptions)
         */
        final mTextView previewTextView = new mTextView(this);
        final mButton previewButton = new mButton(this);
        final mEditText previewEditText = new mEditText(this);
        final mImageView previewImageView = new mImageView(this);
        final mRelativeLayout previewRelativeLayout = new mRelativeLayout(this);
        previewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        previewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        switch (id) {

            /*
            Dialog that handles previewing and setting a Views Text. Current views to use this dialog are Buttons and TextViews
             */
            case SET_TEXT:
                LayoutInflater factory = LayoutInflater.from(this);
                final View textEntryView = factory.inflate(R.layout.settextlayout, null);
                FrameLayout textPreviewFrame = (FrameLayout) textEntryView.findViewById(R.id.selectedTextViewDisplay);
                /*
                View detection and variable copy over for preview views
                 */
                if (viewTag == "TEXTVIEW") {
                    previewTextView.initCopyView((mTextView) currentView, "set Text /preview");
                    previewTextView.setLayoutParams(previewLayoutParams);
                    textPreviewFrame.addView(previewTextView);
                }
                if (viewTag == "BUTTON") {
                    previewButton.initCopyView((mButton) currentView, "set Text /preview");
                    previewButton.setLayoutParams(previewLayoutParams);
                    textPreviewFrame.addView(previewButton);
                }
                /*
                Link dialog EditText Field and apply a text changed listener for a real-time preview
                 */
                final EditText setTextEditText = (EditText) textEntryView.findViewById(R.id.setTextEditText);
                setTextEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewText(setTextEditText.getText().toString());
                        if (viewTag == "BUTTON")
                            previewButton.setViewText(setTextEditText.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                new AlertDialogWrapper.Builder(this)
                        .setTitle("Set Text")
                        .setView(textEntryView)
                        .setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(setTextEditText, InputMethodManager.SHOW_IMPLICIT);
                            }
                        })
                        .setPositiveButton("Set Text", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                /*
                                Declare pointer to selected view and set value. Using the View's tag to determine pointer type
                                 */
                                if (viewTag == "TEXTVIEW") {
                                    mTextView v = (mTextView) currentView;
                                    v.setViewText(setTextEditText.getText().toString());
                                }
                                if (viewTag == "BUTTON") {
                                    mButton b = (mButton) currentView;
                                    b.setViewText(setTextEditText.getText().toString());
                                }
                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return null;

            /*
            Dialog responsible for setting text font and style.
             */
            case TEXT_STYLE:
                LayoutInflater textStyleInflator = LayoutInflater.from(this);
                final View textStyleEntryView = textStyleInflator.inflate(R.layout.textstylelayout, null);
                final FrameLayout viewPreviewFrame = (FrameLayout) textStyleEntryView.findViewById(R.id.selectedTextStyleViewDisplay);

                final TableLayout emphasisLayout = (TableLayout) textStyleEntryView.findViewById(R.id.emphasisTextStyleOptions);
                final TableLayout typefaceLayout = (TableLayout) textStyleEntryView.findViewById(R.id.typefaceTextStyleOptions);

                final ButtonFlat emphasisButton = (ButtonFlat) textStyleEntryView.findViewById(R.id.TextEmphasisButton);
                final ButtonFlat textStyleButton = (ButtonFlat) textStyleEntryView.findViewById(R.id.TextStyleButton);
                final TextView emphasisNormalTextView = (TextView) textStyleEntryView.findViewById(R.id.emphasisNormalTextView);
                final TextView emphasisBoldTextView = (TextView) textStyleEntryView.findViewById(R.id.emphasisBoldTextView);
                final TextView emphasisItalicTextView = (TextView) textStyleEntryView.findViewById(R.id.emphasisItalicTextView);
                final TextView emphasisBoldItalicTextView = (TextView) textStyleEntryView.findViewById(R.id.emphasisBoldItalicTextView);
                final TextView typefaceNormalTextView = (TextView) textStyleEntryView.findViewById(R.id.typefaceDefaultTextView);
                final TextView typefaceMonospaceTextView = (TextView) textStyleEntryView.findViewById(R.id.typefaceMonospaceTextView);
                final TextView typefaceSerifTextView = (TextView) textStyleEntryView.findViewById(R.id.typefaceSerifTextView);
                final TextView typefaceSanSerifTextView = (TextView) textStyleEntryView.findViewById(R.id.typefaceSanSerifTextView);


                if (viewTag == "TEXTVIEW") {
                    previewTextView.initCopyView((mTextView) currentView, "set Text /preview");
                    previewTextView.setLayoutParams(previewLayoutParams);
                    viewPreviewFrame.addView(previewTextView);
                    if (previewTextView.getViewTypeface().equals(Typeface.DEFAULT)) {
                        typefaceNormalTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewTextView.getViewTypeface().equals(Typeface.MONOSPACE)) {
                        typefaceMonospaceTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewTextView.getViewTypeface().equals(Typeface.SERIF)) {
                        typefaceSerifTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewTextView.getViewTypeface().equals(Typeface.SANS_SERIF)) {
                        typefaceSanSerifTextView.setTextColor(Color.parseColor("#24aaf2"));
                    }
                    if (previewTextView.getViewTypefaceStyle() == Typeface.NORMAL) {
                        emphasisNormalTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewTextView.getViewTypefaceStyle() == Typeface.BOLD) {
                        emphasisBoldTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewTextView.getViewTypefaceStyle() == Typeface.ITALIC) {
                        emphasisItalicTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewTextView.getViewTypefaceStyle() == Typeface.BOLD_ITALIC) {
                        emphasisBoldItalicTextView.setTextColor(Color.parseColor("#24aaf2"));
                    }
                }
                if (viewTag == "BUTTON") {
                    previewButton.initCopyView((mButton) currentView, "set Text /preview");
                    previewButton.setLayoutParams(previewLayoutParams);
                    viewPreviewFrame.addView(previewButton);
                    if (previewButton.getViewTypeface().equals(Typeface.DEFAULT)) {
                        typefaceNormalTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewButton.getViewTypeface().equals(Typeface.MONOSPACE)) {
                        typefaceMonospaceTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewButton.getViewTypeface().equals(Typeface.SERIF)) {
                        typefaceSerifTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewButton.getViewTypeface().equals(Typeface.SANS_SERIF)) {
                        typefaceSanSerifTextView.setTextColor(Color.parseColor("#24aaf2"));
                    }
                    if (previewButton.getViewTypefaceStyle() == Typeface.NORMAL) {
                        emphasisNormalTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewButton.getViewTypefaceStyle() == Typeface.BOLD) {
                        emphasisBoldTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewButton.getViewTypefaceStyle() == Typeface.ITALIC) {
                        emphasisItalicTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewButton.getViewTypefaceStyle() == Typeface.BOLD_ITALIC) {
                        emphasisBoldItalicTextView.setTextColor(Color.parseColor("#24aaf2"));
                    }
                }
                if (viewTag == "EDITTEXT") {
                    previewEditText.initCopyView((mEditText) currentView, "set Text /preview");
                    previewEditText.setLayoutParams(previewLayoutParams);
                    viewPreviewFrame.addView(previewEditText);
                    if (previewEditText.getViewTypeface().equals(Typeface.DEFAULT)) {
                        typefaceNormalTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewEditText.getViewTypeface().equals(Typeface.MONOSPACE)) {
                        typefaceMonospaceTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewEditText.getViewTypeface().equals(Typeface.SERIF)) {
                        typefaceSerifTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewEditText.getViewTypeface().equals(Typeface.SANS_SERIF)) {
                        typefaceSanSerifTextView.setTextColor(Color.parseColor("#24aaf2"));
                    }
                    if (previewEditText.getViewTypefaceStyle() == Typeface.NORMAL) {
                        emphasisNormalTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewEditText.getViewTypefaceStyle() == Typeface.BOLD) {
                        emphasisBoldTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewEditText.getViewTypefaceStyle() == Typeface.ITALIC) {
                        emphasisItalicTextView.setTextColor(Color.parseColor("#24aaf2"));
                    } else if (previewEditText.getViewTypefaceStyle() == Typeface.BOLD_ITALIC) {
                        emphasisBoldItalicTextView.setTextColor(Color.parseColor("#24aaf2"));
                    }
                }

                emphasisButton.setBackgroundColor(Color.parseColor("#24aaf2"));
                textStyleButton.setBackgroundColor(Color.LTGRAY);

                emphasisButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emphasisLayout.setVisibility(View.VISIBLE);
                        typefaceLayout.setVisibility(View.INVISIBLE);
                        emphasisButton.setBackgroundColor(Color.parseColor("#24aaf2"));
                        textStyleButton.setBackgroundColor(Color.LTGRAY);
                    }
                });
                textStyleButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        typefaceLayout.setVisibility(View.VISIBLE);
                        emphasisLayout.setVisibility(View.INVISIBLE);
                        textStyleButton.setBackgroundColor(Color.parseColor("#24aaf2"));
                        emphasisButton.setBackgroundColor(Color.LTGRAY);

                    }
                });
                emphasisNormalTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emphasisNormalTextView.setTextColor(Color.parseColor("#24aaf2"));
                        emphasisBoldTextView.setTextColor(Color.LTGRAY);
                        emphasisItalicTextView.setTextColor(Color.LTGRAY);
                        emphasisBoldItalicTextView.setTextColor(Color.LTGRAY);
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewTypefaceStyle(Typeface.NORMAL);
                        if (viewTag == "BUTTON")
                            previewButton.setViewTypefaceStyle(Typeface.NORMAL);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setViewTypefaceStyle(Typeface.NORMAL);
                    }
                });
                emphasisBoldTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emphasisBoldTextView.setTextColor(Color.parseColor("#24aaf2"));
                        emphasisNormalTextView.setTextColor(Color.LTGRAY);
                        emphasisItalicTextView.setTextColor(Color.LTGRAY);
                        emphasisBoldItalicTextView.setTextColor(Color.LTGRAY);
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewTypefaceStyle(Typeface.BOLD);
                        if (viewTag == "BUTTON")
                            previewButton.setViewTypefaceStyle(Typeface.BOLD);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setViewTypefaceStyle(Typeface.BOLD);
                    }
                });
                emphasisItalicTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emphasisItalicTextView.setTextColor(Color.parseColor("#24aaf2"));
                        emphasisNormalTextView.setTextColor(Color.LTGRAY);
                        emphasisBoldTextView.setTextColor(Color.LTGRAY);
                        emphasisBoldItalicTextView.setTextColor(Color.LTGRAY);
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewTypefaceStyle(Typeface.ITALIC);
                        if (viewTag == "BUTTON")
                            previewButton.setViewTypefaceStyle(Typeface.ITALIC);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setViewTypefaceStyle(Typeface.ITALIC);
                    }
                });
                emphasisBoldItalicTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emphasisBoldItalicTextView.setTextColor(Color.parseColor("#24aaf2"));
                        emphasisNormalTextView.setTextColor(Color.LTGRAY);
                        emphasisBoldTextView.setTextColor(Color.LTGRAY);
                        emphasisItalicTextView.setTextColor(Color.LTGRAY);
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewTypefaceStyle(Typeface.BOLD_ITALIC);
                        if (viewTag == "BUTTON")
                            previewButton.setViewTypefaceStyle(Typeface.BOLD_ITALIC);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setViewTypefaceStyle(Typeface.BOLD_ITALIC);
                    }
                });
                typefaceNormalTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        typefaceNormalTextView.setTextColor(Color.parseColor("#24aaf2"));
                        typefaceMonospaceTextView.setTextColor(Color.LTGRAY);
                        typefaceSerifTextView.setTextColor(Color.LTGRAY);
                        typefaceSanSerifTextView.setTextColor(Color.LTGRAY);
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewTypeface(Typeface.DEFAULT);
                        if (viewTag == "BUTTON")
                            previewButton.setViewTypeface(Typeface.DEFAULT);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setViewTypeface(Typeface.DEFAULT);
                    }
                });
                typefaceMonospaceTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        typefaceMonospaceTextView.setTextColor(Color.parseColor("#24aaf2"));
                        typefaceNormalTextView.setTextColor(Color.LTGRAY);
                        typefaceSerifTextView.setTextColor(Color.LTGRAY);
                        typefaceSanSerifTextView.setTextColor(Color.LTGRAY);
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewTypeface(Typeface.MONOSPACE);
                        if (viewTag == "BUTTON")
                            previewButton.setViewTypeface(Typeface.MONOSPACE);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setViewTypeface(Typeface.MONOSPACE);
                    }
                });
                typefaceSerifTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        typefaceSerifTextView.setTextColor(Color.parseColor("#24aaf2"));
                        typefaceNormalTextView.setTextColor(Color.LTGRAY);
                        typefaceMonospaceTextView.setTextColor(Color.LTGRAY);
                        typefaceSanSerifTextView.setTextColor(Color.LTGRAY);
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewTypeface(Typeface.SERIF);
                        if (viewTag == "BUTTON")
                            previewButton.setViewTypeface(Typeface.SERIF);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setViewTypeface(Typeface.SERIF);
                    }
                });
                typefaceSanSerifTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        typefaceSanSerifTextView.setTextColor(Color.parseColor("#24aaf2"));
                        typefaceNormalTextView.setTextColor(Color.LTGRAY);
                        typefaceMonospaceTextView.setTextColor(Color.LTGRAY);
                        typefaceSerifTextView.setTextColor(Color.LTGRAY);
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewTypeface(Typeface.SANS_SERIF);
                        if (viewTag == "BUTTON")
                            previewButton.setViewTypeface(Typeface.SANS_SERIF);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setViewTypeface(Typeface.SANS_SERIF);
                    }
                });

                new AlertDialogWrapper.Builder(this)
                        .setTitle("Text Style")
                        .setView(textStyleEntryView)
                        .setPositiveButton("Set Style", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (viewTag == "TEXTVIEW") {
                                    mTextView tv = (mTextView) currentView;
                                    tv.setViewTypeface(previewTextView.getViewTypeface());
                                    tv.setViewTypefaceStyle(previewTextView.getViewTypefaceStyle());
                                }
                                if (viewTag == "BUTTON") {
                                    mButton b = (mButton) currentView;
                                    b.setViewTypeface(previewButton.getViewTypeface());
                                    b.setViewTypefaceStyle(previewButton.getViewTypefaceStyle());
                                }
                                if (viewTag == "EDITTEXT") {
                                    mEditText et = (mEditText) currentView;
                                    et.setViewTypeface(previewEditText.getViewTypeface());
                                    et.setViewTypefaceStyle(previewEditText.getViewTypefaceStyle());
                                }
                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
                return null;

            /*
            Dialog responsible for setting a views text size
             */
            case SET_TEXT_SIZE:
                LayoutInflater size = LayoutInflater.from(this);
                final View sizeEntryView = size.inflate(R.layout.setsizelayout, null);
                final Slider setSizeSlider = (Slider) sizeEntryView.findViewById(R.id.slider);
                FrameLayout sizePreviewFrame = (FrameLayout) sizeEntryView.findViewById(R.id.selectedSizeViewDisplay);
                final TextView sizeCounter = (TextView) sizeEntryView.findViewById(R.id.setSizeNumberCounter);
                if (viewTag == "TEXTVIEW") {
                    previewTextView.initCopyView((mTextView) currentView, "set Text /preview");
                    previewTextView.setLayoutParams(previewLayoutParams);
                    sizePreviewFrame.addView(previewTextView);
                    textViewPrefs = new TextViewPreferences(this);
                    setSizeSlider.setMax(textViewPrefs.getMaxTextViewTextSize());
                    setSizeSlider.setValue((int) previewTextView.getViewTextSize());
                    sizeCounter.setText(previewTextView.getViewTextSize() + "sp");
                }
                if (viewTag == "BUTTON") {
                    previewButton.initCopyView((mButton) currentView, "set Text /preview");
                    previewButton.setLayoutParams(previewLayoutParams);
                    sizePreviewFrame.addView(previewButton);
                    buttonPrefs = new ButtonPreferences(this);
                    setSizeSlider.setMax(buttonPrefs.getMaxButtonTextSize());
                    setSizeSlider.setValue((int) previewButton.getViewTextSize());
                    sizeCounter.setText(previewButton.getViewTextSize() + "sp");
                }
                if (viewTag == "EDITTEXT") {
                    previewEditText.initCopyView((mEditText) currentView, "set Text /preview");
                    previewEditText.setLayoutParams(previewLayoutParams);
                    sizePreviewFrame.addView(previewEditText);
                    editTextPrefs = new EditTextPreferences(this);
                    setSizeSlider.setMax(editTextPrefs.getMaxEditTextTextSize());
                    setSizeSlider.setValue((int) previewEditText.getViewTextSize());
                    sizeCounter.setText(previewEditText.getViewTextSize() + "sp");
                }

                setSizeSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        sizeCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewTextSize((float) i);
                        if (viewTag == "BUTTON")
                            previewButton.setViewTextSize((float) i);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setViewTextSize((float) i);
                    }
                });
                new AlertDialogWrapper.Builder(this)
                        .setTitle("Set Text Size")
                        .setView(sizeEntryView)
                        .setPositiveButton("Set Text Size", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if (viewTag == "TEXTVIEW") {
                                    mTextView v = (mTextView) currentView;
                                    v.setViewTextSize((float) setSizeSlider.getValue());
                                } else if (viewTag == "BUTTON") {
                                    mButton b = (mButton) currentView;
                                    b.setViewTextSize((float) setSizeSlider.getValue());
                                } else if (viewTag == "EDITTEXT") {
                                    mEditText et = (mEditText) currentView;
                                    et.setViewTextSize((float) setSizeSlider.getValue());
                                }
                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }

                        })
                        .show();
                return null;

            /*
            Dialog responsible for setting a views height and width.
             */
            case SET_VIEW_SIZE:
                LayoutInflater viewSize = LayoutInflater.from(this);
                final View viewSizeEntryView = viewSize.inflate(R.layout.setviewsizelayout, null);
                final Slider setWidthSizeSlider = (Slider) viewSizeEntryView.findViewById(R.id.widthSlider);
                final Slider setHeightSizeSlider = (Slider) viewSizeEntryView.findViewById(R.id.heightSlider);
                FrameLayout viewSizePreviewFrame = (FrameLayout) viewSizeEntryView.findViewById(R.id.selectedViewSizeViewDisplay);
                final TextView widthCounter = (TextView) viewSizeEntryView.findViewById(R.id.widthCounter);
                final TextView heightCounter = (TextView) viewSizeEntryView.findViewById(R.id.heightCounter);

                if (viewTag == "TEXTVIEW") {
                    previewTextView.initCopyView((mTextView) currentView, "set Text /preview");
                    previewTextView.setLayoutParams(previewLayoutParams);
                    viewSizePreviewFrame.addView(previewTextView);
                    textViewPrefs = new TextViewPreferences(this);
                    setWidthSizeSlider.setMax(textViewPrefs.getMaxWidth());
                    setHeightSizeSlider.setMax(textViewPrefs.getMaxHeight());
                    setWidthSizeSlider.setValue(previewTextView.getViewSize("width"));
                    setHeightSizeSlider.setValue(previewTextView.getViewSize("height"));
                    widthCounter.setText(previewTextView.getViewSize("width") + "dp");
                    heightCounter.setText(previewTextView.getViewSize("height") + "dp");
                }
                if (viewTag == "BUTTON") {
                    previewButton.initCopyView((mButton) currentView, "set Text /preview");
                    previewButton.setLayoutParams(previewLayoutParams);
                    viewSizePreviewFrame.addView(previewButton);
                    buttonPrefs = new ButtonPreferences(this);
                    setWidthSizeSlider.setMax(buttonPrefs.getMaxWidth());
                    setHeightSizeSlider.setMax(buttonPrefs.getMaxHeight());
                    setWidthSizeSlider.setValue(previewButton.getViewSize("width"));
                    setHeightSizeSlider.setValue(previewButton.getViewSize("height"));
                    widthCounter.setText(previewButton.getViewSize("width") + "dp");
                    heightCounter.setText(previewButton.getViewSize("height") + "dp");
                }
                if (viewTag == "EDITTEXT") {
                    previewEditText.initCopyView((mEditText) currentView, "set Text /preview");
                    previewEditText.setLayoutParams(previewLayoutParams);
                    viewSizePreviewFrame.addView(previewEditText);
                    editTextPrefs = new EditTextPreferences(this);
                    setWidthSizeSlider.setMax(editTextPrefs.getMaxWidth());
                    setHeightSizeSlider.setMax(editTextPrefs.getMaxHeight());
                    setWidthSizeSlider.setValue(previewEditText.getViewSize("width"));
                    setHeightSizeSlider.setValue(previewEditText.getViewSize("height"));
                    widthCounter.setText(previewEditText.getViewSize("width") + "dp");
                    heightCounter.setText(previewEditText.getViewSize("height") + "dp");
                }
                if (viewTag == "IMAGEVIEW") {
                    previewImageView.initCopyView((mImageView) currentView, "set Text /preview");
                    previewImageView.setLayoutParams(previewLayoutParams);
                    viewSizePreviewFrame.addView(previewImageView);
                    imageViewPrefs = new ImageViewPreferences(this);
                    setWidthSizeSlider.setMax(imageViewPrefs.getMaxWidth());
                    setHeightSizeSlider.setMax(imageViewPrefs.getMaxHeight());
                    setWidthSizeSlider.setValue(previewImageView.getViewSize("width"));
                    setHeightSizeSlider.setValue(previewImageView.getViewSize("height"));
                    widthCounter.setText(previewImageView.getViewSize("width") + "dp");
                    heightCounter.setText(previewImageView.getViewSize("height") + "dp");
                }
                if (viewTag == "RELATIVE_LAYOUT") {
                    previewRelativeLayout.initCopyView((mRelativeLayout) currentView, "non base");
                    //previewImageView.setLayoutParams(previewLayoutParams);
                    viewSizePreviewFrame.addView(previewRelativeLayout);
                    relativeLayoutPrefs = new RelativeLayoutPreferences(this);
                    setWidthSizeSlider.setMax(relativeLayoutPrefs.getMaxWidth());
                    setHeightSizeSlider.setMax(relativeLayoutPrefs.getMaxHeight());

                    setWidthSizeSlider.setValue(previewRelativeLayout.getViewSize("width"));
                    setHeightSizeSlider.setValue(previewRelativeLayout.getViewSize("height"));
                    widthCounter.setText(previewRelativeLayout.getViewSize("width") + "dp");
                    heightCounter.setText(previewRelativeLayout.getViewSize("height") + "dp");
                }


                setWidthSizeSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        widthCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setPreviewViewSize(i, previewTextView.getViewSize("height"), "width");
                        if (viewTag == "BUTTON")
                            previewButton.setPreviewViewSize(i, previewButton.getViewSize("height"), "width");
                        if (viewTag == "EDITTEXT")
                            previewEditText.setPreviewViewSize(i, previewEditText.getViewSize("height"), "width");
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setPreviewViewSize(i, previewImageView.getViewSize("height"), "width");
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewRelativeLayout.setPreviewViewSize(i, previewRelativeLayout.getViewSize("height"), "width");
                    }
                });
                setHeightSizeSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        heightCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setPreviewViewSize(previewTextView.getViewSize("width"), i, "height");
                        if (viewTag == "BUTTON")
                            previewButton.setPreviewViewSize(previewButton.getViewSize("width"), i, "height");
                        if (viewTag == "EDITTEXT")
                            previewEditText.setPreviewViewSize(previewEditText.getViewSize("width"), i, "height");
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setPreviewViewSize(previewImageView.getViewSize("width"), i, "height");
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewRelativeLayout.setPreviewViewSize(i, previewRelativeLayout.getViewSize("width"), "height");
                    }
                });
                new AlertDialogWrapper.Builder(this)
                        .setTitle("Set View Size")
                        .setView(viewSizeEntryView)
                        .setPositiveButton("Set View Size", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if (viewTag == "TEXTVIEW") {
                                    mTextView v = (mTextView) currentView;
                                    v.setViewSize(setWidthSizeSlider.getValue(), setHeightSizeSlider.getValue(), "width and height");
                                } else if (viewTag == "BUTTON") {
                                    mButton b = (mButton) currentView;
                                    b.setViewSize(setWidthSizeSlider.getValue(), setHeightSizeSlider.getValue(), "width and height");
                                } else if (viewTag == "EDITTEXT") {
                                    mEditText et = (mEditText) currentView;
                                    et.setViewSize(setWidthSizeSlider.getValue(), setHeightSizeSlider.getValue(), "width and height");
                                } else if (viewTag == "IMAGEVIEW") {
                                    mImageView iv = (mImageView) currentView;
                                    iv.setViewSize(setWidthSizeSlider.getValue(), setHeightSizeSlider.getValue(), "width and height");
                                } else if (viewTag == "RELATIVE_LAYOUT") {
                                    mRelativeLayout tempRL = (mRelativeLayout) currentView;
                                    tempRL.setViewSize(setWidthSizeSlider.getValue(), setHeightSizeSlider.getValue(), "width and height");
                                }
                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return null;

            /*
            Dialog responsible for setting a views text or background color
             */
            case COLOR:
                LayoutInflater colorLayout = LayoutInflater.from(this);
                final View colorDialogLayout = colorLayout.inflate(R.layout.setcolorlayout, null);
                final ColorPickerView colorPicker = (ColorPickerView) colorDialogLayout.findViewById(R.id.colorPicker);
                colorPicker.showAlpha(true);
                colorPicker.showHex(true);
                final EditText hexValueText = colorPicker.getHexEdit();

                final ButtonFlat FontColorSelection = (ButtonFlat) colorDialogLayout.findViewById(R.id.FontColorButton);
                final ButtonFlat BackgroundColorSelection = (ButtonFlat) colorDialogLayout.findViewById(R.id.BackgroundColorButton);
                FontColorSelection.setBackgroundColor(Color.parseColor("#24aaf2"));
                BackgroundColorSelection.setBackgroundColor(Color.LTGRAY);

                final FrameLayout colorPreview = (FrameLayout) colorDialogLayout.findViewById(R.id.selectedColorViewDisplay);
                if (viewTag == "TEXTVIEW") {
                    previewTextView.initCopyView((mTextView) currentView, "set Text /preview");
                    previewTextView.setLayoutParams(previewLayoutParams);
                    colorPreview.addView(previewTextView);
                    colorPicker.setColor(previewTextView.getViewColor("text"));
                    //colorPicker.setColor(previewTextView.getViewColor("background"));
                    colorSelection = 1;
                }
                if (viewTag == "BUTTON") {
                    previewButton.initCopyView((mButton) currentView, "set Text /preview");
                    previewButton.setLayoutParams(previewLayoutParams);
                    colorPreview.addView(previewButton);
                    colorPicker.setColor(previewButton.getViewColor("text"));
                    colorSelection = 1;
                }
                if (viewTag == "EDITTEXT") {
                    previewEditText.initCopyView((mEditText) currentView, "set Text /preview");
                    previewEditText.setLayoutParams(previewLayoutParams);
                    colorPreview.addView(previewEditText);
                    FontColorSelection.setText("Hint Color");
                    BackgroundColorSelection.setText("Underline Color");
                    colorPicker.setColor(previewEditText.getViewColor("hint"));
                    colorSelection = 1;
                }
                if (viewTag == "BASE_LAYOUT") {
                    previewTextView.setViewText("Base Layout Preview");
                    RelativeLayout.LayoutParams layoutRelativePreview = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutRelativePreview.addRule(RelativeLayout.CENTER_IN_PARENT);
                    previewTextView.setLayoutParams(layoutRelativePreview);
                    colorPreview.addView(previewTextView);
                    colorPicker.setColor(baseLayout.getViewColor("background"));
                    FontColorSelection.setVisibility(View.INVISIBLE);
                    BackgroundColorSelection.setVisibility(View.INVISIBLE);
                    colorSelection = 0;
                }
                if (viewTag == "RELATIVE_LAYOUT") {
                    previewTextView.setViewText("Relative Layout Preview");
                    previewRelativeLayout.initCopyView((mRelativeLayout) currentView, "non base");
                    RelativeLayout.LayoutParams layoutRelativePreview = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutRelativePreview.addRule(RelativeLayout.CENTER_IN_PARENT);
                    previewTextView.setLayoutParams(layoutRelativePreview);
                    previewTextView.setViewColor(previewRelativeLayout.getViewColor("background"), "text", previewRelativeLayout.getColorHexValue("background"));
                    colorPreview.addView(previewTextView);
                    colorPicker.setColor(previewRelativeLayout.getViewColor("background"));
                    FontColorSelection.setVisibility(View.INVISIBLE);
                    BackgroundColorSelection.setVisibility(View.INVISIBLE);
                    colorSelection = 0;
                }

                FontColorSelection.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        colorSelection = 1;
                        FontColorSelection.setBackgroundColor(Color.parseColor("#24aaf2"));
                        BackgroundColorSelection.setBackgroundColor(Color.LTGRAY);
                        if (viewTag == "TEXTVIEW") {
                            colorPicker.setColor(previewTextView.getViewColor("text"));
                        }
                        if (viewTag == "BUTTON") {
                            colorPicker.setColor(previewButton.getViewColor("text"));
                        }
                        if (viewTag == "EDITTEXT") {
                            colorPicker.setColor(previewEditText.getViewColor("hint"));
                        }
                    }
                });
                BackgroundColorSelection.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        colorSelection = 0;
                        FontColorSelection.setBackgroundColor(Color.LTGRAY);
                        BackgroundColorSelection.setBackgroundColor(Color.parseColor("#24aaf2"));
                        if (viewTag == "TEXTVIEW") {
                            colorPicker.setColor(previewTextView.getViewColor("background"));
                        }
                        if (viewTag == "BUTTON") {
                            colorPicker.setColor(previewButton.getViewColor("background"));
                        }
                        if (viewTag == "EDITTEXT") {
                            colorPicker.setColor(previewEditText.getViewColor("background"));
                        }
                    }
                });

                // Update color picker in real time based on current hex input
                hexValueText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (colorSelection == 0) {
                            if (viewTag == "TEXTVIEW")
                                previewTextView.setViewColor(colorPicker.getColor(), "background", hexValueText.getText().toString());
                            if (viewTag == "BUTTON")
                                previewButton.setViewColor(colorPicker.getColor(), "background", hexValueText.getText().toString());
                            if (viewTag == "EDITTEXT")
                                previewEditText.setViewColor(colorPicker.getColor(), "background", hexValueText.getText().toString());
                            if (viewTag == "BASE_LAYOUT") {
                                previewTextView.setViewColor(colorPicker.getColor(), "text", hexValueText.getText().toString());
                            }
                            if (viewTag == "RELATIVE_LAYOUT") {
                                previewTextView.setViewColor(colorPicker.getColor(), "text", hexValueText.getText().toString());
                            }
                        }
                        if (colorSelection == 1) {
                            if (viewTag == "TEXTVIEW")
                                previewTextView.setViewColor(colorPicker.getColor(), "text", hexValueText.getText().toString());
                            if (viewTag == "BUTTON")
                                previewButton.setViewColor(colorPicker.getColor(), "text", hexValueText.getText().toString());
                            if (viewTag == "EDITTEXT")
                                previewEditText.setViewColor(colorPicker.getColor(), "hint", hexValueText.getText().toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                updateTutorialState();

                new AlertDialogWrapper.Builder(this)
                        .setTitle("Color Control")
                        .setView(colorDialogLayout)
                        .setPositiveButton("Set Color", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stopCheckingColor();
                                if (viewTag == "TEXTVIEW") {
                                    mTextView tv = (mTextView) currentView;
                                    tv.setViewColor(previewTextView.getViewColor("background"), "background", previewTextView.getColorHexValue("background"));
                                    tv.setViewColor(previewTextView.getViewColor("text"), "text", previewTextView.getColorHexValue("text"));
                                }
                                if (viewTag == "BUTTON") {
                                    mButton b = (mButton) currentView;
                                    b.setViewColor(previewButton.getViewColor("background"), "background", previewButton.getColorHexValue("background"));
                                    b.setViewColor(previewButton.getViewColor("text"), "text", previewButton.getColorHexValue("text"));
                                }
                                if (viewTag == "EDITTEXT") {
                                    mEditText et = (mEditText) currentView;
                                    et.setViewColor(previewEditText.getViewColor("background"), "background", previewEditText.getColorHexValue("background"));
                                    et.setViewColor(previewEditText.getViewColor("hint"), "hint", previewEditText.getColorHexValue("hint"));
                                }
                                if (viewTag == "BASE_LAYOUT") {
                                    baseLayout.setViewColor(colorPicker.getColor(), "background", previewTextView.getColorHexValue("text"));
                                    menuGraphics = getResources().obtainTypedArray(R.array.baseLayoutIconItems);
                                    reDeclareLeftMenuItems();
                                }
                                if (viewTag == "RELATIVE_LAYOUT") {
                                    mRelativeLayout rl = (mRelativeLayout) currentView;
                                    if(rl.hasBackgroundImage()) {
                                        menuGraphics = getResources().obtainTypedArray(R.array.relativeLayoutIconItems);
                                    }
                                    rl.setViewColor(colorPicker.getColor(), "background", previewTextView.getColorHexValue("text"));
                                    reDeclareLeftMenuItems();
                                }

                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return null;

            /*
            Dialog responsible for managing the tint on view backgrounds
             */
            case SET_BACKGROUND_TINT:
                /*
                Inflate Layout and initialize control system UI components
                 */
                LayoutInflater colorTintLayout = LayoutInflater.from(this);
                final View colorTintDialogLayout = colorTintLayout.inflate(R.layout.settintlayout, null);
                final ColorPickerView colorTintPicker = (ColorPickerView) colorTintDialogLayout.findViewById(R.id.colorTintPicker);
                colorTintPicker.showAlpha(true);
                colorTintPicker.showHex(true);
                final EditText hexTintValueText = colorTintPicker.getHexEdit();
                final FrameLayout colorTintPreview = (FrameLayout) colorTintDialogLayout.findViewById(R.id.selectedColorTintViewDisplay);

                /*
                Determine what type of view is currently selected and then copy over selected view attributes to preview view. Also set
                color control system color.
                 */

                if (viewTag == "IMAGEVIEW") {
                    RelativeLayout.LayoutParams layoutRelativePreview = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutRelativePreview.addRule(RelativeLayout.CENTER_IN_PARENT);
                    previewImageView.initCopyView((mImageView) currentView, "set Text /preview");
                    previewImageView.setLayoutParams(layoutRelativePreview);
                    colorTintPreview.addView(previewImageView);
                    colorTintPicker.setColor(previewImageView.getViewTintColor());
                }
                if (viewTag == "BASE_LAYOUT") {
                    previewTextView.setViewText("Base Layout Preview");
                    RelativeLayout.LayoutParams layoutRelativePreview = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutRelativePreview.addRule(RelativeLayout.CENTER_IN_PARENT);
                    previewTextView.setLayoutParams(layoutRelativePreview);
                    colorTintPreview.addView(previewTextView);
                    colorTintPicker.setColor(baseLayout.getViewTintColor());
                }
                if (viewTag == "RELATIVE_LAYOUT") {
                    previewRelativeLayout.initCopyView((mRelativeLayout) currentView, "non base");
                    previewTextView.setViewText("Relative Layout Preview");
                    RelativeLayout.LayoutParams layoutRelativePreview = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutRelativePreview.addRule(RelativeLayout.CENTER_IN_PARENT);
                    previewTextView.setLayoutParams(layoutRelativePreview);
                    colorTintPreview.addView(previewTextView);
                    colorTintPicker.setColor(previewRelativeLayout.getViewTintColor());
                }

                // Update color picker as user types new hex values
                hexTintValueText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setViewTintColor(colorTintPicker.getColor(), hexTintValueText.getText().toString());
                        if (viewTag == "BASE_LAYOUT")
                           previewTextView.setViewTintColor(colorTintPicker.getColor(), hexTintValueText.getText().toString());
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewTextView.setViewTintColor(colorTintPicker.getColor(), hexTintValueText.getText().toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                updateTutorialState();

                new AlertDialogWrapper.Builder(this)
                        .setTitle("Tint Overlay Control")
                        .setView(colorTintDialogLayout)
                        .setPositiveButton("Set Tint Overlay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (viewTag == "IMAGEVIEW") {
                                    mImageView iv = (mImageView) currentView;
                                    iv.setViewTintColor(colorTintPicker.getColor(), previewImageView.getTintHexColor());
                                }
                                if (viewTag == "BASE_LAYOUT")
                                    baseLayout.setViewTintColor(colorTintPicker.getColor(), previewTextView.getTintHexValue());
                                if (viewTag == "RELATIVE_LAYOUT") {
                                    mRelativeLayout tempRL = (mRelativeLayout) currentView;
                                    tempRL.setViewTintColor(colorTintPicker.getColor(), previewTextView.getTintHexValue());
                                }

                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return null;

            /*
            Dialog responsible for controlling a views padding values.
             */
            case SET_PADDING:
                LayoutInflater padding = LayoutInflater.from(this);
                final View paddingEntryView = padding.inflate(R.layout.setpaddinglayout, null);
                final Slider leftPaddingSlider = (Slider) paddingEntryView.findViewById(R.id.leftPaddingSlider);
                final Slider rightPaddingSlider = (Slider) paddingEntryView.findViewById(R.id.rightPaddingSlider);
                final Slider topPaddingSlider = (Slider) paddingEntryView.findViewById(R.id.topPaddingSlider);
                final Slider bottomPaddingSlider = (Slider) paddingEntryView.findViewById(R.id.bottomPaddingSlider);

                final FrameLayout paddingPreviewFrame = (FrameLayout) paddingEntryView.findViewById(R.id.selectedPaddingViewDisplay);
                paddingPreviewFrame.setBackgroundColor(Color.BLACK);

                final TextView leftPaddingCounter = (TextView) paddingEntryView.findViewById(R.id.leftPaddingCounter);
                final TextView rightPaddingCounter = (TextView) paddingEntryView.findViewById(R.id.rightPaddingCounter);
                final TextView topPaddingCounter = (TextView) paddingEntryView.findViewById(R.id.topPaddingCounter);
                final TextView bottomPaddingCounter = (TextView) paddingEntryView.findViewById(R.id.bottomPaddingCounter);

                if (viewTag == "TEXTVIEW") {
                    previewTextView.initCopyView((mTextView) currentView, "set Text /preview");
                    paddingPreviewFrame.addView(previewTextView);
                    textViewPrefs = new TextViewPreferences(this);
                    leftPaddingSlider.setMax(textViewPrefs.getMaxTextViewLeftPadding());
                    rightPaddingSlider.setMax(textViewPrefs.getMaxTextViewRightPadding());
                    topPaddingSlider.setMax(textViewPrefs.getMaxTextViewTopPadding());
                    bottomPaddingSlider.setMax(textViewPrefs.getMaxTextViewBottomPadding());

                    leftPaddingSlider.setValue(previewTextView.getViewPadding("left"));
                    rightPaddingSlider.setValue(previewTextView.getViewPadding("right"));
                    topPaddingSlider.setValue(previewTextView.getViewPadding("top"));
                    bottomPaddingSlider.setValue(previewTextView.getViewPadding("bottom"));

                    leftPaddingCounter.setText(previewTextView.getViewPadding("left") + "dp");
                    rightPaddingCounter.setText(previewTextView.getViewPadding("right") + "dp");
                    topPaddingCounter.setText(previewTextView.getViewPadding("top") + "dp");
                    bottomPaddingCounter.setText(previewTextView.getViewPadding("bottom") + "dp");
                }
                if (viewTag == "BUTTON") {
                    previewButton.initCopyView((mButton) currentView, "set Text /preview");
                    paddingPreviewFrame.addView(previewButton);
                    buttonPrefs = new ButtonPreferences(this);
                    leftPaddingSlider.setMax(buttonPrefs.getMaxButtonLeftPadding());
                    rightPaddingSlider.setMax(buttonPrefs.getMaxButtonRightPadding());
                    topPaddingSlider.setMax(buttonPrefs.getMaxButtonTopPadding());
                    bottomPaddingSlider.setMax(buttonPrefs.getMaxButtonBottomPadding());

                    leftPaddingSlider.setValue(previewButton.getViewPadding("left"));
                    rightPaddingSlider.setValue(previewButton.getViewPadding("right"));
                    topPaddingSlider.setValue(previewButton.getViewPadding("top"));
                    bottomPaddingSlider.setValue(previewButton.getViewPadding("bottom"));

                    leftPaddingCounter.setText(previewButton.getViewPadding("left") + "dp");
                    rightPaddingCounter.setText(previewButton.getViewPadding("right") + "dp");
                    topPaddingCounter.setText(previewButton.getViewPadding("top") + "dp");
                    bottomPaddingCounter.setText(previewButton.getViewPadding("bottom") + "dp");
                }
                if (viewTag == "EDITTEXT") {
                    previewEditText.initCopyView((mEditText) currentView, "set Text /preview");
                    paddingPreviewFrame.addView(previewEditText);

                    editTextPrefs = new EditTextPreferences(this);
                    leftPaddingSlider.setMax(editTextPrefs.getMaxEditTextLeftPadding());
                    rightPaddingSlider.setMax(editTextPrefs.getMaxEditTextRightPadding());
                    topPaddingSlider.setMax(editTextPrefs.getMaxEditTextTopPadding());
                    bottomPaddingSlider.setMax(editTextPrefs.getMaxEditTextBottomPadding());

                    leftPaddingSlider.setValue(previewEditText.getViewPadding("left"));
                    rightPaddingSlider.setValue(previewEditText.getViewPadding("right"));
                    topPaddingSlider.setValue(previewEditText.getViewPadding("top"));
                    bottomPaddingSlider.setValue(previewEditText.getViewPadding("bottom"));

                    leftPaddingCounter.setText(previewEditText.getViewPadding("left") + "dp");
                    rightPaddingCounter.setText(previewEditText.getViewPadding("right") + "dp");
                    topPaddingCounter.setText(previewEditText.getViewPadding("top") + "dp");
                    bottomPaddingCounter.setText(previewEditText.getViewPadding("bottom") + "dp");
                }
                if (viewTag == "IMAGEVIEW") {
                    previewImageView.initCopyView((mImageView) currentView, "set Text /preview");
                    paddingPreviewFrame.addView(previewImageView);

                    imageViewPrefs = new ImageViewPreferences(this);
                    leftPaddingSlider.setMax(imageViewPrefs.getMaxImageViewLeftPadding());
                    rightPaddingSlider.setMax(imageViewPrefs.getMaxImageViewRightPadding());
                    topPaddingSlider.setMax(imageViewPrefs.getMaxImageViewTopPadding());
                    bottomPaddingSlider.setMax(imageViewPrefs.getMaxImageViewBottomPadding());

                    leftPaddingSlider.setValue(previewImageView.getViewPadding("left"));
                    rightPaddingSlider.setValue(previewImageView.getViewPadding("right"));
                    topPaddingSlider.setValue(previewImageView.getViewPadding("top"));
                    bottomPaddingSlider.setValue(previewImageView.getViewPadding("bottom"));

                    leftPaddingCounter.setText(previewImageView.getViewPadding("left") + "dp");
                    rightPaddingCounter.setText(previewImageView.getViewPadding("right") + "dp");
                    topPaddingCounter.setText(previewImageView.getViewPadding("top") + "dp");
                    bottomPaddingCounter.setText(previewImageView.getViewPadding("bottom") + "dp");
                }
                if (viewTag == "BASE_LAYOUT") {
                    previewTextView.setViewText("Base Layout Preview");
                    RelativeLayout.LayoutParams layoutRelativePreview = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutRelativePreview.addRule(RelativeLayout.CENTER_IN_PARENT);
                    previewTextView.setLayoutParams(layoutRelativePreview);
                    paddingPreviewFrame.addView(previewTextView);

                    relativeLayoutPrefs = new RelativeLayoutPreferences(this);
                    leftPaddingSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutLeftPadding());
                    rightPaddingSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutRightPadding());
                    topPaddingSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutTopPadding());
                    bottomPaddingSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutBottomPadding());

                    leftPaddingSlider.setValue(baseLayout.getViewPadding("left"));
                    rightPaddingSlider.setValue(baseLayout.getViewPadding("right"));
                    topPaddingSlider.setValue(baseLayout.getViewPadding("top"));
                    bottomPaddingSlider.setValue(baseLayout.getViewPadding("bottom"));

                    leftPaddingCounter.setText(baseLayout.getViewPadding("left") + "dp");
                    rightPaddingCounter.setText(baseLayout.getViewPadding("right") + "dp");
                    topPaddingCounter.setText(baseLayout.getViewPadding("top") + "dp");
                    bottomPaddingCounter.setText(baseLayout.getViewPadding("bottom") + "dp");
                }
                if (viewTag == "RELATIVE_LAYOUT") {
                    previewTextView.setViewText("Relative Layout Preview");
                    previewRelativeLayout.initCopyView((mRelativeLayout) currentView, "non base");
                    RelativeLayout.LayoutParams layoutRelativePreview = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutRelativePreview.addRule(RelativeLayout.CENTER_IN_PARENT);
                    previewTextView.setLayoutParams(layoutRelativePreview);
                    paddingPreviewFrame.addView(previewTextView);

                    relativeLayoutPrefs = new RelativeLayoutPreferences(this);
                    leftPaddingSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutLeftPadding());
                    rightPaddingSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutRightPadding());
                    topPaddingSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutTopPadding());
                    bottomPaddingSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutBottomPadding());


                    leftPaddingSlider.setValue(previewRelativeLayout.getViewPadding("left"));
                    rightPaddingSlider.setValue(previewRelativeLayout.getViewPadding("right"));
                    topPaddingSlider.setValue(previewRelativeLayout.getViewPadding("top"));
                    bottomPaddingSlider.setValue(previewRelativeLayout.getViewPadding("bottom"));

                    leftPaddingCounter.setText(previewRelativeLayout.getViewPadding("left") + "dp");
                    rightPaddingCounter.setText(previewRelativeLayout.getViewPadding("right") + "dp");
                    topPaddingCounter.setText(previewRelativeLayout.getViewPadding("top") + "dp");
                    bottomPaddingCounter.setText(previewRelativeLayout.getViewPadding("bottom") + "dp");
                }

                leftPaddingSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        leftPaddingCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setPreviewPadding("left", i);
                        if (viewTag == "BUTTON")
                            previewButton.setPreviewPadding("left", i);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setPreviewPadding("left", i);
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setPreviewPadding("left", i);
                        if (viewTag == "BASE_LAYOUT")
                            previewTextView.setPreviewPadding("left", i);
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewTextView.setPreviewPadding("left", i);
                    }
                });
                rightPaddingSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        rightPaddingCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setPreviewPadding("right", i);
                        if (viewTag == "BUTTON")
                            previewButton.setPreviewPadding("right", i);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setPreviewPadding("right", i);
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setPreviewPadding("right", i);
                        if (viewTag == "BASE_LAYOUT")
                            previewTextView.setPreviewPadding("right", i);
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewTextView.setPreviewPadding("right", i);
                    }
                });
                topPaddingSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        topPaddingCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setPreviewPadding("top", i);
                        if (viewTag == "BUTTON")
                            previewButton.setPreviewPadding("top", i);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setPreviewPadding("top", i);
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setPreviewPadding("top", i);
                        if (viewTag == "BASE_LAYOUT")
                            previewTextView.setPreviewPadding("top", i);
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewTextView.setPreviewPadding("top", i);
                    }
                });
                bottomPaddingSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        bottomPaddingCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setPreviewPadding("bottom", i);
                        if (viewTag == "BUTTON")
                            previewButton.setPreviewPadding("bottom", i);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setPreviewPadding("bottom", i);
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setPreviewPadding("bottom", i);
                        if (viewTag == "BASE_LAYOUT")
                            previewTextView.setPreviewPadding("bottom", i);
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewTextView.setPreviewPadding("bottom", i);
                    }
                });
                updateTutorialState();

                new AlertDialogWrapper.Builder(this)
                        .setTitle("Padding")
                        .setView(paddingEntryView)
                        .setPositiveButton("Set Padding", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if (viewTag == "TEXTVIEW") {
                                    mTextView v = (mTextView) currentView;
                                    v.setViewPadding(leftPaddingSlider.getValue(), rightPaddingSlider.getValue(),
                                            topPaddingSlider.getValue(), bottomPaddingSlider.getValue());
                                } else if (viewTag == "BUTTON") {
                                    mButton b = (mButton) currentView;
                                    b.setViewPadding(leftPaddingSlider.getValue(), rightPaddingSlider.getValue(),
                                            topPaddingSlider.getValue(), bottomPaddingSlider.getValue());
                                } else if (viewTag == "EDITTEXT") {
                                    mEditText et = (mEditText) currentView;
                                    et.setViewPadding(leftPaddingSlider.getValue(), rightPaddingSlider.getValue(),
                                            topPaddingSlider.getValue(), bottomPaddingSlider.getValue());
                                } else if (viewTag == "IMAGEVIEW") {
                                    mImageView iv = (mImageView) currentView;
                                    iv.setViewPadding(leftPaddingSlider.getValue(), rightPaddingSlider.getValue(),
                                            topPaddingSlider.getValue(), bottomPaddingSlider.getValue());
                                } else if (viewTag == "BASE_LAYOUT") {
                                    baseLayout.setViewPadding(leftPaddingSlider.getValue(), rightPaddingSlider.getValue(),
                                            topPaddingSlider.getValue(), bottomPaddingSlider.getValue());
                                } else if (viewTag == "RELATTIVE_LAYOUT") {
                                    mRelativeLayout tempRL = (mRelativeLayout) currentView;
                                    tempRL.setViewPadding(leftPaddingSlider.getValue(), rightPaddingSlider.getValue(),
                                            topPaddingSlider.getValue(), bottomPaddingSlider.getValue());
                                    paddingPreviewFrame.removeAllViews();
                                }
                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return null;

            /*
            Dialog responsible for managing a view's margins.
             */
            case MARGINS_MENU:
                LayoutInflater margins = LayoutInflater.from(this);
                final View marginsEntryView = margins.inflate(R.layout.setmarginlayout, null);
                final Slider leftMarginSlider = (Slider) marginsEntryView.findViewById(R.id.leftMarginSlider);
                final Slider rightMarginSlider = (Slider) marginsEntryView.findViewById(R.id.rightMarginSlider);
                final Slider topMarginSlider = (Slider) marginsEntryView.findViewById(R.id.topMarginSlider);
                final Slider bottomMarginSlider = (Slider) marginsEntryView.findViewById(R.id.bottomMarginSlider);

                final TextView leftMarginCounter = (TextView) marginsEntryView.findViewById(R.id.leftMarginCounter);
                final TextView rightMarginCounter = (TextView) marginsEntryView.findViewById(R.id.rightMarginCounter);
                final TextView topMarginCounter = (TextView) marginsEntryView.findViewById(R.id.topMarginCounter);
                final TextView bottomMarginCounter = (TextView) marginsEntryView.findViewById(R.id.bottomMarginCounter);

                final FrameLayout marginPreviewFrame = (FrameLayout) marginsEntryView.findViewById(R.id.selectedMarginViewDisplay);
                marginPreviewFrame.setBackgroundColor(Color.BLACK);
                if (viewTag == "TEXTVIEW") {
                    previewTextView.initCopyView((mTextView) currentView, "set Text /preview");
                    marginPreviewFrame.addView(previewTextView);
                    textViewPrefs = new TextViewPreferences(this);
                    leftMarginSlider.setMax(textViewPrefs.getMaxTextViewLeftMargin());
                    rightMarginSlider.setMax(textViewPrefs.getMaxTextViewRightMargin());
                    topMarginSlider.setMax(textViewPrefs.getMaxTextViewTopMargin());
                    bottomMarginSlider.setMax(textViewPrefs.getMaxTextViewBottomMargin());

                    leftMarginSlider.setValue(previewTextView.getViewMargins("left"));
                    rightMarginSlider.setValue(previewTextView.getViewMargins("right"));
                    topMarginSlider.setValue(previewTextView.getViewMargins("top"));
                    bottomMarginSlider.setValue(previewTextView.getViewMargins("bottom"));

                    leftMarginCounter.setText(previewTextView.getViewMargins("left") + "dp");
                    rightMarginCounter.setText(previewTextView.getViewMargins("right") + "dp");
                    topMarginCounter.setText(previewTextView.getViewMargins("top") + "dp");
                    bottomMarginCounter.setText(previewTextView.getViewMargins("bottom") + "dp");
                }
                if (viewTag == "BUTTON") {
                    previewButton.initCopyView((mButton) currentView, "set Text /preview");
                    marginPreviewFrame.addView(previewButton);
                    buttonPrefs = new ButtonPreferences(this);
                    leftMarginSlider.setMax(buttonPrefs.getMaxButtonLeftMargin());
                    rightMarginSlider.setMax(buttonPrefs.getMaxButtonRightMargin());
                    topMarginSlider.setMax(buttonPrefs.getMaxButtonTopMargin());
                    bottomMarginSlider.setMax(buttonPrefs.getMaxButtonBottomMargin());

                    leftMarginSlider.setValue(previewButton.getViewMargins("left"));
                    rightMarginSlider.setValue(previewButton.getViewMargins("right"));
                    topMarginSlider.setValue(previewButton.getViewMargins("top"));
                    bottomMarginSlider.setValue(previewButton.getViewMargins("bottom"));

                    leftMarginCounter.setText(previewButton.getViewMargins("left") + "dp");
                    rightMarginCounter.setText(previewButton.getViewMargins("right") + "dp");
                    topMarginCounter.setText(previewButton.getViewMargins("top") + "dp");
                    bottomMarginCounter.setText(previewButton.getViewMargins("bottom") + "dp");

                }
                if (viewTag == "EDITTEXT") {
                    previewEditText.initCopyView((mEditText) currentView, "set Text /preview");
                    marginPreviewFrame.addView(previewEditText);
                    editTextPrefs = new EditTextPreferences(this);
                    leftMarginSlider.setMax(editTextPrefs.getMaxEditTextLeftMargin());
                    rightMarginSlider.setMax(editTextPrefs.getMaxEditTextRightMargin());
                    topMarginSlider.setMax(editTextPrefs.getMaxEditTextTopMargin());
                    bottomMarginSlider.setMax(editTextPrefs.getMaxEditTextBottomMargin());

                    leftMarginSlider.setValue(previewEditText.getViewMargins("left"));
                    rightMarginSlider.setValue(previewEditText.getViewMargins("right"));
                    topMarginSlider.setValue(previewEditText.getViewMargins("top"));
                    bottomMarginSlider.setValue(previewEditText.getViewMargins("bottom"));

                    leftMarginCounter.setText(previewEditText.getViewMargins("left") + "dp");
                    rightMarginCounter.setText(previewEditText.getViewMargins("right") + "dp");
                    topMarginCounter.setText(previewEditText.getViewMargins("top") + "dp");
                    bottomMarginCounter.setText(previewEditText.getViewMargins("bottom") + "dp");
                }
                if (viewTag == "IMAGEVIEW") {
                    previewImageView.initCopyView((mImageView) currentView, "set Text /preview");
                    marginPreviewFrame.addView(previewImageView);
                    imageViewPrefs = new ImageViewPreferences(this);
                    leftMarginSlider.setMax(imageViewPrefs.getMaxImageViewLeftMargin());
                    rightMarginSlider.setMax(imageViewPrefs.getMaxImageViewRightMargin());
                    topMarginSlider.setMax(imageViewPrefs.getMaxImageViewTopMargin());
                    bottomMarginSlider.setMax(imageViewPrefs.getMaxImageViewBottomMargin());

                    leftMarginSlider.setValue(previewImageView.getViewMargins("left"));
                    rightMarginSlider.setValue(previewImageView.getViewMargins("right"));
                    topMarginSlider.setValue(previewImageView.getViewMargins("top"));
                    bottomMarginSlider.setValue(previewImageView.getViewMargins("bottom"));

                    leftMarginCounter.setText(previewImageView.getViewMargins("left") + "dp");
                    rightMarginCounter.setText(previewImageView.getViewMargins("right") + "dp");
                    topMarginCounter.setText(previewImageView.getViewMargins("top") + "dp");
                    bottomMarginCounter.setText(previewImageView.getViewMargins("bottom") + "dp");
                }
                if (viewTag == "RELATIVE_LAYOUT") {
                    previewTextView.setViewText("Relative Layout Preview");
                    previewRelativeLayout.initCopyView((mRelativeLayout) currentView, "non base");
                    RelativeLayout.LayoutParams layoutRelativePreview = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutRelativePreview.addRule(RelativeLayout.CENTER_IN_PARENT);
                    previewTextView.setLayoutParams(layoutRelativePreview);
                    marginPreviewFrame.addView(previewTextView);

                    relativeLayoutPrefs = new RelativeLayoutPreferences(this);
                    leftMarginSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutLeftMargin());
                    rightMarginSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutRightMargin());
                    topMarginSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutTopMargin());
                    bottomMarginSlider.setMax(relativeLayoutPrefs.getMaxRelativeLayoutBottomMargin());

                    leftMarginSlider.setValue(previewRelativeLayout.getViewMargin("left"));
                    rightMarginSlider.setValue(previewRelativeLayout.getViewMargin("right"));
                    topMarginSlider.setValue(previewRelativeLayout.getViewMargin("top"));
                    bottomMarginSlider.setValue(previewRelativeLayout.getViewMargin("bottom"));

                    leftMarginCounter.setText(previewRelativeLayout.getViewMargin("left") + "dp");
                    rightMarginCounter.setText(previewRelativeLayout.getViewMargin("right") + "dp");
                    topMarginCounter.setText(previewRelativeLayout.getViewMargin("top") + "dp");
                    bottomMarginCounter.setText(previewRelativeLayout.getViewMargin("bottom") + "dp");
                }

                leftMarginSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        leftMarginCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setPreviewMargins("left", i);
                        if (viewTag == "BUTTON")
                            previewButton.setPreviewMargins("left", i);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setPreviewMargins("left", i);
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setPreviewMargins("left", i);
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewTextView.setPreviewMargins("left", i);
                    }
                });
                rightMarginSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        rightMarginCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setPreviewMargins("right", i);
                        if (viewTag == "BUTTON")
                            previewButton.setPreviewMargins("right", i);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setPreviewMargins("right", i);
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setPreviewMargins("right", i);
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewTextView.setPreviewMargins("right", i);
                    }
                });
                topMarginSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        topMarginCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setPreviewMargins("top", i);
                        if (viewTag == "BUTTON")
                            previewButton.setPreviewMargins("top", i);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setPreviewMargins("top", i);
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setPreviewMargins("top", i);
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewTextView.setPreviewMargins("top", i);

                    }
                });
                bottomMarginSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        bottomMarginCounter.setText(i + "dp");
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setPreviewMargins("bottom", i);
                        if (viewTag == "BUTTON")
                            previewButton.setPreviewMargins("bottom", i);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setPreviewMargins("bottom", i);
                        if (viewTag == "IMAGEVIEW")
                            previewImageView.setPreviewMargins("bottom", i);
                        if (viewTag == "RELATIVE_LAYOUT")
                            previewTextView.setPreviewMargins("bottom", i);
                    }
                });
                new AlertDialogWrapper.Builder(this)
                        .setTitle("Margin Control")
                        .setView(marginsEntryView)
                        .setPositiveButton("Set Margins", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if (viewTag == "TEXTVIEW") {
                                    mTextView v = (mTextView) currentView;
                                    v.setViewMargins(leftMarginSlider.getValue(), rightMarginSlider.getValue(),
                                            topMarginSlider.getValue(), bottomMarginSlider.getValue());
                                    v.requestLayout();
                                } else if (viewTag == "BUTTON") {
                                    mButton b = (mButton) currentView;
                                    b.setViewMargins(leftMarginSlider.getValue(), rightMarginSlider.getValue(),
                                            topMarginSlider.getValue(), bottomMarginSlider.getValue());
                                    b.requestLayout();
                                } else if (viewTag == "EDITTEXT") {
                                    mEditText et = (mEditText) currentView;
                                    et.setViewMargins(leftMarginSlider.getValue(), rightMarginSlider.getValue(),
                                            topMarginSlider.getValue(), bottomMarginSlider.getValue());
                                    et.requestLayout();
                                } else if (viewTag == "IMAGEVIEW") {
                                    mImageView iv = (mImageView) currentView;
                                    iv.setViewMargins(leftMarginSlider.getValue(), rightMarginSlider.getValue(),
                                            topMarginSlider.getValue(), bottomMarginSlider.getValue());
                                    iv.requestLayout();
                                } else if (viewTag == "RELATIVE_LAYOUT") {
                                    mRelativeLayout tempRL = (mRelativeLayout) currentView;
                                    tempRL.setViewMargins(leftMarginSlider.getValue(), rightMarginSlider.getValue(),
                                            topMarginSlider.getValue(), bottomMarginSlider.getValue());
                                    tempRL.requestLayout();
                                    marginPreviewFrame.removeAllViews();
                                } else if (viewTag == "SPINNER") {
                                    Spinner s = (Spinner) currentView;
                                }
                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return null;


            /*
            Dialog responsible for control line spacing for views that display text
             */
            case SET_LINE_SPACING:
                LayoutInflater lineSpacing = LayoutInflater.from(this);
                final View lineSpacingEntryView = lineSpacing.inflate(R.layout.setlinespacinglayout, null);
                final Slider setSpacingSlider = (Slider) lineSpacingEntryView.findViewById(R.id.lineSpacingSlider);
                FrameLayout lineSpacingViewPreview = (FrameLayout) lineSpacingEntryView.findViewById(R.id.selectedLineSpacingViewDisplay);
                final TextView lineSpacingCounter = (TextView) lineSpacingEntryView.findViewById(R.id.lineSpacingCounter);
                if (viewTag == "TEXTVIEW") {
                    previewTextView.initCopyView((mTextView) currentView, "set Text /preview");
                    previewTextView.setViewText("This is a line spacing preview, " +
                            "To show the spacing to be applied");
                    lineSpacingViewPreview.addView(previewTextView);
                    textViewPrefs = new TextViewPreferences(this);
                    setSpacingSlider.setMax(textViewPrefs.getMaxTextViewLineSpacing());
                    setSpacingSlider.setValue((int) previewTextView.getViewLineSpacingAdd());
                    lineSpacingCounter.setText(previewTextView.getViewLineSpacingAdd() + "");
                }
                if (viewTag == "BUTTON") {
                    previewButton.initCopyView((mButton) currentView, "set Text /preview");
                    previewButton.setViewText("This is a line spacing preview, " +
                            "To show the spacing to be applied");
                    lineSpacingViewPreview.addView(previewButton);
                    buttonPrefs = new ButtonPreferences(this);
                    setSpacingSlider.setMax(buttonPrefs.getMaxButtonLineSpacing());
                    setSpacingSlider.setValue((int) previewButton.getViewLineSpacingAdd());
                    lineSpacingCounter.setText(previewButton.getViewLineSpacingAdd() + "");
                }
                if (viewTag == "EDITTEXT") {
                    previewEditText.initCopyView((mEditText) currentView, "set Text /preview");
                    previewEditText.setViewHint("This is a line spacing preview, " +
                            "To show the spacing to be applied");
                    lineSpacingViewPreview.addView(previewEditText);
                    editTextPrefs = new EditTextPreferences(this);
                    setSpacingSlider.setMax(editTextPrefs.getMaxEditTextLineSpacing());
                    setSpacingSlider.setValue((int) previewEditText.getViewLineSpacingAdd());
                    lineSpacingCounter.setText(previewEditText.getViewLineSpacingAdd() + "");
                }
                setSpacingSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
                    @Override
                    public void onValueChanged(int i) {
                        lineSpacingCounter.setText("" + i);
                        if (viewTag == "TEXTVIEW")
                            previewTextView.setViewLineSpacing((float) i, (float) i / 10);
                        if (viewTag == "BUTTON")
                            previewButton.setViewLineSpacing((float) i, (float) i / 10);
                        if (viewTag == "EDITTEXT")
                            previewEditText.setViewLineSpacing((float) i, (float) i / 10);
                    }
                });

                return new AlertDialogWrapper.Builder(this)
                        .setTitle("line Spacing")
                        .setView(lineSpacingEntryView)
                        .setPositiveButton("Set Spacing", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if (viewTag == "TEXTVIEW") {
                                    mTextView v = (mTextView) currentView;
                                    v.setViewLineSpacing(setSpacingSlider.getValue(),
                                            setSpacingSlider.getValue() / 10);
                                } else if (viewTag == "BUTTON") {
                                    mButton b = (mButton) currentView;
                                    b.setViewLineSpacing(setSpacingSlider.getValue(),
                                            setSpacingSlider.getValue() / 10);
                                } else if (viewTag == "EDITTEXT") {
                                    mEditText et = (mEditText) currentView;
                                    et.setViewLineSpacing(setSpacingSlider.getValue(),
                                            setSpacingSlider.getValue() / 10);
                                }
                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();

            /*
            Dialog responsible for setting a views visibility
             */
            case SET_VISIBILITY:
                String visibilityString = "";
                if(viewTag.equals("TEXTVIEW")) {
                    mTextView tv = (mTextView) currentView;
                    visibilityString = tv.getViewVisibility();
                } else if(viewTag.equals("BUTTON")) {
                    mButton b = (mButton) currentView;
                    visibilityString = b.getViewVisibility();
                } else if(viewTag.equals("EDITTEXT")) {
                    mEditText ed = (mEditText) currentView;
                    visibilityString = ed.getViewVisibility();
                } else if(viewTag.equals("IMAGEVIEW")) {
                    mImageView iv = (mImageView) currentView;
                    visibilityString = iv.getViewVisibility();
                }

                if(visibilityString.equals("visible")) {
                    new AlertDialogWrapper.Builder(this)
                            .setTitle("Set Visibility")
                            .setMessage("Current view is visible, do you want to make it invisible? If you have wrap content layout wrapping, you may notice the view size changing, the visible view has not changed")
                            .setPositiveButton("set visibility", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(viewTag.equals("TEXTVIEW")) {
                                        mTextView tv = (mTextView) currentView;
                                        tv.setViewVisibility("invisible");
                                    } else if(viewTag.equals("BUTTON")) {
                                        mButton b = (mButton) currentView;
                                        b.setViewVisibility("invisible");
                                    } else if(viewTag.equals("EDITTEXT")) {
                                        mEditText ed = (mEditText) currentView;
                                        ed.setViewVisibility("invisible");
                                    } else if(viewTag.equals("IMAGEVIEW")) {
                                        mImageView iv = (mImageView) currentView;
                                        iv.setViewVisibility("invisible");
                                    }
                                    updateViewToolTip();
                                }
                            })
                            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } else {
                    new AlertDialogWrapper.Builder(this)
                            .setTitle("Set Visibility")
                            .setMessage("Current view is invisible, do you want to make it visible? If you have wrap content layout wrapping, you may notice the view size changing, the visible view has not changed")
                            .setPositiveButton("set visibility", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(viewTag.equals("TEXTVIEW")) {
                                        mTextView tv = (mTextView) currentView;
                                        tv.setViewVisibility("visible");
                                    } else if(viewTag.equals("BUTTON")) {
                                        mButton b = (mButton) currentView;
                                        b.setViewVisibility("visible");
                                    } else if(viewTag.equals("EDITTEXT")) {
                                        mEditText ed = (mEditText) currentView;
                                        ed.setViewVisibility("visible");
                                    }  else if(viewTag.equals("IMAGEVIEW")) {
                                        mImageView iv = (mImageView) currentView;
                                        iv.setViewVisibility("visible");
                                    }
                                    updateViewToolTip();
                                }
                            })
                            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
                return null;


            // TODO; Finish alpha control, current issues with controlling alpha pre API 11+
			/*case ALPHA_MENU:
                LayoutInflater setAlpha = LayoutInflater.from(this);
				final View alphaEntryView = setAlpha.inflate(R.layout.setalphalayout, null);
				final Slider setBackgroundAlphaSlider = (Slider) alphaEntryView.findViewById(R.id.backgroundAlphaSlider);
				final Slider setTextAlphaSlider = (Slider) alphaEntryView.findViewById(R.id.textAlphaSlider);
				FrameLayout alphaPreviewFrame = (FrameLayout) alphaEntryView.findViewById(R.id.selectedAlphaViewDisplay);
				final TextView backgroundAlphaCounter = (TextView) alphaEntryView.findViewById(R.id.setBackgroundAlphaNumberCounter);
				final TextView textAlphaCounter = (TextView) alphaEntryView.findViewById(R.id.setTextAlphaNumberCounter);
				final TextView textAlphaText = (TextView) alphaEntryView.findViewById(R.id.textAlphaText);

				if (viewTag == "TEXTVIEW") {
					previewTextView.initCopyView((mTextView) currentView, "set Text /preview");
					previewTextView.setLayoutParams(previewLayoutParams);
					alphaPreviewFrame.addView(previewTextView);
					setBackgroundAlphaSlider.setValue(previewTextView.getViewAlpha());
					setTextAlphaSlider.setValue(previewTextView.getViewTextAlpha());
					backgroundAlphaCounter.setText("" + previewTextView.getViewAlpha());
					textAlphaCounter.setText("" + previewTextView.getViewTextAlpha());
				}
				if (viewTag == "BUTTON") {
					previewButton.initCopyView((mButton) currentView, "set Text /preview");
					previewButton.setLayoutParams(previewLayoutParams);
					alphaPreviewFrame.addView(previewButton);
					setBackgroundAlphaSlider.setValue(previewButton.getViewAlpha());
					setTextAlphaSlider.setValue(previewButton.getViewTextAlpha());
					backgroundAlphaCounter.setText("" + previewButton.getViewAlpha());
					textAlphaCounter.setText("" + previewButton.getViewTextAlpha());
				}
				if (viewTag == "EDITTEXT") {
					previewEditText.initCopyView((mEditText) currentView, "set Text /preview");
					previewEditText.setLayoutParams(previewLayoutParams);
					alphaPreviewFrame.addView(previewEditText);
					setBackgroundAlphaSlider.setValue(previewEditText.getViewAlpha());
					backgroundAlphaCounter.setText("" + previewEditText.getViewAlpha());
					setTextAlphaSlider.setVisibility(View.INVISIBLE);
					textAlphaCounter.setVisibility(View.INVISIBLE);
					textAlphaText.setVisibility(View.INVISIBLE);
				}
				if (viewTag == "BASE_LAYOUT") {
					previewTextView.setViewText("Base Layout Preview for Alpha ");
					previewTextView.setViewColor(Color.WHITE, "text", "#fff");
					if (baseLayout.getViewColor("background") != Color.WHITE)
						previewTextView.setViewColor(baseLayout.getViewColor("background"), "background", baseLayout.getColorHexValue("background"));
					else {
						previewTextView.setViewColor(Color.BLACK, "background", "#000");
					}
					RelativeLayout.LayoutParams layoutRelativePreview = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
					layoutRelativePreview.addRule(RelativeLayout.CENTER_IN_PARENT);
					previewTextView.setLayoutParams(layoutRelativePreview);
					alphaPreviewFrame.addView(previewTextView);
					setBackgroundAlphaSlider.setValue(baseLayout.getViewAlpha());
					backgroundAlphaCounter.setText("" + baseLayout.getViewAlpha());
					setTextAlphaSlider.setVisibility(View.INVISIBLE);
					textAlphaCounter.setVisibility(View.INVISIBLE);
					textAlphaText.setVisibility(View.INVISIBLE);
				}

				setBackgroundAlphaSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
					@Override
					public void onValueChanged(int i) {
						backgroundAlphaCounter.setText("" + i);
						if (viewTag == "TEXTVIEW")
							previewTextView.setViewAlpha(i);

						if (viewTag == "BUTTON")
							previewButton.setViewAlpha(i);

						if (viewTag == "EDITTEXT")
							previewEditText.setViewAlpha(i);

						if (viewTag == "BASE_LAYOUT")
							previewTextView.setViewAlpha(i);
					}
				});
				setTextAlphaSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
					@Override
					public void onValueChanged(int i) {
						textAlphaCounter.setText("" + i);
						/*if (viewTag == "TEXTVIEW") {
							String hexColor = Integer.toString(previewTextView.getViewColor("text"), 16);
							int color = (int) Long.parseLong(hexColor, 16);
							int red = (color >> 16) & 0xFF;
							int blue = (color >> 0) & 0xFF;
							int green = (color >> 8) & 0xFF;
							previewTextView.setViewColor(Color.argb(i, red, green, blue), "text");
						}
						if (viewTag == "BUTTON") {
							String hexColor = Integer.toString(previewButton.getViewColor("text"), 16);
							int color = (int) Long.parseLong(hexColor, 16);
							int red = (color >> 16) & 0xFF;
							int blue = (color >> 0) & 0xFF;
							int green = (color >> 8) & 0xFF;
							previewButton.setViewTextAlpha(i);
							previewButton.setViewColor(Color.argb(i, red, green, blue), "text");
						}
					}
				});

				return new AlertDialogWrapper.Builder(this)
						.setTitle("Alpha Control")
						.setView(alphaEntryView)
						.setPositiveButton("Set Alpha", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								if (viewTag == "TEXTVIEW") {
									mTextView v = (mTextView) currentView;
									v.setViewAlpha(setBackgroundAlphaSlider.getValue());
									//v.setViewColor(previewTextView.getViewColor("text"), "text");
								}
								if (viewTag == "BUTTON") {
									mButton b = (mButton) currentView;
									b.setViewAlpha(setBackgroundAlphaSlider.getValue());
									//b.setViewColor(previewButton.getViewColor("text"), "text");
								}
								if (viewTag == "EDITTEXT") {
									mEditText et = (mEditText) currentView;
									et.getBackground().setAlpha(setBackgroundAlphaSlider.getValue());
								}
								if (viewTag == "SPINNER") {
									Spinner s = (Spinner) currentView;
									s.getBackground().setAlpha(setBackgroundAlphaSlider.getValue());
								}
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						})
						.create();
						*/

			/*
			Dialog responsible for handling all parent layout paramters for views
			 */
            case LAYOUT_PARENT_POSITIONING:
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Parent Positioning Options")
                        .setSingleChoiceItems(R.array.Layout_Parent_Positioning, -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if (which == 0) {
                                    addParentLayoutParamsToView(RelativeLayout.ALIGN_PARENT_TOP);
                                }
                                if (which == 1) {
                                    addParentLayoutParamsToView(RelativeLayout.ALIGN_PARENT_RIGHT);
                                }
                                if (which == 2) {
                                    addParentLayoutParamsToView(RelativeLayout.ALIGN_PARENT_LEFT);
                                }
                                if (which == 3) {
                                    addParentLayoutParamsToView(RelativeLayout.ALIGN_PARENT_BOTTOM);
                                }
                                if (which == 4) {
                                    addParentLayoutParamsToView(RelativeLayout.CENTER_IN_PARENT);
                                }
                                if (which == 5) {
                                    addParentLayoutParamsToView(RelativeLayout.CENTER_HORIZONTAL);
                                }
                                if (which == 6) {
                                    addParentLayoutParamsToView(RelativeLayout.CENTER_VERTICAL);
                                }
                                reDrawAllViews();
                                Intent intent = new Intent("com.Protofy.com.Protofy.protofy.LAYOUT_CHANGED");
                                getApplicationContext().sendBroadcast(intent);
                                dismissDialog(LAYOUT_PARENT_POSITIONING);
                            }
                        })
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();

            /*
            Dialog responsible for presenting all child relationship layout parameters available
             */
            case LAYOUT_OTHER_VIEW_POSITIONING:
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Positioning from other view options")
                        .setSingleChoiceItems(R.array.Layout_Other_View_Positioning, -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if (which == 0) {
                                    layoutPositioningSelection = PLACE_ABOVE_VIEW;
                                    showDialog(LIST_OF_CURRENT_VIEW_IDS);
                                }
                                if (which == 1) {
                                    layoutPositioningSelection = PLACE_BELOW_VIEW;
                                    showDialog(LIST_OF_CURRENT_VIEW_IDS);
                                }
                                if (which == 2) {
                                    layoutPositioningSelection = PLACE_LEFT_OF_VIEW;
                                    showDialog(LIST_OF_CURRENT_VIEW_IDS);
                                }
                                if (which == 3) {
                                    layoutPositioningSelection = PLACE_RIGHT_OF_VIEW;
                                    showDialog(LIST_OF_CURRENT_VIEW_IDS);
                                }
                                if (which == 4) {
                                    layoutPositioningSelection = ALIGN_BASELINE;
                                    showDialog(LIST_OF_CURRENT_VIEW_IDS);
                                }
                                if (which == 5) {
                                    layoutPositioningSelection = ALIGN_END;
                                    showDialog(LIST_OF_CURRENT_VIEW_IDS);
                                }
                                if (which == 6) {
                                    layoutPositioningSelection = ALIGN_LEFT_EDGES;
                                    showDialog(LIST_OF_CURRENT_VIEW_IDS);
                                }
                                if (which == 7) {
                                    layoutPositioningSelection = ALIGN_RIGHT_EDGES;
                                    showDialog(LIST_OF_CURRENT_VIEW_IDS);
                                }
                                if (which == 8) {
                                    layoutPositioningSelection = ALIGN_TOP;
                                    showDialog(LIST_OF_CURRENT_VIEW_IDS);
                                }
                                if (which == 9) {
                                    layoutPositioningSelection = ALIGN_START;
                                    showDialog(LIST_OF_CURRENT_VIEW_IDS);
                                }
                            }
                        })
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();

            /*
            Dialog responsible for allowing user to select a child view to link too and apply view linking
             */
            case LIST_OF_CURRENT_VIEW_IDS:
                LayoutInflater inflater = LayoutInflater.from(this);
                View convertView = inflater.inflate(R.layout.view_names_list_view, null);
                mRelativeLayout viewsOnTheLayout = (mRelativeLayout) convertView.findViewById(R.id.idViewLayout);
                ToolTipRelativeLayout previewToolTips = (ToolTipRelativeLayout) convertView.findViewById(R.id.view_preview_tooltipRelativeLayout);
                final mTextView textViewToBeMoved = new mTextView(this);
                final mButton buttonToBeMoved = new mButton(this);
                final mEditText editTextToBeMoved = new mEditText(this);
                final mImageView imageViewToBeMoved = new mImageView(this);
                //final mRelativeLayout relativeLayoutToBeMoved = new mRelativeLayout(this);
                //viewsOnTheLayout.initCopyView(baseLayout);


                ToolTip previewToolTip = new com.nhaarman.supertooltips.ToolTip()
                        .withText("Selected View")
                        .withColor(Color.parseColor("#24aaf2"))
                        .withShadow()
                        .withAnimationType(ToolTip.AnimationType.NONE);

                for (int i = 0; i < uiList.getSize(); i++) {
                    mRelativeLayout tempRL = (mRelativeLayout) uiList.getView(i);

                    if(tempRL.getId() == currentLayoutView.getId()) {
                        UIelementList currentListOfViews = tempRL.getLayoutViews();
                        viewsOnTheLayout.initCopyView(tempRL, "");
                        for(int j = 0; j < currentListOfViews.getSize(); j++) {
                            if (currentListOfViews.getView(j).getId() == currentView.getId()) {
                                if (currentListOfViews.getViewTag(j) == "TEXTVIEW") {
                                    textViewToBeMoved.initCopyView((mTextView) currentListOfViews.getView(j), "set id as text");
                                    viewsOnTheLayout.addView(textViewToBeMoved);
                                    textViewToBeMoved.setPreview(true);
                                    Log.v("View found/added", textViewToBeMoved.getStringID());
                                } else if (currentListOfViews.getViewTag(j) == "BUTTON") {
                                    buttonToBeMoved.initCopyView((mButton) currentListOfViews.getView(j), "set id as text");
                                    buttonToBeMoved.setPreview(true);
                                    viewsOnTheLayout.addView(buttonToBeMoved);
                                } else if (currentListOfViews.getViewTag(j) == "EDITTEXT") {
                                    editTextToBeMoved.initCopyView((mEditText) currentListOfViews.getView(j), "set id as text");
                                    editTextToBeMoved.setPreview(true);
                                    viewsOnTheLayout.addView(editTextToBeMoved);
                                } else if (currentListOfViews.getViewTag(j) == "IMAGEVIEW") {
                                    imageViewToBeMoved.initCopyView((mImageView) currentListOfViews.getView(j), "set id as text");
                                    imageViewToBeMoved.setPreview(true);
                                    viewsOnTheLayout.addView(imageViewToBeMoved);
                                }
                            } else {
                                if (currentListOfViews.getView(j).getTag() == "TEXTVIEW") {
                                    mTextView tv = new mTextView(this);
                                    tv.initCopyView((mTextView) currentListOfViews.getView(j), "set id as text");
                                    final String viewStringId = tv.getStringID();
                                    tv.setClickable(true);
                                    tv.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (currentView.getTag() == "BUTTON") {
                                                addLinkedLayoutParamsToButton(buttonToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "TEXTVIEW") {
                                                addLinkedLayoutParamsToTextView(textViewToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "EDITTEXT") {
                                                addLinkedLayoutParamsToEditText(editTextToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "IMAGEVIEW") {
                                                addLinkedLayoutParamsToImageView(imageViewToBeMoved, viewStringId, v);
                                            }
                                        }
                                    });
                                    viewsOnTheLayout.addView(tv);
                                    previewToolTip.withText("Id: " + tv.getStringID());
                                    ToolTipView mToolTip = previewToolTips.showToolTipForView(previewToolTip, tv);
                                } else if (currentListOfViews.getView(j).getTag() == "BUTTON") {
                                    final mButton b = new mButton(this);
                                    b.initCopyView((mButton) currentListOfViews.getView(j), "set id as text");
                                    final String viewStringId = b.getStringID();
                                    b.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (currentView.getTag() == "BUTTON") {
                                                addLinkedLayoutParamsToButton(buttonToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "TEXTVIEW") {
                                                addLinkedLayoutParamsToTextView(textViewToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "EDITTEXT") {
                                                addLinkedLayoutParamsToEditText(editTextToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "IMAGEVIEW") {
                                                addLinkedLayoutParamsToImageView(imageViewToBeMoved, viewStringId, v);
                                            }
                                        }
                                    });
                                    viewsOnTheLayout.addView(b);
                                    previewToolTip.withText("Id: " + b.getStringID());
                                    ToolTipView mToolTip = previewToolTips.showToolTipForView(previewToolTip, b);
                                } else if (currentListOfViews.getView(j).getTag() == "EDITTEXT") {
                                    final mEditText et = new mEditText(this);
                                    et.initCopyView((mEditText) currentListOfViews.getView(j), "set id as text");
                                    final String viewStringId = et.getStringID();
                                    et.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (currentView.getTag() == "BUTTON") {
                                                addLinkedLayoutParamsToButton(buttonToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "TEXTVIEW") {
                                                addLinkedLayoutParamsToTextView(textViewToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "EDITTEXT") {
                                                addLinkedLayoutParamsToEditText(editTextToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "IMAGEVIEW") {
                                                addLinkedLayoutParamsToImageView(imageViewToBeMoved, viewStringId, v);
                                            }
                                        }
                                    });
                                    viewsOnTheLayout.addView(et);
                                    previewToolTip.withText("Id: " + et.getStringID());
                                    ToolTipView mToolTip = previewToolTips.showToolTipForView(previewToolTip, et);
                                } else if (currentListOfViews.getView(j).getTag() == "IMAGEVIEW") {
                                    final mImageView iv = new mImageView(this);
                                    iv.initCopyView((mImageView) currentListOfViews.getView(j), "set id as text");
                                    final String viewStringId = iv.getStringID();
                                    iv.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (currentView.getTag() == "BUTTON") {
                                                addLinkedLayoutParamsToButton(buttonToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "TEXTVIEW") {
                                                addLinkedLayoutParamsToTextView(textViewToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "EDITTEXT") {
                                                addLinkedLayoutParamsToEditText(editTextToBeMoved, viewStringId, v);
                                            } else if (currentView.getTag() == "IMAGEVIEW") {
                                                addLinkedLayoutParamsToImageView(imageViewToBeMoved, viewStringId, v);
                                            }
                                        }
                                    });
                                    viewsOnTheLayout.addView(iv);
                                    previewToolTip.withText("Id: " + iv.getStringID());
                                    ToolTipView mToolTip = previewToolTips.showToolTipForView(previewToolTip, iv);
                                }
                            }
                        }
                    }

                }

                new AlertDialogWrapper.Builder(this)
                        .setTitle("Select target view from preview")
                        .setView(convertView)
                        .setPositiveButton("Set Parameter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (viewTag == "TEXTVIEW") {
                                    mTextView tv = (mTextView) currentView;
                                    tv.setPreview(false);
                                    tv.addViewLayoutParam(textViewToBeMoved.getLastLayoutParam(), textViewToBeMoved.getLastLinkedViewStringId(), textViewToBeMoved.getLastLinkedViewId(), uiList);
                                } else if (viewTag == "BUTTON") {
                                    mButton b = (mButton) currentView;
                                    b.setPreview(false);
                                    b.addViewLayoutParam(buttonToBeMoved.getLastChildLayoutParam(), buttonToBeMoved.getLastLinkedViewStringId(), buttonToBeMoved.getLastLinkedViewId(), uiList);
                                } else if (viewTag == "EDITTEXT") {
                                    mEditText et = (mEditText) currentView;
                                    et.setPreview(false);
                                    et.addViewLayoutParam(editTextToBeMoved.getLastChildLayoutParam(), editTextToBeMoved.getLastLinkedViewStringId(), editTextToBeMoved.getLastLinkedViewId(), uiList);
                                } else if (viewTag == "IMAGEVIEW") {
                                    mImageView iv = (mImageView) currentView;
                                    iv.setPreview(false);
                                    iv.addViewLayoutParam(imageViewToBeMoved.getLastChildLayoutParam(), imageViewToBeMoved.getLastLinkedViewStringId(), imageViewToBeMoved.getLastLinkedViewId(), uiList);
                                }
                                Intent intent = new Intent("com.Protofy.com.Protofy.protofy.LAYOUT_CHANGED");
                                getApplicationContext().sendBroadcast(intent);
                            }
                        })
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create()
                        .show();
                return null;

            //TODO: Allow view resizing based on pixels and not just Dp
            /*
            case PIXEL_LAYOUT_MENU:
				LayoutInflater pixelLayout = LayoutInflater.from(this);
				final View setPixelEntryView = pixelLayout.inflate(R.layout.setpixellayout, null);
				final Slider pixelHeightSlider = (Slider) setPixelEntryView.findViewById(R.id.pixelHeightSlider);
				final Slider pixelWidthSlider = (Slider) setPixelEntryView.findViewById(R.id.pixelWidthSlider);
				final TextView heightCounter = (TextView) setPixelEntryView.findViewById(R.id.heightSize);
				final TextView widthCounter = (TextView) setPixelEntryView.findViewById(R.id.widthSize);
				FrameLayout pixelPreview = (FrameLayout) setPixelEntryView.findViewById(R.id.selectedPixelViewDisplay);
				final mButton pixelPreviewView = new mButton(this);
				pixelPreview.addView(pixelPreviewView);
				pixelHeightSlider.setValue(pixelPreviewView.getHeight());
				pixelWidthSlider.setValue(pixelWidthSlider.getWidth());

				pixelHeightSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
					@Override
					public void onValueChanged(int i) {
						heightCounter.setText(i+"");
						pixelPreviewView.setViewPixelSize(pixelPreviewView.getWidth(), i, "height");
					}
				});
				pixelWidthSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
					@Override
					public void onValueChanged(int i) {
						widthCounter.setText(i+"");
						pixelPreviewView.setViewPixelSize(i, pixelPreviewView.getHeight(), "width");
					}
				});

				return new AlertDialogWrapper.Builder(this)
						.setTitle("Pixel Control")
						.setView(setPixelEntryView)
						.setPositiveButton("Set Pixels", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								if (viewTag == "TEXTVIEW") {
									TextView v = (TextView) currentView;
									//v.setHeight(Integer.parseInt(pixelH.getText().toString()));
									//v.setWidth(Integer.parseInt(pixelW.getText().toString()));
									v.invalidate();
								} else if (viewTag == "BUTTON") {
									mButton b = (mButton) currentView;
									b.setViewPixelSize(pixelWidthSlider.getValue(), pixelHeightSlider.getValue(), "width and height");
									b.invalidate();
								} else if (viewTag == "EDITTEXT") {
									EditText e = (EditText) currentView;
									//e.setHeight(Integer.parseInt(pixelH.getText().toString()));
									//e.setWidth(Integer.parseInt(pixelW.getText().toString()));
									e.invalidate();
								}
								updateViewToolTip();
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						})
						.create();

            //TODO: FInish allowing views to scale in multiples
			/*case SCALE:
				LayoutInflater scale = LayoutInflater.from(this);
				final View setScaleEntryView = scale.inflate(R.layout.setscalelayout, null);
				FrameLayout scalePreviewFrame = (FrameLayout) setScaleEntryView.findViewById(R.id.selectedScaleViewDisplay);
				final TextView scaleXText = (TextView) setScaleEntryView.findViewById(R.id.scaleXText);
				final TextView scaleYText = (TextView) setScaleEntryView.findViewById(R.id.scaleYText);

				final mButton scalePreview = new mButton(this);
				scalePreview.initCopyView((mButton) currentView, "set Text");
				scalePreviewFrame.addView(scalePreview);

				final Slider scaleXSlider = (Slider) setScaleEntryView.findViewById(R.id.scaleXSlider);
				scaleXSlider.setValue((int)scalePreview.getButtonScale("Scale X"));
				scaleXSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
					@Override
					public void onValueChanged(int i) {
						i = i/15;
						scaleXText.setText(i+"");
						scalePreview.setButtonScale((float) i, scalePreview.getScaleY(), "Scale X");
					}
				});
				final Slider scaleYSlider = (Slider) setScaleEntryView.findViewById(R.id.scaleYSlider);
				scaleYSlider.setValue((int)scalePreview.getButtonScale("Scale Y"));
				scaleYSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
					@Override
					public void onValueChanged(int i) {
						i = i/15;
						scaleYText.setText(i+"");
						scalePreview.setButtonScale(scalePreview.getScaleX(), (float)i, "Scale Y");
					}
				});
				return new AlertDialog.Builder(this)
						.setTitle("Scale")
						.setView(setScaleEntryView)
						.setPositiveButton("Set Scale", new DialogInterface.OnClickListener() {
							EditText e = (EditText) setScaleEntryView.findViewById(R.id.scaleEditText1);

							public void onClick(DialogInterface dialog, int which) {
								if (viewTag == "TEXTVIEW") {
									TextView v = (TextView) currentView;
									v.setTextScaleX(Float.parseFloat(e.getText().toString()));
								} else if (viewTag == "BUTTON") {
									mButton b = (mButton) currentView;
									b.setButtonScale((float) scaleXSlider.getValue(), (float) scaleYSlider.getValue(), "set Scale");
									//b.setTextScaleX(Float.parseFloat(e.getText().toString()));
								} else if (viewTag == "EDITTEXT") {
									EditText ed = (EditText) currentView;
									ed.setTextScaleX(Float.parseFloat(e.getText().toString()));
								}
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						})
						.create();*/

            /*
            Dialog responsible for adding new views to the current layout
             */
            case ADD_VIEW:
                LayoutInflater addViewLayout = LayoutInflater.from(this);
                final View addViewDialogView = addViewLayout.inflate(R.layout.addviewlayout, null);
                final MaterialListView mListView = (MaterialListView) addViewDialogView.findViewById(R.id.addview_listview);
                mListView.setAdapter(new MaterialListAdapter(null));
                // Set the dismiss listener
                mListView.setOnDismissCallback(new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull Card card, int position) {
                        // Show a toast
                        Toast.makeText(getApplicationContext(), "You have dismissed a " + card.getTag(), Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the ItemTouchListener
                mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(@NonNull Card card, int position) {
                        if(card.getTag().equals("TEXTVIEW_CARD")){
                            if(viewTag == "BASE_LAYOUT" || viewTag == "RELATIVE_LAYOUT") {
                                mRelativeLayout rl = (mRelativeLayout) currentView;
                                rl.addViewToList(new mTextView(getApplicationContext()), "TEXTVIEW");
                                setDialogView(rl.getLayoutViews().getLast(), rl.getLayoutViews().getLast().getTag(), savedInstanceStateSecond);
                                dismissDialog(ADD_VIEW);
                                showDialog(SET_ID);
                            }

                        } else if(card.getTag().equals("BUTTON_CARD")) {
                            if(viewTag == "BASE_LAYOUT" || viewTag == "RELATIVE_LAYOUT") {
                                mRelativeLayout rl = (mRelativeLayout) currentView;
                                rl.addViewToList(new mButton(getApplicationContext()), "BUTTON");
                                setDialogView(rl.getLayoutViews().getLast(), rl.getLayoutViews().getLast().getTag(), savedInstanceStateSecond);
                                dismissDialog(ADD_VIEW);
                                showDialog(SET_ID);
                            }
                        } else if (card.getTag().equals("EDITTEXT_CARD")) {
                            if(viewTag == "BASE_LAYOUT" || viewTag == "RELATIVE_LAYOUT") {
                                mRelativeLayout rl = (mRelativeLayout) currentView;
                                rl.addViewToList(new mEditText(getApplicationContext()), "EDITTEXT");
                                setDialogView(rl.getLayoutViews().getLast(), rl.getLayoutViews().getLast().getTag(), savedInstanceStateSecond);
                                dismissDialog(ADD_VIEW);
                                showDialog(SET_ID);
                            }
                        } else if (card.getTag().equals("IMAGEVIEW_CARD")) {
                            if(viewTag == "BASE_LAYOUT" || viewTag == "RELATIVE_LAYOUT") {
                                mRelativeLayout rl = (mRelativeLayout) currentView;
                                rl.addViewToList(new mImageView(getApplicationContext()), "IMAGEVIEW");
                                setDialogView(rl.getLayoutViews().getLast(), rl.getLayoutViews().getLast().getTag(), savedInstanceStateSecond);
                                dismissDialog(ADD_VIEW);
                                showDialog(SET_ID);
                            }
                        } else if (card.getTag().equals("RELATIVE_LAYOUT_CARD")) {
                            if(viewTag == "BASE_LAYOUT" || viewTag == "RELATIVE_LAYOUT") {
                                mRelativeLayout rl = (mRelativeLayout) currentView;
                                rl.addViewToList(new mRelativeLayout(getApplicationContext()), "RELATIVE_LAYOUT");
                                setDialogView(rl.getLayoutViews().getLast(), rl.getLayoutViews().getLast().getTag(), savedInstanceStateSecond);
                                dismissDialog(ADD_VIEW);
                                showDialog(SET_ID);
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(@NonNull Card card, int position) {
                        Log.d("LONG_CLICK", "" + card.getTag());
                    }
                });
                Card textViewCard = new Card.Builder(this)
                        .setTag("TEXTVIEW_CARD")
                        .withProvider(SmallImageCardProvider.class)
                        .setTitle("Add some text")
                        .setDescription("TextViews are the main view in android for displaying any type of text to the user")
                        .setDrawable(R.drawable.textview)
                        .setDescriptionColor(Color.parseColor("#24aaf2"))
                        .setTitleColor(Color.parseColor("#f99503"))
                        .endConfig()
                        .build();
                Card buttonCard = new Card.Builder(this)
                        .setTag("BUTTON_CARD")
                        .withProvider(SmallImageCardProvider.class)
                        .setTitle("Add a button")
                        .setDescription("Buttons are great at handling user actions within the layout, such as telling the app to login the user when the button is clicked")
                        .setDrawable(R.drawable.button)
                        .setDescriptionColor(Color.parseColor("#24aaf2"))
                        .setTitleColor(Color.parseColor("#f99503"))
                        .endConfig()
                        .build();
                Card editTextCard = new Card.Builder(this)
                        .setTag("EDITTEXT_CARD")
                        .withProvider(SmallImageCardProvider.class)
                        .setTitle("Add text input")
                        .setDescription("EditText views are the go to view on android for handling user text input. This could be like providing a place for the user to input their username and password.")
                        .setDrawable(R.drawable.edittext)
                        .setDescriptionColor(Color.parseColor("#24aaf2"))
                        .setTitleColor(Color.parseColor("#f99503"))
                        .endConfig()
                        .build();
                Card imageViewCard = new Card.Builder(this)
                        .setTag("IMAGEVIEW_CARD")
                        .withProvider(SmallImageCardProvider.class)
                        .setTitle("Add an Image")
                        .setDescription("ImageView views are for handling and displaying images to the user. This could be like providing a place for the app to display the user's profile image.")
                        .setDrawable(R.drawable.imageview_default)
                        .setDescriptionColor(Color.parseColor("#24aaf2"))
                        .setTitleColor(Color.parseColor("#f99503"))
                        .endConfig()
                        .build();
                Card relativeLayoutCard = new Card.Builder(this)
                        .setTag("RELATIVE_LAYOUT_CARD")
                        .withProvider(SmallImageCardProvider.class)
                        .setTitle("Add a Relative Layout")
                        .setDescription("Relative layouts act as a frame that holds views and controls the view positions within the frame.")
                        .setDrawable(R.drawable.imageview_default)
                        .setDescriptionColor(Color.parseColor("#24aaf2"))
                        .setTitleColor(Color.parseColor("#f99503"))
                        .endConfig()
                        .build();
                mListView.add(textViewCard);
                mListView.add(buttonCard);
                mListView.add(editTextCard);
                mListView.add(imageViewCard);
                mListView.add(relativeLayoutCard);


                /*TextView textViewAddPreview = (TextView) addViewDialogView.findViewById(R.id.addTextViewPreview);
                textViewAddPreview.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uiList.addView(new mTextView(getApplicationContext()), "TEXTVIEW");
                        setDialogView(uiList.getLast(), uiList.getLast().getTag(), savedInstanceStateSecond);
                        dismissDialog(ADD_VIEW);
                        showDialog(SET_ID);
                    }
                });

                Button buttonAddPreview = (Button) addViewDialogView.findViewById(R.id.addButtonPreview);
                buttonAddPreview.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uiList.addView(new mButton(getApplicationContext()), "BUTTON");
                        setDialogView(uiList.getLast(), uiList.getLast().getTag(), savedInstanceStateSecond);
                        dismissDialog(ADD_VIEW);
                        showDialog(SET_ID);
                    }
                });

                EditText editTextAddPreview = (EditText) addViewDialogView.findViewById(R.id.addEditTextPreview);
                editTextAddPreview.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uiList.addView(new mEditText(getApplicationContext()), "EDITTEXT");
                        setDialogView(uiList.getLast(), uiList.getLast().getTag(), savedInstanceStateSecond);
                        dismissDialog(ADD_VIEW);
                        showDialog(SET_ID);
                    }
                });*/
                updateTutorialState();
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Select which view to add?")
                        .setView(addViewDialogView)
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();

            /*
            Dialog responsible for removing views from the layout
             */
            case REMOVE_VIEW:
                if (viewTag.equals("TEXTVIEW")) {
                    mTextView tv = new mTextView(this);
                    tv.initCopyView((mTextView) currentView, "set id as text");
                    viewDeletedStringId = tv.getStringID();
                } else if (viewTag.equals("BUTTON")) {
                    mButton b = new mButton(this);
                    b.initCopyView((mButton) currentView, "set id as text");
                    viewDeletedStringId = b.getStringID();
                } else if (viewTag.equals("EDITTEXT")) {
                    mEditText ed = new mEditText(this);
                    ed.initCopyView((mEditText) currentView, "set id as text");
                    viewDeletedStringId = ed.getStringID();
                } else if (viewTag.equals("IMAGEVIEW")) {
                    mImageView iv = new mImageView(this);
                    iv.initCopyView((mImageView) currentView, "set id as text");
                    viewDeletedStringId = iv.getStringID();
                }
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("Are you sure you want to delete the " + viewDeletedStringId + " view?")
                        .setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int k = 0; k < uiList.getSize(); k++) {
                                    mRelativeLayout tempRL = (mRelativeLayout) uiList.getView(k);
                                    UIelementList tempUIlist = tempRL.getLayoutViews();
                                    for (int p = 0; p < tempUIlist.getSize(); p++) {
                                        if (tempUIlist.getView(k).equals(currentView)) {
                                            tempUIlist.removeView(k);
                                            tempRL.removeView(currentView);
                                            hideMenu();
                                            removeDeletedViewLayoutParams();
                                        }/* else {
                                            mRelativeLayout rl = (mRelativeLayout) uiList.getView(k);
                                            for (int l = 0; l < rl.getLayoutViews().getSize(); l++) {
                                                if (rl.getLayoutViews().getView(l).equals(currentView)) {
                                                    rl.getLayoutViews().removeView(l);
                                                    rl.removeView(currentView);
                                                    hideMenu();
                                                    removeDeletedViewLayoutParams();
                                                }
                                            }
                                        } */
                                    }
                                }
                            }
                        })
                        .setNegativeButton("No, go back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();

            /*
            Dialog responsible for letting the user remove layout params for a view
             */
            case REMOVE_PARAMS:
                LinkedList<String> tempStringParamsList = new LinkedList<>();
                if (currentView.getTag().equals("TEXTVIEW")) {
                    mTextView tv = (mTextView) currentView;
                    tempStringParamsList = tv.getStringLayoutParams();
                } else if (currentView.getTag().equals("BUTTON")) {
                    mButton b = (mButton) currentView;
                    tempStringParamsList = b.getStringLayoutParams();
                } else if (currentView.getTag().equals("EDITTEXT")) {
                    mEditText ed = (mEditText) currentView;
                    tempStringParamsList = ed.getStringLayoutParams();
                } else if (currentView.getTag().equals("IMAGEVIEW")) {
                    mImageView iv = (mImageView) currentView;
                    tempStringParamsList = iv.getStringLayoutParams();
                }
                Log.v("String Param Size", tempStringParamsList.size() + "");
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Select a parameter to remove")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setSingleChoiceItems(tempStringParamsList.toArray(new String[tempStringParamsList.size()]), -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (currentView.getTag().equals("TEXTVIEW")) {
                                    mTextView tv = (mTextView) currentView;
                                    prepForUserParamRemoval(tv.getStringLayoutParams().get(which), which);
                                } else if (currentView.getTag().equals("BUTTON")) {
                                    mButton b = (mButton) currentView;
                                    prepForUserParamRemoval(b.getStringLayoutParams().get(which), which);
                                } else if (currentView.getTag().equals("EDITTEXT")) {
                                    mEditText ed = (mEditText) currentView;
                                    prepForUserParamRemoval(ed.getStringLayoutParams().get(which), which);
                                } else if (currentView.getTag().equals("IMAGEVIEW")) {
                                    mImageView iv = (mImageView) currentView;
                                    prepForUserParamRemoval(iv.getStringLayoutParams().get(which), which);
                                }
                                showDialog(ARE_YOU_SURE_DIALOG);
                            }
                        })
                        .create();

            /*
            Confirmation dialog.
             */
            case ARE_YOU_SURE_DIALOG:
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("Are you sure you want to remove the layout parameter " + stringParam + "?")
                        .setPositiveButton("Yes, remove it", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (currentView.getTag().equals("TEXTVIEW")) {
                                    mTextView tv = (mTextView) currentView;
                                    tv.userRemovedLayoutParam(paramData);
                                } else if (currentView.getTag().equals("BUTTON")) {
                                    mButton b = (mButton) currentView;
                                    b.userRemovedLayoutParam(paramData);
                                } else if (currentView.getTag().equals("EDITTEXT")) {
                                    mEditText ed = (mEditText) currentView;
                                    ed.userRemovedLayoutParam(paramData);
                                } else if (currentView.getTag().equals("IMAGEVIEW")) {
                                    mImageView iv = (mImageView) currentView;
                                    iv.userRemovedLayoutParam(paramData);
                                }
                                reDrawAllViews();
                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("No, go back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();

            /*
            Dialog responsible for applying view wrapping parameters
             */
            case PARAMETERS:
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Parameter Options")
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setSingleChoiceItems(R.array.Parameters, -1, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    if (viewTag == "TEXTVIEW") {
                                        mTextView v = (mTextView) currentView;
                                        v.setLayoutWrapping(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    } else if (viewTag == "BUTTON") {
                                        mButton b = (mButton) currentView;
                                        b.setLayoutWrapping(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    } else if (viewTag == "EDITTEXT") {
                                        mEditText et = (mEditText) currentView;
                                        et.setLayoutWrapping(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    } else if (viewTag == "IMAGEVIEW") {
                                        mImageView iv = (mImageView) currentView;
                                        iv.setLayoutWrapping(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    }
                                }
                                if (which == 1) {
                                    if (viewTag == "TEXTVIEW") {
                                        mTextView v = (mTextView) currentView;
                                        v.setLayoutWrapping(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                    } else if (viewTag == "BUTTON") {
                                        mButton b = (mButton) currentView;
                                        b.setLayoutWrapping(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                    } else if (viewTag == "EDITTEXT") {
                                        mEditText et = (mEditText) currentView;
                                        et.setLayoutWrapping(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                    } else if (viewTag == "IMAGEVIEW") {
                                        mImageView iv = (mImageView) currentView;
                                        iv.setLayoutWrapping(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                    }
                                }
                                if (which == 2) {
                                    if (viewTag == "TEXTVIEW") {
                                        mTextView v = (mTextView) currentView;
                                        v.setLayoutWrapping(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    } else if (viewTag == "BUTTON") {
                                        mButton b = (mButton) currentView;
                                        b.setLayoutWrapping(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    } else if (viewTag == "EDITTEXT") {
                                        mEditText et = (mEditText) currentView;
                                        et.setLayoutWrapping(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    } else if (viewTag == "IMAGEVIEW") {
                                        mImageView iv = (mImageView) currentView;
                                        iv.setLayoutWrapping(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    }
                                }
                                if (which == 3) {
                                    if (viewTag == "TEXTVIEW") {
                                        mTextView v = (mTextView) currentView;
                                        v.setLayoutWrapping(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                    } else if (viewTag == "BUTTON") {
                                        mButton b = (mButton) currentView;
                                        b.setLayoutWrapping(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                    } else if (viewTag == "EDITTEXT") {
                                        mEditText et = (mEditText) currentView;
                                        et.setLayoutWrapping(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                    } else if (viewTag == "IMAGEVIEW") {
                                        mImageView iv = (mImageView) currentView;
                                        iv.setLayoutWrapping(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                    }
                                }
                                Intent intent = new Intent("com.Protofy.com.Protofy.protofy.LAYOUT_CHANGED");
                                getApplicationContext().sendBroadcast(intent);
                                currentView.requestLayout();
                                updateViewToolTip();
                            }
                        })
                        .create();
            /*

			case SHADOW_LAYER:
				LayoutInflater shadowLayer = LayoutInflater.from(this);
				final View shadowLayerEntryView = shadowLayer.inflate(R.layout.shadowlayerlayout, null);
				Button colorButton = (Button) shadowLayerEntryView.findViewById(R.id.shadowColorButton);
				colorButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						showDialog(COLOR);
					}
				});
				return new AlertDialogWrapper.Builder(this)
						.setTitle("Shadow Layer Control")
						.setView(shadowLayerEntryView)
						.setPositiveButton("Set Shadow", new DialogInterface.OnClickListener() {
							EditText radius = (EditText) shadowLayerEntryView.findViewById(R.id.shadowRadiusEditText);
							EditText dxEdit = (EditText) shadowLayerEntryView.findViewById(R.id.shawdowDxEditText);
							EditText dyEdit = (EditText) shadowLayerEntryView.findViewById(R.id.shadowDyEdittext);

							public void onClick(DialogInterface dialog, int which) {
								if (viewTag == "TEXTVIEW") {
									TextView v = (TextView) currentView;
									v.setShadowLayer(Float.parseFloat(radius.getText().toString()), Float.parseFloat(dxEdit.getText().toString()), Float.parseFloat(dyEdit.getText().toString()), shadowColor);
								}
								if (viewTag == "EDITTEXT") {
									EditText ed = (EditText) currentView;
									ed.setShadowLayer(Float.parseFloat(radius.getText().toString()), Float.parseFloat(dxEdit.getText().toString()), Float.parseFloat(dyEdit.getText().toString()), shadowColor);
								}
								updateViewToolTip();
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						})
						.create();

			/*case TEXTVIEW_OPTIONS:
				return new AlertDialog.Builder(this)
						.setTitle("Textview Options")
						.setSingleChoiceItems(R.array.TextView_Options, -1, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								if (which == 0)
									showDialog(SHADOW_LAYER);
								if (which == 1)
									showDialog(TEXT_STYLE);
								if (which == 2)
									showDialog(LAYOUT_OPTIONS);
							}
						})
						.create();

			case SPINNER_OPTIONS:
				return new AlertDialog.Builder(this)
						.setTitle("Spinner Options")
						.setSingleChoiceItems(R.array.Spinner_Options, -1, new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								if (which == 0)
									showDialog(ALPHA_MENU);
								if (which == 1)
									showDialog(COLOR);
								if (which == 2)
									showDialog(SET_PADDING);
								if (which == 3)
									showDialog(PARAMETERS);
							}
						})
						.create(); */

            /*
            Dialog responsible for letting the user apply hint messages to EditTexts
             */
            case SET_HINT:
                LayoutInflater hint = LayoutInflater.from(this);
                final View setHintEntryView = hint.inflate(R.layout.sethint, null);
                FrameLayout hintPreviewFrame = (FrameLayout) setHintEntryView.findViewById(R.id.selectedHintViewDisplay);
                final EditText setHintEditText = (EditText) setHintEntryView.findViewById(R.id.setHintEditText);
                previewEditText.initCopyView((mEditText) currentView, "set Text /preview");
                previewEditText.setLayoutParams(previewLayoutParams);
                hintPreviewFrame.addView(previewEditText);
                setHintEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        previewEditText.setViewHint(setHintEditText.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Set Hint")
                        .setView(setHintEntryView)
                        .setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(setHintEditText, InputMethodManager.SHOW_IMPLICIT);
                            }
                        })
                        .setPositiveButton("Set Hint", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (viewTag == "EDITTEXT") {
                                    mEditText ed = (mEditText) currentView;
                                    ed.setHint(setHintEditText.getText());
                                }
                                updateViewToolTip();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();

			/*case SET_EMS:
				LayoutInflater emsLayout = LayoutInflater.from(this);
				final View setEMSEntryView = emsLayout.inflate(R.layout.setemslayout, null);
				final Slider setEMSSlider = (Slider) setEMSEntryView.findViewById(R.id.emsSlider);
				FrameLayout viewEMSPreview = (FrameLayout) setEMSEntryView.findViewById(R.id.selectedEmsViewDisplay);
				final TextView setEMSCounter = (TextView) setEMSEntryView.findViewById(R.id.setEMSNumberCounter);

				final mButton emsButtonPreview = new mButton(this);
				viewEMSPreview.addView(emsButtonPreview);
				setEMSSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
					@Override
					public void onValueChanged(int i) {
						setEMSCounter.setText(""+i);
						emsButtonPreview.setEms(i);
					}
				});

				return new AlertDialog.Builder(this)
						.setTitle("EMS Control")
						.setView(setEMSEntryView)
						.setPositiveButton("Set EMS", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								if (viewTag == "TEXTVIEW") {
									TextView v = (TextView) currentView;
									v.setEms(setEMSSlider.getValue());
								}
								if (viewTag == "BUTTON") {
									Button b = (Button) currentView;
									b.setEms(setEMSSlider.getValue());

								}
								if (viewTag == "EDITTEXT") {
									EditText ed = (EditText) currentView;
									ed.setEms(setEMSSlider.getValue());
								}
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						})
						.create();
			*/

			/*
			Dialog responsible for assigning IDs to views. Takes a string from the user and converts it into an ID that Android can use.
			 */
            case SET_ID:
                LayoutInflater idLayout = LayoutInflater.from(this);
                final View setIdEntryView = idLayout.inflate(R.layout.setidlayout, null);
                final EditText idEditText = (EditText) setIdEntryView.findViewById(R.id.setIdEditText);
                idEditText.requestFocus();


                new AlertDialogWrapper.Builder(this)
                        .setTitle("Set the view ID")
                        .setView(setIdEntryView)
                        .setCancelable(false)
                        .setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(idEditText, InputMethodManager.SHOW_IMPLICIT);
                            }
                        })
                        .setPositiveButton("Set ID", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String inputString = idEditText.getText().toString();
                                if (!inputString.isEmpty()) {
                                    String correctString = inputString.replaceAll("\\s", "_");
                                    String hexString = convertStringToHex(correctString);
                                    Log.v("String to hex test", hexString + "");
                                    BigInteger decimalfromHex = new BigInteger(convertHexToDecimal(hexString));
                                    Log.v("hex to decimal test", decimalfromHex + "");
                                    String input = convertHexToString(hexString);
                                    Log.v("final output test", input);

                                    tempViewId = decimalfromHex;
                                    if (viewIdHolderForCheck.isEmpty()) {
                                        viewIdHolderForCheck.add(correctString);
                                    } else {
                                        for (int k = 0; k < viewIdHolderForCheck.size(); k++) {
                                            if (viewIdHolderForCheck.get(k).equals(correctString)) {
                                                SnackBar snackbar = new SnackBar(mActivity, "There is already a view with that ID on the layout. You must give each view a unique ID", "Retry", new OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        showDialog(SET_ID);
                                                    }
                                                });
                                                snackbar.setDismissTimer(20000);
                                                snackbar.show();
                                            }
                                        }
                                        viewIdHolderForCheck.add(correctString);
                                    }
                                    mRelativeLayout rl = (mRelativeLayout) currentLayoutView;
                                    if (viewTag == "TEXTVIEW") {
                                        mTextView tv = (mTextView) currentView;
                                        tv.setStringID(correctString);
                                        tv.setParentIdentifier(rl.getStringID());
                                        tv.setViewID(decimalfromHex.intValue());
                                    } else if (viewTag == "BUTTON") {
                                        mButton b = (mButton) currentView;
                                        b.setStringID(correctString);
                                        b.setParentIdentifier(rl.getStringID());
                                        b.setViewID(decimalfromHex.intValue());
                                    } else if (viewTag == "EDITTEXT") {
                                        mEditText et = (mEditText) currentView;
                                        et.setStringID(correctString);
                                        et.setParentIdentifier(rl.getStringID());
                                        et.setViewID(decimalfromHex.intValue());
                                    } else if (viewTag == "IMAGEVIEW") {
                                        mImageView iv = (mImageView) currentView;
                                        iv.setStringID(correctString);
                                        iv.setParentIdentifier(rl.getStringID());
                                        iv.setViewID(decimalfromHex.intValue());
                                    }  else if (viewTag == "RELATIVE_LAYOUT") {
                                        mRelativeLayout tempRL = (mRelativeLayout) currentView;
                                        tempRL.setStringID(correctString);
                                        tempRL.setParentIdentifier(rl.getStringID());
                                        tempRL.setViewID(decimalfromHex.intValue());
                                    }
                                    logCurrentViewIds(correctString, hexString);

                                    Intent intent = new Intent("com.Protofy.com.Protofy.protofy.VIEW_ADDED");
                                    getApplicationContext().sendBroadcast(intent);
                                    hideMenu();
                                } else {
                                    SnackBar snackbar = new SnackBar(mActivity, "Illegal input! please try again. Please note a view cannot have an empty ID", "Retry", new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            showDialog(SET_ID);
                                        }
                                    });
                                    snackbar.setDismissTimer(20000);
                                    snackbar.show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mRelativeLayout rl = (mRelativeLayout) currentLayoutView;
                                rl.getLayoutViews().removeView(rl.getLayoutViews().getSize() - 1);
                                if (rl.getLayoutViews().getSize() > 1)
                                    setDialogView(rl.getLayoutViews().getLast(), rl.getLayoutViews().getLast().getTag(), savedInstanceStateSecond);
                                else if (rl.getLayoutViews().getSize() == 1 || rl.getLayoutViews().getSize() == 0)
                                    setDialogView(baseLayout, "BASE_LAYOUT", savedInstanceStateSecond);
                            }
                        })
                        .show();
                return null;

            /*
            Dialog responsible for starting the XML exporting process
             */
            case GENERATE_XML:
                LayoutInflater xml = LayoutInflater.from(this);
                final View setXmlEntryView = xml.inflate(R.layout.generatexmllayout, null);
                final EditText setXmlEditText = (EditText) setXmlEntryView.findViewById(R.id.setXmlEditText);
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Name XML file")
                        .setView(setXmlEntryView)
                        .setPositiveButton("Generate File", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(hasStoragePermission) {
                                    currentXMLFileName = setXmlEditText.getText().toString().toLowerCase().replaceAll("\\s", "_");
                                    XMLParser buildXml = new XMLParser(getApplicationContext());
                                    buildXml.writeXML(currentXMLFileName, uiList);
                                } else
                                    checkPermissions();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();

            /*
            Dialog responsible for killing the whiteboard layout activity
             */
            case EXIT_WHITEBOARD:
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Exit Whiteboard?")
                        .setMessage("Are you sure you want to exit the whiteboard? If you have not generated the layout your progress will be lost.")
                        .setPositiveButton("Yes, I want to exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No, go back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();

            /*case SET_IMAGE_SCALING_TYPE:
                return new AlertDialogWrapper.Builder(this)
                        .setTitle("Set Image Scaling Type")
                        .setSingleChoiceItems(R.array.pref_default_image_scale_type_list_titles, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        if(viewTag == "IMAGEVIEW") {
                                            mImageView iv = new mImageView(mContext);
                                            iv.setViewScaleType(ImageView.ScaleType.MATRIX);
                                        }
                                        break;
                                    case 1:
                                        if(viewTag == "IMAGEVIEW") {
                                            mImageView iv = new mImageView(mContext);
                                            iv.setViewScaleType(ImageView.ScaleType.FIT_XY);
                                        }
                                        break;
                                    case 2:
                                        if(viewTag == "IMAGEVIEW") {
                                            mImageView iv = new mImageView(mContext);
                                            iv.setViewScaleType(ImageView.ScaleType.FIT_START);
                                        }
                                        break;
                                    case 3:
                                        if(viewTag == "IMAGEVIEW") {
                                            mImageView iv = new mImageView(mContext);
                                            iv.setViewScaleType(ImageView.ScaleType.FIT_CENTER);
                                        }
                                        break;
                                    case 4:
                                        if(viewTag == "IMAGEVIEW") {
                                            mImageView iv = new mImageView(mContext);
                                            iv.setViewScaleType(ImageView.ScaleType.FIT_END);
                                        }
                                        break;
                                    case 5:
                                        if(viewTag == "IMAGEVIEW") {
                                            mImageView iv = new mImageView(mContext);
                                            iv.setViewScaleType(ImageView.ScaleType.CENTER);
                                        }
                                        break;
                                    case 6:
                                        if(viewTag == "IMAGEVIEW") {
                                            mImageView iv = new mImageView(mContext);
                                            iv.setViewScaleType(ImageView.ScaleType.CENTER_CROP);
                                        }
                                        break;
                                    case 7:
                                        if(viewTag == "IMAGEVIEW") {
                                            mImageView iv = new mImageView(mContext);
                                            iv.setViewScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                        }
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();*/
        }
        return null;
    }

    /**
     * Checks if id already exists in the layout
     * @param idToCheck - id you want to check
     * @return - Returns false if there is a n ID incompatibility
     */
    public boolean checkId(String idToCheck) {
        for (int j = 0; j < viewIdHolderForCheck.size(); j++) {
            if (viewIdHolderForCheck.get(j).equals(idToCheck))
                return false;
            else if (j == viewIdHolderForCheck.size() - 1) {
                viewIdHolderForCheck.add(idToCheck);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all layout parameters that relate to a deleted view.
     */
    private void removeDeletedViewLayoutParams() {
        for (int i = 0; i < uiList.getSize(); i++) {
            mRelativeLayout tempRL = (mRelativeLayout) uiList.getView(i);
            UIelementList tempUIList = tempRL.getLayoutViews();
            for(int k = 0; k < tempUIList.getSize(); k++) {
                if (tempUIList.getView(k).getTag().equals("TEXTVIEW")) {
                    mTextView tv = (mTextView) tempUIList.getView(i);
                    for (int j = 0; j < tv.getLinkedViewStringIds().size(); j++) {
                        if (tv.getLinkedViewStringIds().get(j).equals(viewDeletedStringId)) {
                            tv.getLinkedViewStringIds().remove(j);
                            tv.getLinkedViews().remove(j);
                            tv.getLinkedViewLayoutParams().remove(j);
                        }
                    }
                } else if (tempUIList.getView(k).getTag().equals("BUTTON")) {
                    mButton b = (mButton) tempUIList.getView(i);
                    for (int j = 0; j < b.getLinkedViewStringIds().size(); j++) {
                        if (b.getLinkedViewStringIds().get(j).equals(viewDeletedStringId)) {
                            b.getLinkedViewStringIds().remove(j);
                            b.getLinkedViews().remove(j);
                            b.getLinkedViewLayoutParams().remove(j);
                        }
                    }
                } else if (tempUIList.getView(k).getTag().equals("EDITTEXT")) {
                    mEditText ed = (mEditText) tempUIList.getView(i);
                    for (int j = 0; j < ed.getLinkedViewStringIds().size(); j++) {
                        if (ed.getLinkedViewStringIds().get(j).equals(viewDeletedStringId)) {
                            ed.getLinkedViewStringIds().remove(j);
                            ed.getLinkedViews().remove(j);
                            ed.getLinkedViewLayoutParams().remove(j);
                        }
                    }
                } else if (tempUIList.getView(k).getTag().equals("IMAGEVIEW")) {
                    mImageView iv = (mImageView) tempUIList.getView(i);
                    for (int j = 0; j < iv.getLinkedViewStringIds().size(); j++) {
                        if (iv.getLinkedViewStringIds().get(j).equals(viewDeletedStringId)) {
                            iv.getLinkedViewStringIds().remove(j);
                            iv.getLinkedViews().remove(j);
                            iv.getLinkedViewLayoutParams().remove(j);
                        }
                    }
                }
            }

        }
    }

    /**
     * Converts string into a hex representation of said string
     * @param str - String to convert
     * @return - Hex conversion as a String
     */
    public String convertStringToHex(String str) {

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString();
    }

    /**
     * Converts a string representation of HEX into a Decimal representation
     * @param hex - HEX string to convert
     * @return - Double conversion as a String
     */
    public String convertHexToDecimal(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        System.out.println("Decimal : " + temp.toString());

        return temp.toString();
    }

    /**
     * Converts HEX into a readable String. (Converts IDs back into human readable)
     * @param hex - HEX you want to convert
     * @return - Human readable string conversion
     */
    public String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        System.out.println("Decimal : " + temp.toString());

        return sb.toString();
    }

    /**
     * Forcibly redraws all views in the whiteboard.
     */
    protected void reDrawAllViews() {
        for (int j = 0; j < uiList.getSize(); j++) {
            mRelativeLayout rl = (mRelativeLayout) uiList.getView(j);
            for (int i = 0; i < rl.getLayoutViews().getSize(); i++) {
                if (rl.getLayoutViews().getView(i).getTag().equals("TEXTVIEW")) {
                    mTextView tv = (mTextView) rl.getLayoutViews().getView(i);
                    tv.redrawView();
                } else if (rl.getLayoutViews().getView(i).getTag().equals("BUTTON")) {
                    mButton b = (mButton) rl.getLayoutViews().getView(i);
                    b.redrawView();
                } else if (rl.getLayoutViews().getView(i).getTag().equals("EDITTEXT")) {
                    mEditText ed = (mEditText) rl.getLayoutViews().getView(i);
                    ed.redrawView();
                } else if (rl.getLayoutViews().getView(i).getTag().equals("IMAGEVIEW")) {
                    mImageView iv = (mImageView) rl.getLayoutViews().getView(i);
                    iv.redrawView();
                } else if (rl.getLayoutViews().getView(i).getTag().equals("RELATIVE_LAYOUT")) {
                    mRelativeLayout tempRL = (mRelativeLayout) rl.getLayoutViews().getView(i);
                    tempRL.redrawView();
                }
            }
        }

    }

    /**
     * Update tutorial state, IE progress tutorial to next step and save tutorial state into a preference.
     */
    private void updateTutorialState() {
        if (tutorialState) {
            AppPreferences app = new AppPreferences(this);
            app.saveTutorialState(false);
        }
        tutorialState = false;
    }

    /**
     * Update tooltip and ensure it's always brought to the front so it's not hidden behind views.
     */
    protected void updateViewToolTip() {
        mToolTipFrameLayout.removeAllViews();
        ToolTipView mToolTip = mToolTipFrameLayout.showToolTipForView(toolTip, currentView);
        baseLayout.bringChildToFront(mToolTipFrameLayout);
    }

    /**
     * Helper function to apply parent layout parameters to a child view.
     * @param LayoutParam - Parameter to add
     */
    protected void addParentLayoutParamsToView(int LayoutParam) {
        if (viewTag == "TEXTVIEW") {
            mTextView tv = (mTextView) currentView;
            tv.addViewLayoutParam(LayoutParam, null, -1, returnParentView(tv));
        } else if (viewTag == "BUTTON") {
            mButton b = (mButton) currentView;
            b.addViewLayoutParam(LayoutParam, null, -1, returnParentView(b));
        } else if (viewTag == "EDITTEXT") {
            mEditText et = (mEditText) currentView;
            et.addViewLayoutParam(LayoutParam, null, -1, returnParentView(et));
        } else if (viewTag == "IMAGEVIEW") {
            mImageView iv = (mImageView) currentView;
            iv.addViewLayoutParam(LayoutParam, null, -1, returnParentView(iv));
        }
    }

    /**
     * Helper function to apply layout parameters to our custom TextView class.
     * @param viewToBeMoved - Preview TextView and view you want to apply layout params upon.
     * @param viewStringId - String representation of ID
     * @param v - View associated with new layout parameter
     */
    protected void addLinkedLayoutParamsToTextView(mTextView viewToBeMoved, String viewStringId, View v) {
        viewToBeMoved.setPreview(true);

        if (layoutPositioningSelection == PLACE_ABOVE_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ABOVE, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_BELOW_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.BELOW, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_LEFT_OF_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.LEFT_OF, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_RIGHT_OF_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.RIGHT_OF, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_BASELINE)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_BASELINE, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_END)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_END, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_LEFT_EDGES)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_LEFT, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_RIGHT_EDGES)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_RIGHT, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_TOP)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_TOP, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_START)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_START, viewStringId, v.getId(), returnParentView(viewToBeMoved));
    }

    /**
     * Helper function to apply layout parameters to our custom Button class.
     * @param viewToBeMoved - Preview TextView and view you want to apply layout params upon.
     * @param viewStringId - String representation of ID
     * @param v - View associated with new layout parameter
     */
    protected void addLinkedLayoutParamsToButton(mButton viewToBeMoved, String viewStringId, View v) {
        viewToBeMoved.setPreview(true);

        if (layoutPositioningSelection == PLACE_ABOVE_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ABOVE, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_BELOW_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.BELOW, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_LEFT_OF_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.LEFT_OF, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_RIGHT_OF_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.RIGHT_OF, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_BASELINE)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_BASELINE, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_END)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_END, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_LEFT_EDGES)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_LEFT, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_RIGHT_EDGES)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_RIGHT, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_TOP)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_TOP, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_START)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_START, viewStringId, v.getId(), returnParentView(viewToBeMoved));
    }

    /**
     * Helper function to apply layout parameters to our custom EditText class.
     * @param viewToBeMoved - Preview TextView and view you want to apply layout params upon.
     * @param viewStringId - String representation of ID
     * @param v - View associated with new layout parameter
     */
    protected void addLinkedLayoutParamsToEditText(mEditText viewToBeMoved, String viewStringId, View v) {
        viewToBeMoved.setPreview(true);

        if (layoutPositioningSelection == PLACE_ABOVE_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ABOVE, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_BELOW_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.BELOW, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_LEFT_OF_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.LEFT_OF, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_RIGHT_OF_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.RIGHT_OF, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_BASELINE)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_BASELINE, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_END)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_END, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_LEFT_EDGES)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_LEFT, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_RIGHT_EDGES)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_RIGHT, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_TOP)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_TOP, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_START)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_START, viewStringId, v.getId(), returnParentView(viewToBeMoved));
    }

    /**
     * Helper function to apply layout parameters to our custom ImageView class.
     * @param viewToBeMoved - Preview TextView and view you want to apply layout params upon.
     * @param viewStringId - String representation of ID
     * @param v - View associated with new layout parameter
     */
    protected void addLinkedLayoutParamsToImageView(mImageView viewToBeMoved, String viewStringId, View v) {
        viewToBeMoved.setPreview(true);

        if (layoutPositioningSelection == PLACE_ABOVE_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ABOVE, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_BELOW_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.BELOW, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_LEFT_OF_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.LEFT_OF, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == PLACE_RIGHT_OF_VIEW)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.RIGHT_OF, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_BASELINE)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_BASELINE, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_END)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_END, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_LEFT_EDGES)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_LEFT, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_RIGHT_EDGES)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_RIGHT, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_TOP)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_TOP, viewStringId, v.getId(), returnParentView(viewToBeMoved));
        if (layoutPositioningSelection == ALIGN_START)
            viewToBeMoved.addViewLayoutParam(RelativeLayout.ALIGN_START, viewStringId, v.getId(), returnParentView(viewToBeMoved));
    }

    /**
     * Hold user selection of selected layout parameter they want removed. This allows us to carry over to the confirm dialog.
     * @param layoutParamName - Name of parameter to be removed
     * @param choiceIndex - Location reference of layout parameter in list
     */
    private void prepForUserParamRemoval(String layoutParamName, int choiceIndex) {
        stringParam = layoutParamName;
        paramData = choiceIndex;
    }

    /**
     * Setting to stop checking color values (IE: color picker isn't displayed)
     */
    private void stopCheckingColor() {
        setViewColor = false;
    }


    /**
     * Dialog click listener logic. Will display option dialogs based on clicked view and options selected.
     * @param id -
     */
    @Override
    public void onItemClick(int id) {

        if (viewTag.equals("BASE_LAYOUT")) {
            switch (id) {
                case 1:
                    showDialog(ADD_VIEW);
                    break;
                case 2:
                    showDialog(COLOR);
                    break;
                case 3:
                    if(baseLayout.hasBackgroundImage()) {
                        showDialog(SET_BACKGROUND_TINT);
                    } else {
                        loadImagefromGallery(baseLayout);
                    }
                    break;
                case 4:
                    if (baseLayout.hasBackgroundImage()) {
                        loadImagefromGallery(baseLayout);
                    } else {
                        showDialog(SET_PADDING);
                    }
                    break;
                case 5:
                    if(baseLayout.hasBackgroundImage()) {
                        showDialog(SET_PADDING);
                    }
                    break;
            }
        } else if (viewTag.equals("RELATIVE_LAYOUT")) {
            mRelativeLayout tempRL = (mRelativeLayout) currentView;
            switch (id) {
                case 1:
                    showDialog(ADD_VIEW);
                    break;
                case 2:
                    showDialog(COLOR);
                    break;
                case 3:
                    if(tempRL.hasBackgroundImage()) {
                        showDialog(SET_BACKGROUND_TINT);
                    } else {
                        loadImagefromGallery(tempRL);
                    }
                    break;
                case 4:
                    if (tempRL.hasBackgroundImage()) {
                        loadImagefromGallery(tempRL);
                    } else {
                        showDialog(SET_PADDING);
                    }
                    break;
                case 5:
                    if(tempRL.hasBackgroundImage()) {
                        showDialog(SET_PADDING);
                    } else {
                        showDialog(MARGINS_MENU);
                    }
                    break;
                case 6:
                    if(tempRL.hasBackgroundImage()) {
                        showDialog(MARGINS_MENU);
                    } else {
                        showDialog(SET_VIEW_SIZE);
                    }
                    break;
                case 7:
                    if(tempRL.hasBackgroundImage()) {
                        showDialog(SET_VIEW_SIZE);
                    } else {
                        showDialog(LAYOUT_PARENT_POSITIONING);
                    }
                    break;
                case 8:
                    if(tempRL.hasBackgroundImage()) {
                        showDialog(LAYOUT_PARENT_POSITIONING);
                    } else {
                        showDialog(LAYOUT_OTHER_VIEW_POSITIONING);
                    }
                    break;
                case 9:
                    if(tempRL.hasBackgroundImage()) {
                        showDialog(LAYOUT_OTHER_VIEW_POSITIONING);
                    } else {
                        showDialog(REMOVE_PARAMS);
                    }
                    break;
                case 10:
                    if(tempRL.hasBackgroundImage()) {
                        showDialog(REMOVE_PARAMS);
                    } else {
                        showDialog(REMOVE_VIEW);
                    }
                    break;
                case 11:
                    if(tempRL.hasBackgroundImage()) {
                        showDialog(REMOVE_VIEW);
                    }
                    break;
            }
        }
        else if (viewTag.equals("IMAGEVIEW")) {
            switch (id) {
                case 0:
                    if(menuOnDisplay.equals("Layout")) {
                        hideMenuKeepToolTip();
                        removeAllLeftItems();
                        removeAllTopItems();
                        menuGraphics = getResources().obtainTypedArray(R.array.appearanceMenuItemsImageView);
                        for (int i = 0; i < menuarray.length; i++) {
                            TextItem textItem;
                            if (i == 0)
                                textItem = new TextItem(this, i, menuarray[i], Color.parseColor("#24aaf2"));
                            else {
                                textItem = new TextItem(this, i, menuarray[i], Color.WHITE);
                                textItem.setAlpha((float) 0.3);
                            }
                            addTopItem(textItem);
                        }
                        for (int i = 0; i < menuGraphics.length(); i++) {
                            IconItem iconItem = new IconItem(this, i + 2, menuGraphics.getResourceId(i, 0));
                            addLeftItem(iconItem);
                        }
                        menuOnDisplay = "Appearance";
                        menuGraphics.recycle();
                    }
                    showMenu();
                    break;

                case 1:
                    menuGraphics = getResources().obtainTypedArray(R.array.layoutParamsMenuItems);
                    if (menuOnDisplay.equals("Appearance")) {
                        hideMenuKeepToolTip();
                        removeAllLeftItems();
                        removeAllTopItems();
                        for (int i = 0; i < menuarray.length; i++) {
                            TextItem textItem;
                            if (i == 1)
                                textItem = new TextItem(this, i, menuarray[i], Color.parseColor("#24aaf2"));
                            else {
                                textItem = new TextItem(this, i, menuarray[i], Color.WHITE);
                                textItem.setAlpha((float) 0.3);
                            }
                            addTopItem(textItem);
                        }

                        for (int i = 0; i < menuGraphics.length(); i++) {
                            IconItem iconItem = new IconItem(this, i + 2, menuGraphics.getResourceId(i, 0));
                            addLeftItem(iconItem);
                        }
                        menuOnDisplay = "Layout";
                        menuGraphics.recycle();
                    }
                    showMenu();
                    break;

                case 2:
                    if(menuOnDisplay.equals("Appearance")){
                        loadImagefromGallery(currentView);
                    } else if (menuOnDisplay.equals("Layout")) {
                        showDialog(LAYOUT_PARENT_POSITIONING);
                    }
                    break;

                case 3:
                    if(menuOnDisplay.equals("Appearance")) {
                        showDialog(SET_BACKGROUND_TINT);
                    } else if (menuOnDisplay.equals("Layout")) {
                        showDialog(LAYOUT_OTHER_VIEW_POSITIONING);
                    }
                    break;

                case 4:
                    if(menuOnDisplay.equals("Appearance")) {
                        showDialog(SET_VIEW_SIZE);
                    } else if (menuOnDisplay.equals("Layout")) {
                        showDialog(MARGINS_MENU);
                    }
                    break;

                case 5:
                    if(menuOnDisplay.equals("Appearance")) {
                        showDialog(SET_PADDING);
                    } else if(menuOnDisplay.equals("Layout")) {
                        showDialog(PARAMETERS);
                    }
                    break;

                case 6:
                    if(menuOnDisplay.equals("Appearance")) {
                        showDialog(SET_VISIBILITY);
                    } else if(menuOnDisplay.equals("Layout")) {
                        showDialog(REMOVE_PARAMS);
                    }
                    break;

                case 7:
                   // if (menuOnDisplay.equals("Appearance"))
                   //     showDialog(SET_CHILD_FRONT_BACK);
                    if(menuOnDisplay.equals("Layout")) {
                        showDialog(REMOVE_VIEW);
                    }
                    break;
            }
        }
        else if (viewTag.equals("BUTTON") || viewTag.equals("TEXTVIEW") || viewTag.equals("EDITTEXT")) {
            switch (id) {
                case 0:
                    if (menuOnDisplay.equals("Appearance") || menuOnDisplay.equals("Layout")) {
                        hideMenuKeepToolTip();
                        removeAllLeftItems();
                        removeAllTopItems();
                        menuGraphics = getResources().obtainTypedArray(R.array.nonEditTextTextItems);
                        for (int i = 0; i < menuarray.length; i++) {
                            TextItem textItem;
                            if (i == 0)
                                textItem = new TextItem(this, i, menuarray[i], Color.parseColor("#24aaf2"));
                            else {
                                textItem = new TextItem(this, i, menuarray[i], Color.WHITE);
                                textItem.setAlpha((float) 0.3);
                            }
                            addTopItem(textItem);
                        }

                        for (int i = 0; i < menuGraphics.length(); i++) {
                            IconItem iconItem = new IconItem(this, i + 3, menuGraphics.getResourceId(i, 0));
                            addLeftItem(iconItem);
                        }
                        menuOnDisplay = "Text";
                        menuGraphics.recycle();
                    }
                    showMenu();
                    break;
                case 1:
                    menuGraphics = getResources().obtainTypedArray(R.array.appearanceMenuItems);
                    if (menuOnDisplay.equals("Text") || menuOnDisplay.equals("Layout")) {
                        hideMenuKeepToolTip();
                        removeAllLeftItems();
                        removeAllTopItems();
                        for (int i = 0; i < menuarray.length; i++) {
                            TextItem textItem;
                            if (i == 1)
                                textItem = new TextItem(this, i, menuarray[i], Color.parseColor("#24aaf2"));
                            else {
                                textItem = new TextItem(this, i, menuarray[i], Color.WHITE);
                                textItem.setAlpha((float) 0.3);
                            }
                            addTopItem(textItem);
                        }

                        for (int i = 0; i < menuGraphics.length(); i++) {
                            IconItem iconItem = new IconItem(this, i + 3, menuGraphics.getResourceId(i, 0));
                            addLeftItem(iconItem);
                        }
                        menuOnDisplay = "Appearance";
                        menuGraphics.recycle();
                    }
                    showMenu();
                    break;

                case 2:
                    menuGraphics = getResources().obtainTypedArray(R.array.layoutParamsMenuItems);
                    if (menuOnDisplay.equals("Text") || menuOnDisplay.equals("Appearance")) {
                        hideMenuKeepToolTip();
                        removeAllLeftItems();
                        removeAllTopItems();
                        for (int i = 0; i < menuarray.length; i++) {
                            TextItem textItem;
                            if (i == 2)
                                textItem = new TextItem(this, i, menuarray[i], Color.parseColor("#24aaf2"));
                            else {
                                textItem = new TextItem(this, i, menuarray[i], Color.WHITE);
                                textItem.setAlpha((float) 0.3);
                            }
                            addTopItem(textItem);
                        }

                        for (int i = 0; i < menuGraphics.length(); i++) {
                            IconItem iconItem = new IconItem(this, i + 3, menuGraphics.getResourceId(i, 0));
                            addLeftItem(iconItem);
                        }
                        menuOnDisplay = "Layout";
                        menuGraphics.recycle();
                    }
                    showMenu();
                    break;

                case 3:
                    if (menuOnDisplay.equals("Text"))
                        if (viewTag.equals("EDITTEXT"))
                            showDialog(SET_HINT);
                        else
                            showDialog(SET_TEXT);
                    if (menuOnDisplay.equals("Appearance"))
                        showDialog(COLOR);
                    if (menuOnDisplay.equals("Layout"))
                        showDialog(LAYOUT_PARENT_POSITIONING);
                    break;
                case 4:
                    if (menuOnDisplay.equals("Text"))
                        showDialog(TEXT_STYLE);
                    if (menuOnDisplay.equals("Appearance"))
                        showDialog(SET_PADDING);
                    if (menuOnDisplay.equals("Layout"))
                        showDialog(LAYOUT_OTHER_VIEW_POSITIONING);
                    break;
                case 5:
                    if (menuOnDisplay.equals("Text"))
                        showDialog(SET_TEXT_SIZE);
                    if (menuOnDisplay.equals("Appearance")){
                        showDialog(SET_VISIBILITY);
                    }
                    if (menuOnDisplay.equals("Layout"))
                        showDialog(MARGINS_MENU);
                    break;
                case 6:
                    if (menuOnDisplay.equals("Text"))
                        showDialog(SET_LINE_SPACING);
                    if (menuOnDisplay.equals("Appearance"))
                        showDialog(SET_VIEW_SIZE);
                    if (menuOnDisplay.equals("Layout"))
                        showDialog(PARAMETERS);
                    break;
                case 7:
                    //if (menuOnDisplay.equals("Appearance"))
                    //    showDialog(SET_CHILD_FRONT_BACK);
                    if (menuOnDisplay.equals("Layout"))
                        showDialog(REMOVE_PARAMS);
                    break;
                case 8:
                    if (menuOnDisplay.equals("Layout"))
                        showDialog(REMOVE_VIEW);
                    break;
            }
        }
    }

    /**
     * Get current parent to the view the user is working on. If it's a layout view, layout view will be it's own parent.
     * @return
     */
    protected View getCurrentLayoutView() {
        return currentLayoutView;
    }

    /**
     * Use already installed media app to grab images for backgrounds.
     * @param view - View image needs to be applied too.
     */
    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    /**
     * Handle image app response and apply images to view
     * @param requestCode - Type of request
     * @param resultCode - Result of request
     * @param data - Actual data of intent. In this case, an image.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                if(viewTag.equals("IMAGEVIEW")){
                    try {
                        mImageView iv = (mImageView) currentView;
                        Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        iv.setViewImage(b);
                    } catch (Exception e) {
                        Log.v("Exception", e.getMessage());
                    }
                } else if (viewTag.equals("BASE_LAYOUT")) {
                    try {
                        if(baseLayout.hasBackgroundImage()){
                        } else {
                            menuGraphics = getResources().obtainTypedArray(R.array.baseLayoutIconItemsImage);
                            reDeclareLeftMenuItems();
                        }
                        Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        baseLayout.setViewBackgroundImage(b);
                    } catch (Exception e) {
                        Log.v("Exception", e.getMessage());
                    }
                } else if (viewTag.equals("RELATIVE_LAYOUT")) {
                    try {
                        mRelativeLayout tempRL = (mRelativeLayout) currentView;
                        if(tempRL.hasBackgroundImage()){
                        } else {
                            menuGraphics = getResources().obtainTypedArray(R.array.relativeLayoutIconItemsImage);
                            reDeclareLeftMenuItems();
                        }
                        Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        tempRL.setViewBackgroundImage(b);
                    } catch (Exception e) {
                        Log.v("Exception", e.getMessage());
                    }
                }
                }
            }
        }

    /**
     * Rebuilt left menu items
     */
    private void reDeclareLeftMenuItems () {
        removeAllLeftItems();
        for (int i = 0; i < menuGraphics.length(); i++) {
            IconItem iconItem = new IconItem(this, i + 1, menuGraphics.getResourceId(i, 0));
            addLeftItem(iconItem);
        }
    }

    /**
     * Check for user permissions
     */
    protected void checkPermissions() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    /**
     * Helper function to return parent view and all of it's children of selected TextView
     * @param tv - TextView to get parent ViewGroup of
     * @return - Returns whole ViewGroup TextView belongs too as a custom UIelementList
     */
    private UIelementList returnParentView(mTextView tv) {
        String tempId = tv.getParentIdentifier();
        for(int i = 0; i < uiList.getSize(); i++) {
            mRelativeLayout tempLayout = (mRelativeLayout) uiList.getView(i);
            if(tempId.equals(tempLayout.getStringID()))
                return tempLayout.getLayoutViews();
            else
                return null;
        }
        return null;
    }
    /**
     * Helper function to return parent view and all of it's children of selected Button
     * @param b - Button to get parent ViewGroup of
     * @return - Returns whole ViewGroup Button belongs too as a custom UIelementList
     */
    private UIelementList returnParentView(mButton b) {
        String tempId = b.getParentIdentifier();
        Log.v("Parent ID", tempId);
        for(int i = 0; i < uiList.getSize(); i++) {
            mRelativeLayout tempLayout = (mRelativeLayout) uiList.getView(i);
            if(tempId.equals(tempLayout.getStringID()))
                return tempLayout.getLayoutViews();
            else
                return null;
        }
        return null;
    }
    /**
     * Helper function to return parent view and all of it's children of selected EditText
     * @param ed - EditText to get parent ViewGroup of
     * @return - Returns whole ViewGroup EditText belongs too as a custom UIelementList
     */
    private UIelementList returnParentView(mEditText ed) {
        String tempId = ed.getParentIdentifier();
        for(int i = 0; i < uiList.getSize(); i++) {
            mRelativeLayout tempLayout = (mRelativeLayout) uiList.getView(i);
            if(tempId.equals(tempLayout.getStringID()))
                return tempLayout.getLayoutViews();
            else
                return null;
        }
        return null;
    }
    /**
     * Helper function to return parent view and all of it's children of selected ImageView
     * @param iv - ImageView to get parent ViewGroup of
     * @return - Returns whole ViewGroup ImageView belongs too as a custom UIelementList
     */
    private UIelementList returnParentView(mImageView iv) {
        String tempId = iv.getParentIdentifier();
        for(int i = 0; i < uiList.getSize(); i++) {
            mRelativeLayout tempLayout = (mRelativeLayout) uiList.getView(i);
            if(tempId.equals(tempLayout.getStringID()))
                return tempLayout.getLayoutViews();
            else
                return null;
        }
        return null;
    }
}