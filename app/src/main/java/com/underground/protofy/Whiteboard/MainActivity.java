package com.underground.protofy.Whiteboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Protofy.protofy.R;
import com.gc.bordermenu.views.IconItem;
import com.gc.bordermenu.views.TextItem;
import com.gc.materialdesign.widgets.SnackBar;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;
import com.underground.protofy.CustomDataStructures.UIelementList;
import com.underground.protofy.Preferences.AppPreferences;
import com.underground.protofy.Preferences.SettingsActivity;
import com.underground.protofy.Views.mButton;
import com.underground.protofy.Views.mEditText;
import com.underground.protofy.Views.mImageView;
import com.underground.protofy.Views.mRelativeLayout;
import com.underground.protofy.Views.mSeekBar;
import com.underground.protofy.Views.mTextView;

import java.util.ArrayList;

import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTipIt;
import tourguide.tourguide.TourGuide;


public class MainActivity extends Dialogs {
    View v;
    //private View selected_item = null;
    int j = 0;
    Animation animation;
    private boolean firstStart = true;
    private boolean viewTooltipDisplayed = false;
    private ToolTipIt mToolTip;
    private boolean toolTipAnchorRemoved = false;
    private BroadcastReceiver layoutChangedReceiver, viewAddedReceiver, layoutConflictViewOffReceiver, xmlSuccessReceiver;


    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);                                                    //set Content Panel to the scrollView + Linear combo

        mActivity = this;
        appPrefs = new AppPreferences(mActivity);
        tutorialState = appPrefs.getTutorialState();
        mContext = this;

        //SCREEN RESOLUTION CONTROL
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Prompt user to storage permission if on SDK 23 or greater.
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        } else {
            hasStoragePermission = true;
        }


        //If Activity's first start initialize UI data structure
        if (firstStart) {
            savedInstanceStateSecond = savedInstanceState;                      //Create copy of onCreate Bundle
            //ScrollView sv = new ScrollView(this);								//Creates new ScrollView as layout place-mat
            baseLayout = (mRelativeLayout) findViewById(R.id.baseLayout);       //Creates new base layout (attach to underlying ScrollView)
            //baseLinearLayout.setOrientation(LinearLayout.VERTICAL);	        //Lock Layout orientation to VERTICAL
            //Apply Default Layout Parameters
            //sv.setLayoutParams(new ScrollView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            //sv.addView(baseLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)); //Attach BaseLayout to ScrollView(will be base board layout)

            baseLayout.setMinimumHeight(metrics.heightPixels);                  // Dynamically match base view to screen height
            baseLayout.setMinimumWidth(metrics.widthPixels);                    // Dynamically match base view to screen width

            baseLayout.setStringID("baseLayout");                               // Set layout identifier so we know it's the base

            LinearLayout headerbar = (LinearLayout) findViewById(R.id.masterParent);
            ImageView generateButton = (ImageView) headerbar.findViewById(R.id.generateXMLButton);

            DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(this, generateButton);
            final DroppyMenuPopup droppyMenu;

            // Add normal items (text only)
            droppyBuilder.addMenuItem(new DroppyMenuItem("Submit Feedback"));
            droppyBuilder.addMenuItem(new DroppyMenuItem("Generate XML"));
            droppyBuilder.addMenuItem(new DroppyMenuItem("Settings"));

            // Set Callback handler
            droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
                @Override
                public void call(View v, int id) {
                    Log.d("Clicked on ", String.valueOf(id));
                    switch (id){
                        case 0:
                            String[] TO = {"gallimoredakota@gmail.com"};
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("message/rfc822");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback submission");
                            try {
                                startActivity(Intent.createChooser(emailIntent, "Send feedback..."));
                            }
                            catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1:
                            showDialog(GENERATE_XML);
                            break;

                        case 2:
                            Intent intent = new Intent(mActivity, SettingsActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });

            droppyMenu = droppyBuilder.build();

            generateButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    droppyMenu.show();
                }
            });

            // Initialize UI view tracking List
            uiList = new UIelementList();
            uiList.initializeUIdataStructure();

            // Add our base layout as first view into view tracker
            uiList.addView(baseLayout, "BASE_LAYOUT");


            mToolTipFrameLayout = (ToolTipRelativeLayout) findViewById(R.id.activity_main_tooltipRelativeLayout);
            setToolTipRemoval(mToolTipFrameLayout);
            setLayoutIndexValue(baseLayout, savedInstanceStateSecond);

            //baseLayout.setBackground(getResources().getDrawable(R.drawable.doubleclick));

            // Initialize List to keep track of all view Ids before exporting.
            viewIdHolderForCheck = new ArrayList<>();

            // Set base to clickable to make it editable
            baseLayout.setClickable(true);

            animation = new TranslateAnimation(0f, 0f, 100f, 0f);
            animation.setDuration(100);
            animation.setFillAfter(true);
            animation.setInterpolator(new BounceInterpolator());

            final TextView toolTipAnchorView = (TextView) findViewById(R.id.OpeningToolTipAnchor);
            tutorialCompleted = false;

            // Since this is first startup  - Set up and display greeting Tutorial ToolTip
            mToolTip = new ToolTipIt()
                    .setTitle("Lets get started")
                    .setDescription("Double-Click anywhere")
                    .setTextColor(Color.parseColor("#bdc3c7"))
                    .setBackgroundColor(Color.parseColor("#e74c3c"))
                    .setGravity(Gravity.CENTER)
                    .setShadow(true)
                    .setEnterAnimation(animation);
            if (!tutorialState) {
                mTourGuideHandler = TourGuide.init(mActivity).with(TourGuide.Technique.Click)
                        .setPointer(new Pointer())
                        .setToolTip(new ToolTipIt()
                                .setTitle("The next great UI")
                                .setDescription("Is just a double-Click away")
                                .setTextColor(Color.parseColor("#bdc3c7"))
                                .setBackgroundColor(Color.parseColor("#e74c3c"))
                                .setGravity(Gravity.CENTER)
                                .setShadow(true)
                                .setEnterAnimation(animation))
                        .playOn(toolTipAnchorView);
            } else {
                mTourGuideHandler = TourGuide.init(mActivity).with(TourGuide.Technique.Click)
                        .setPointer(new Pointer())
                        .setToolTip(new ToolTipIt()
                                .setTitle("This is your Whiteboard")
                                .setDescription("The Whiteboard is where all the action happens, Use it to craft your UI prototype. Let's get familiar with the interface. Double-Click to continue (this will open the edge menu)")
                                .setTextColor(Color.parseColor("#bdc3c7"))
                                .setBackgroundColor(Color.parseColor("#24aaf2"))
                                .setGravity(Gravity.CENTER)
                                .setShadow(true)
                                .setEnterAnimation(animation))
                        .playOn(toolTipAnchorView);

                mWhiteboardSettingsGuideHandler = TourGuide.init(mActivity).with(TourGuide.Technique.Click)
                        .setPointer(new Pointer())
                        .setToolTip(new ToolTipIt()
                                .setTitle("Header menu")
                                .setDescription("The header menu will allow you to submit feedback, export/create the XML file, and go to the app settings page")
                                .setTextColor(Color.parseColor("#bdc3c7"))
                                .setBackgroundColor(Color.parseColor("#24aaf2"))
                                .setGravity(Gravity.CENTER)
                                .setShadow(true)
                                .setEnterAnimation(animation))
                        .playOn(generateButton);
            }

            //Set up The selected View ToolTip
            toolTip = new com.nhaarman.supertooltips.ToolTip()
                    .withText("Selected View")
                    .withColor(Color.parseColor("#24aaf2"))
                    .withShadow()
                    .withAnimationType(ToolTip.AnimationType.FROM_TOP);

            baseLayout.setOnClickListener(new DoubleClickListener() {
                @Override
                public void onSingleClick(View v) {

                }

                @Override
                public void onDoubleClick(View view) {
                    mTourGuideHandler.cleanUp();
                    if(tutorialState)
                        mWhiteboardSettingsGuideHandler.cleanUp();
                    setIndexValue(view, view.getTag(), savedInstanceStateSecond);                //pass the View and the views tag to the Dialog class
                    setLayoutIndexValue(view, savedInstanceStateSecond);
                    initBaseLayoutMenuFragment();

                    //Remove Welcoming Tutorial ToolTip and remove anchoring view for ToolTip for performance

                    if (!toolTipAnchorRemoved) {
                        baseLayout.removeView(toolTipAnchorView);
                        setToolTipAnchorRemoved(true);
                    }
                    firstStart = false;
                }
            });
        }
        //If Activity has been started before, we need to force a redraw.
        if (!firstStart) {
            reloadUI();
        }


        /*
            Initialize and register broadcast receivers to the Activity so we know when UI redraw needs to occur.
         */

        layoutChangedReceiver = new LayoutChangedReceiver();
        viewAddedReceiver = new ViewAddedReceiver();
        layoutConflictViewOffReceiver = new LayoutConflictViewOffScreenReceiver();
        xmlSuccessReceiver = new XmlSuccessReceiver();

        IntentFilter layoutChangedFilter = new IntentFilter();
        IntentFilter viewAddedFilter = new IntentFilter();
        IntentFilter layoutConflictViewOffScreenFilter = new IntentFilter();
        IntentFilter xmlSuccessFilter = new IntentFilter();

        layoutChangedFilter.addAction("com.Protofy.com.Protofy.protofy.LAYOUT_CHANGED");
        viewAddedFilter.addAction("com.Protofy.com.Protofy.protofy.VIEW_ADDED");
        layoutConflictViewOffScreenFilter.addAction("com.Protofy.com.Protofy.protofy.LAYOUT_CONFLICT_VIEW_OUTSIDE_SCREEN");
        xmlSuccessFilter.addAction("com.Protofy.com.Protofy.protofy.XML_SUCCESS");

        registerReceiver(layoutChangedReceiver, layoutChangedFilter);
        registerReceiver(viewAddedReceiver, viewAddedFilter);
        registerReceiver(layoutConflictViewOffReceiver, layoutConflictViewOffScreenFilter);
        registerReceiver(xmlSuccessReceiver, xmlSuccessFilter);
    }

    /**
     * Remove customization tooltip from specific view.
     * @param value - true or false.
     */
    private void setToolTipAnchorRemoved(boolean value) {
        toolTipAnchorRemoved = value;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Check to see if menu is still open on Resume if it's been closed then remove selected View ToolTip and reload the UI
        // TODO: ensure broadcast receivers are still linked to the activity.
        if (!isMenuShowed())
            mToolTipFrameLayout.removeAllViews();
        reloadUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //When Activity gets destroyed clean up and unregister Broadcast Receivers
        unregisterReceiver(layoutChangedReceiver);
        unregisterReceiver(viewAddedReceiver);
        unregisterReceiver(layoutConflictViewOffReceiver);
        unregisterReceiver(xmlSuccessReceiver);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoptions, menu);        //inflate android menu from application resource
        return true;
    }*/

    /**
     * Forcibly redraws the app UI by looping through the View tracker. Can be called from a non-UI thread.
     */
    public void reloadUI() {
        runOnUiThread(new Runnable() {
            public void run() {

                // UI code goes here
                reDrawAllViews();
                for(int i = 0; i < uiList.getSize(); i++) {
                    uiList.getView(i).requestLayout();
                    Log.v("uiList ID", "" + uiList.getView(i).getId());
                    Log.v("currentView ID", ""+getCurrentLayoutView().getId());
                        mRelativeLayout tempRelativeLayout = (mRelativeLayout) getCurrentLayoutView();
                        if(!firstStart) {
                            v = tempRelativeLayout.getLayoutViews().getLast();

                            if (v.getParent() == null) {
                                Log.v("Parent Mapping: ", "View has no parent");
                                tempRelativeLayout.addViewToLayout(v);                              //add view to UI thread layout
                                v.setClickable(true);


                                if (v.getTag() == "SPINNER" || v.getTag() == "SEEK") {
                                    if (v.getTag() == "SPINNER") {
                                        v.setOnLongClickListener(new OnLongClickListener() {
                                            @SuppressWarnings("deprecation")
                                            public boolean onLongClick(View v) {
                                                setIndexValue(v, v.getTag(), savedInstanceStateSecond);

                                                if (v.getTag() == "SPINNER")
                                                    showDialog(SPINNER_OPTIONS);
                                                if (v.getTag() == "SEEK")
                                                    showDialog(SEEKBAR_OPTIONS);
                                                return false;
                                            }
                                        });
                                    }
                                    if (v.getTag() == "SEEK") {
                                        mSeekBar sb = (mSeekBar) v;
                                    }
                                } else {
                                    v.setOnClickListener(new DoubleClickListener() {
                                        @Override
                                        public void onSingleClick(View v) {

                                        }

                                        // User will double click to bring up customization menu
                                        @Override
                                        public void onDoubleClick(View view) {

                                            setIndexValue(view, view.getTag(), savedInstanceStateSecond);                //pass the View and the views tag to the Dialog class

                                            /*Call Dialogs associated with particular View*/
                                            if (view.getTag() == "BUTTON") {
                                                initButtonMenuFragment();
                                                baseLayout.bringChildToFront(mToolTipFrameLayout);
                                                showMenu();
                                                ToolTipView mToolTip = mToolTipFrameLayout.showToolTipForView(toolTip, view);
                                            } else if (view.getTag() == "TEXTVIEW") {
                                                initTextViewMenuFragment();
                                                baseLayout.bringChildToFront(mToolTipFrameLayout);
                                                showMenu();
                                                ToolTipView mToolTip = mToolTipFrameLayout.showToolTipForView(toolTip, view);
                                            } else if (view.getTag() == "IMAGEVIEW") {
                                                initImageViewMenuFragment();
                                                baseLayout.bringChildToFront(mToolTipFrameLayout);
                                                showMenu();
                                                ToolTipView mToolTip = mToolTipFrameLayout.showToolTipForView(toolTip, view);
                                            } else if (view.getTag() == "EDITTEXT") {
                                                initEditTextMenuFragment();
                                                baseLayout.bringChildToFront(mToolTipFrameLayout);
                                                showMenu();
                                                ToolTipView mToolTip = mToolTipFrameLayout.showToolTipForView(toolTip, view);
                                            } else if (view.getTag() == "RELATIVE_LAYOUT") {
                                                initRelativeLayoutMenuFragment();
                                                baseLayout.bringChildToFront(mToolTipFrameLayout);
                                                showMenu();
                                                ToolTipView mToolTip = mToolTipFrameLayout.showToolTipForView(toolTip, view);
                                            } else if (view.getTag() == "PROGRESS")
                                                showDialog(PROGRESSBAR_OPTIONS);
                                            else if (view.getTag() == "SEEK")
                                                showDialog(SEEKBAR_OPTIONS);
                                            else if (view.getTag() == "RATING")
                                                showDialog(RATINGBAR_OPTIONS);
                                            else if (view.getTag() == "LINEAR")
                                                showDialog(LINEARLAYOUT_OPTIONS);
                                        }
                                    });
                                }
                            }
                    }
                }
                reDrawAllViews();

                if (uiList.getSize() != 0) {
                    v = uiList.getView((uiList.getSize() - 1));                                //set temp view to current data structure index view


                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //item Listener for android menu resource
        switch (item.getItemId()) {
        }
        return true;
    }


    /**
     * Sets menu options based upon type of view selected
     * @param viewType - string that specifies type of view (i.e: imageview, button)
     */
    private void setMenuObjects(String viewType) {

        // Clear past menu options
        removeAllLeftItems();
        removeAllTopItems();

        /*
        Set up border menu for an ImageView view
         */
        if(viewType.equals("imageview")) {
            menuarray = getResources().getStringArray(R.array.topMenuItemsForImageView);
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

            setHidedOnTouchOutside(true);
            menuOnDisplay = "Appearance";
            menuGraphics.recycle();
        }

        /*
        Set up border menu for a Button view
         */
        if (viewType.equals("button")) {
            menuarray = getResources().getStringArray(R.array.topMenuItemsForButtonEditTextTextView);
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
            setHidedOnTouchOutside(true);
            menuOnDisplay = "Text";
            menuGraphics.recycle();
        }

        /*
        Set up border menu for a TextView view
         */
        if (viewType.equals("textview")) {
            menuarray = getResources().getStringArray(R.array.topMenuItemsForButtonEditTextTextView);
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
            setHidedOnTouchOutside(true);
            menuOnDisplay = "Text";
            menuGraphics.recycle();
        }

        /*
        Set up border menu for an EditText view
         */
        if (viewType.equals("edittext")) {
            menuarray = getResources().getStringArray(R.array.topMenuItemsForButtonEditTextTextView);
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
                IconItem iconItem;
                if (i == 0)
                    iconItem = new IconItem(this, i + 3, R.drawable.sethint);
                else
                    iconItem = new IconItem(this, i + 3, menuGraphics.getResourceId(i, 0));
                addLeftItem(iconItem);
            }
            setHidedOnTouchOutside(true);
            menuOnDisplay = "Text";
            menuGraphics.recycle();
        }

        /*
        Set up border menu for the RelativeLayout view
         */
        if (viewType.equals("relativelayout")) {
            if (baseLayout.hasBackgroundImage()) {
                menuGraphics = getResources().obtainTypedArray(R.array.relativeLayoutIconItemsImage);
            } else {
                menuGraphics = getResources().obtainTypedArray(R.array.relativeLayoutIconItems);
            }

            TextItem textItem = new TextItem(this, 0, "Relative Layout Options", Color.parseColor("#24aaf2"));
            for (int i = 0; i < menuGraphics.length(); i++) {
                IconItem iconItem = new IconItem(this, i + 1, menuGraphics.getResourceId(i, 0));
                addLeftItem(iconItem);
            }
            addTopItem(textItem);
            setHidedOnTouchOutside(true);
            showMenu();
            menuGraphics.recycle();
        }

        /*
        Set up border menu for the BaseLayout view
         */
        if (viewType.equals("baselayout")) {
            if(baseLayout.hasBackgroundImage()) {
                menuGraphics = getResources().obtainTypedArray(R.array.baseLayoutIconItemsImage);
            } else {
                menuGraphics = getResources().obtainTypedArray(R.array.baseLayoutIconItems);
            }

            TextItem textItem = new TextItem(this, 0, "Base Layout Options", Color.parseColor("#24aaf2"));
            for (int i = 0; i < menuGraphics.length(); i++) {
                IconItem iconItem = new IconItem(this, i + 1, menuGraphics.getResourceId(i, 0));
                addLeftItem(iconItem);
            }
            addTopItem(textItem);
            setHidedOnTouchOutside(true);
            showMenu();
            menuGraphics.recycle();
            if (tutorialState) {
                mTourGuideHandler = TourGuide.init(mActivity)
                        .setToolTip(new ToolTipIt()
                                .setTitle("This is your edge menu")
                                .setDescription("The top portion of the edge menu will provide categories and the left portion of the edge menu will provide different options for each category. Since this is the menu for the base layout there are no categories. Click to dismiss")
                                .setTextColor(Color.parseColor("#bdc3c7"))
                                .setBackgroundColor(Color.parseColor("#24aaf2"))
                                .setGravity(Gravity.BOTTOM | Gravity.RIGHT)
                                .setShadow(true)
                                .setEnterAnimation(animation))
                        .setOverlay(new Overlay())
                        .playOn(getBtnMenu());
                if (mTourGuideHandler.getFrameLayoutWithHole() != null) {
                    mTourGuideHandler.getFrameLayoutWithHole().setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTourGuideHandler.cleanUp();
                        }
                    });
                }
            }
        }
        // clean up views from tooltip if it's displayed.
        if (viewTooltipDisplayed)
            mToolTipFrameLayout.removeAllViews();
    }

    /**
     * Set menu items for a button view
     */
    private void initButtonMenuFragment() {
        setMenuObjects("button");
    }

    /**
     * Set menu items for a textview view
     */
    private void initTextViewMenuFragment() {
        setMenuObjects("textview");
    }

    /**
     * Set menu items for a edittext view
     */
    private void initEditTextMenuFragment() {
        setMenuObjects("edittext");
    }

    /**
     * Set menu items for a imageview view
     */
    private void initImageViewMenuFragment() {
        setMenuObjects("imageview");
    }

    /**
     * Set menu items for the baselayout
     */
    private void initBaseLayoutMenuFragment() {
        setMenuObjects("baselayout");
    }

    /**
     * Set menu items for a relativelayout
     */
    private void initRelativeLayoutMenuFragment() {
        setMenuObjects("relativelayout");
    }

    /**
     * Handle screen orientation and UI redraw on new Activity
     * @param newConfig - UI configuration
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            firstStart = false;
            if(isMenuShowed()) {
                hideMenuKeepToolTip();
            }
            reDrawAllViews();
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            firstStart = false;
            if(isMenuShowed()) {
                hideMenuKeepToolTip();
            }
            reDrawAllViews();
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Override back button to exit the whiteboard and return to the opening screen
     */
    @Override
    public void onBackPressed() {
        showDialog(EXIT_WHITEBOARD);
    }

    // Before 2.0
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog(EXIT_WHITEBOARD);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Broadcast Receiver to notify of a layout view change so that we redraw modified view and all views related to that view
     */
    public class LayoutChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("Broadcast Recieved: ", "Layout Changed");
            for (int i = 0; i < uiList.getSize(); i++) {
                if (uiList.getView(i).getTag().equals("BUTTON")) {
                    mButton b = (mButton) uiList.getView(i);
                    b.updateLinkedViews(uiList);
                    Log.v("Updating View: ", b.getStringID());
                }
                if (uiList.getView(i).getTag().equals("TEXTVIEW")) {
                    mTextView tv = (mTextView) uiList.getView(i);
                    tv.updateLinkedViews(uiList);
                    Log.v("Updating View: ", tv.getStringID());
                }
                if (uiList.getView(i).getTag().equals("EDITTEXT")) {
                    mEditText et = (mEditText) uiList.getView(i);
                    et.updateLinkedViews(uiList);
                    Log.v("Updating View: ", et.getStringID());
                }
                if (uiList.getView(i).getTag().equals("IMAGEVIEW")) {
                    mImageView iv = (mImageView) uiList.getView(i);
                    iv.updateLinkedViews(uiList);
                    Log.v("Updating View: ", iv.getStringID());
                }
            }
            updateViewToolTip();
        }
    }

    /**
     * Broadcast Receiver to notify activity that a view has been added and redraw the layout.
     */
    public class ViewAddedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.i("Broadcast Recieved: ", "View Added");
            reloadUI();
        }
    }

    /**
     * Broadcast Receiver to notify user they can not set a view to be outside of the screen.
     */
    public class LayoutConflictViewOffScreenReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("Layout Error ", "View off screen");
            SnackBar snackbar = new SnackBar(mActivity, "Option would force a view outside of screen bounds, please reselect a different option", "Close", null);
            snackbar.setDismissTimer(20000);
            snackbar.show();
        }
    }

    /**
     * Broadcast Receiver to notify the user that saving the XML was successful.
     */
    public class XmlSuccessReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("Broadcast Recieved: ", "XML Success");
            SnackBar snackbar = new SnackBar(mActivity, "XML generation is successful! File is saved in " + Environment.getExternalStorageDirectory() + "/Protofy_Layouts/", null, null);
            snackbar.setDismissTimer(20000);
            snackbar.show();
        }
    }

    /**
     * Abstract Class to handle view double click at an interval of 300 milliseconds between clicks.
     */
    public abstract class DoubleClickListener implements OnClickListener {

        private static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

        long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onDoubleClick(v);
            } else {
                onSingleClick(v);
            }
            lastClickTime = clickTime;
        }

        public abstract void onSingleClick(View v);

        public abstract void onDoubleClick(View v);
    }

    /**
     * Check to see if user gave permission for us to write to storage.
     * @param requestCode - permissions request code
     * @param permissions - permissions asked for
     * @param grantResults - result for each permission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                hasStoragePermission = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}