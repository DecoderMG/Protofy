package com.underground.protofy.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.underground.protofy.CustomDataStructures.UIelementList;
import com.underground.protofy.Preferences.RelativeLayoutPreferences;

import java.util.LinkedList;

/**
 * Created by Dakota on 7/18/2015.
 * Custom Relative Layout class to build upon Android's existing Relative Layout class.
 */
public class mRelativeLayout extends RelativeLayout {

    public String stringViewId, backgroundHexColor, tintHexColor;
    private Context mContext;
    private LinkedList<Integer> viewLayoutParameters;
    private LinkedList<Integer> linkedViewIds;
    private UIelementList layoutViewList;
    private String parentLayoutId;

    private Bitmap backgroundBitmap;

    private int alpha, leftPadding, rightPadding, topPadding, bottomPadding, id,
            backgroundColor, oldBackgroundColor, oldTintColor, tintColor, width, height,
            marginLeft, marginRight, marginTop, marginBottom;
    private float viewScaleX, viewScaleY;

    private boolean exportColor, exportPadding, tintHasBeenAdded, imageHasBeenAdded, inPreviewMode, isViewSizeEdited;

    private RelativeLayoutPreferences relativeLayoutPrefs;


    public mRelativeLayout(Context c) {
        super(c);
        mContext = c;
        initializeView();
    }

    public mRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        viewLayoutParameters = new LinkedList<>();
        linkedViewIds = new LinkedList<>();
        layoutViewList = new UIelementList();
        layoutViewList.initializeUIdataStructure();

        viewScaleX = getScaleX();
        viewScaleY = getScaleY();
        backgroundColor = Color.WHITE;
        backgroundHexColor = "fff";
        tintHexColor = "00ffffff";
        tintColor = Color.TRANSPARENT;

        leftPadding = 16;
        rightPadding = 16;
        topPadding = 16;
        bottomPadding = 16;

        Log.v("Relative1", "AttributeSet method called");

        exportColor = false;
        exportPadding = false;
        tintHasBeenAdded = false;
        imageHasBeenAdded = false;
        inPreviewMode = false;
        isViewSizeEdited = false;

        width = getWidth();
        height = getHeight();
        alpha = 255;
        //this.setViewAlpha(alpha);
        this.setBackgroundColor(backgroundColor);
    }

    /**
     * Initialize the custom Relative Layout. Always call in constructor.
     */
    private void initializeView() {
        viewLayoutParameters = new LinkedList<>();
        linkedViewIds = new LinkedList<>();
        relativeLayoutPrefs = new RelativeLayoutPreferences(mContext);
        layoutViewList = new UIelementList();
        layoutViewList.initializeUIdataStructure();

        viewScaleX = getScaleX();
        viewScaleY = getScaleY();
        backgroundColor = relativeLayoutPrefs.getRelativeLayoutColor();
        backgroundHexColor = String.format("%08x", relativeLayoutPrefs.getRelativeLayoutColor());

        leftPadding = relativeLayoutPrefs.getDefaultRelativeLayoutLeftPadding();
        rightPadding = relativeLayoutPrefs.getDefaultRelativeLayoutRightPadding();
        topPadding = relativeLayoutPrefs.getDefaultRelativeLayoutTopPadding();
        bottomPadding = relativeLayoutPrefs.getDefaultRelativeLayoutBottomPadding();

        marginLeft = relativeLayoutPrefs.getDefaultRelativeLayoutLeftMargin();
        marginRight = relativeLayoutPrefs.getDefaultRelativeLayoutRightMargin();
        marginTop = relativeLayoutPrefs.getDefaultRelativeLayoutTopMargin();
        marginBottom = relativeLayoutPrefs.getDefaultRelativeLayoutBottomMargin();

        width = relativeLayoutPrefs.getDefaultWidth();
        height = relativeLayoutPrefs.getDefaultHeight();

        exportColor = false;
        exportPadding = false;
        tintHasBeenAdded = false;
        imageHasBeenAdded = false;
        inPreviewMode = false;
        isViewSizeEdited = true;

        Log.v("Relative2", "Non-AttributeSet called");


        this.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(backgroundColor);
        this.setViewSize(width, height, "width and height");
    }

    /**
     * Add view to Relative Layout
     * @param v - view to add to layout
     */
    public void addViewToLayout(View v) {
        if(v.getTag().equals("TEXTVIEW")) {
            mTextView tv = (mTextView) v;
            tv.setParentIdentifier(this.getStringID());
        } else if (v.getTag().equals("BUTTON")) {
            mButton b = (mButton) v;
            b.setParentIdentifier(this.getStringID());
        } else if (v.getTag().equals("EDITTEXT")) {
            mEditText ed = (mEditText) v;
            ed.setParentIdentifier(this.getStringID());
        } else if (v.getTag().equals("IMAGEVIEW")) {
            mImageView iv = (mImageView) v;
            iv.setParentIdentifier(this.getStringID());
        } else if (v.getTag().equals("RELATIVE_LAYOUT")) {
            mRelativeLayout rl = (mRelativeLayout) v;
            rl.setParentIdentifier(this.getStringID());
        }
        this.addView(v);
    }

    /**
     * Add view to Relative Layout view tracker
     * @param v - view to add to layout
     * @param object - view identifier tag
     */
    public void addViewToList(View v, Object object) {
        layoutViewList.addView(v, object);
    }

    /**
     * Associate Parent views ID that this Relative Layout belongs to the Relative Layout.
     * @param parentId - ID of parent viewgroup
     */
    public void setParentIdentifier(String parentId) {
        parentLayoutId = parentId;
    }
    public String getParentIdentifier() {
        return parentLayoutId;
    }

    /**
     * Removed view from Relative Layout
     * @param v - view to remove
     */
    public void removeViewFromLayout(View v) {
        for(int i=0; i < layoutViewList.getSize(); i++){
            if(v == layoutViewList.getView(i)){
                layoutViewList.removeView(i);
            }
        }
    }

    /**
     * Returns all child views in the Relative Layout
     * @return - List of all child views
     */
    public UIelementList getLayoutViews() {
        return layoutViewList;
    }

    /**
     * Set string ID to Relative Layout for a user friendly ID.
     * @param viewId - string representation of ID you want to set on view.
     */
    public void setStringID(String viewId) {
        stringViewId = viewId;
    }

    /**
     * Set Relative Layout ID so that we can refer to button by ID
     * @param viewId - ID you wish to associate with view.
     */
    public void setViewID(int viewId) {
        id = viewId;
        setId(id);
    }

    public void setPreviewPadding(String paddingType, int paddingAmount) {
        if (paddingType.equals("left"))
            leftPadding = paddingAmount;
        if (paddingType.equals("right"))
            rightPadding = paddingAmount;
        if (paddingType.equals("top"))
            topPadding = paddingAmount;
        if (paddingType.equals("bottom"))
            bottomPadding = paddingAmount;

        setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
    }

    public void setViewPadding(int left, int right, int top, int bottom) {
        leftPadding = left;
        rightPadding = right;
        topPadding = top;
        bottomPadding = bottom;
        setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
    }

    public void setViewMargins(int left, int right, int top, int bottom) {
        marginLeft = left;
        marginRight = right;
        marginTop = top;
        marginBottom = bottom;
        ((RelativeLayout.MarginLayoutParams) this.getLayoutParams()).setMargins(
                getDipValueinPixels(marginLeft),
                getDipValueinPixels(marginTop),
                getDipValueinPixels(marginRight),
                getDipValueinPixels(marginBottom));
    }
    public int getViewMargin(String marginType) {
        if (marginType.equals("left"))
            return marginLeft;
        if (marginType.equals("right"))
            return marginRight;
        if (marginType.equals("top"))
            return marginTop;
        if (marginType.equals("bottom"))
            return marginBottom;
        return 0;
    }


    public void setViewColor(int color, String option, String hexValue) {
        if (option.equals("background")) {
            oldBackgroundColor = backgroundColor;
            backgroundColor = color;
            backgroundHexColor = hexValue;
            setBackgroundColor(backgroundColor);
        }
        imageHasBeenAdded = false;
        tintColor = Color.TRANSPARENT;
        tintHasBeenAdded = false;
        backgroundBitmap = null;
    }

    public void setViewTintColor(int color, String hexValue) {
        oldTintColor = tintColor;
        tintColor = color;
        tintHexColor = hexValue;
        tintHasBeenAdded = true;
        //if(relativeLayoutPrefs.getDefaultTintMode().equals("0")) {
            this.getBackground().setColorFilter(tintColor, PorterDuff.Mode.SRC_ATOP);
        /*
        } else if (relativeLayoutPrefs.getDefaultTintMode().equals("1")) {
            this.getBackground().setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
        } else if (relativeLayoutPrefs.getDefaultTintMode().equals("2")) {
            this.getBackground().setColorFilter(tintColor, PorterDuff.Mode.SRC_OVER);
        } else if (relativeLayoutPrefs.getDefaultTintMode().equals("3")) {
            this.getBackground().setColorFilter(tintColor, PorterDuff.Mode.MULTIPLY);
        } else if (relativeLayoutPrefs.getDefaultTintMode().equals("4")) {
            this.getBackground().setColorFilter(tintColor, PorterDuff.Mode.SCREEN);
        } else if (relativeLayoutPrefs.getDefaultTintMode().equals("5")) {
            this.getBackground().setColorFilter(tintColor, PorterDuff.Mode.ADD);
        } */
    }

    public int getViewTintColor() {
        return tintColor;
    }

    public String getTintHexColor() {
        return tintHexColor;
    }

    public boolean hasTintBeenAdded() {
        return tintHasBeenAdded;
    }

    public void setViewScale(float scaleX, float scaleY, String option) {
        if (option.equals("Scale X")) {
            viewScaleX = scaleX;
            setScaleX(viewScaleX);
        }
        if (option.equals("Scale Y")) {
            viewScaleY = scaleY;
            setScaleY(viewScaleY);
        }
        if (option.equals("set Scale")) {
            viewScaleX = scaleX;
            viewScaleY = scaleY;
            setScaleX(viewScaleX);
            setScaleY(viewScaleY);
        }
    }

    public void setViewBackgroundImage(Bitmap bitmap) {
        try {
            //int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
            backgroundBitmap = Bitmap.createScaledBitmap(bitmap, this.getWidth(), this.getHeight(), true);
            Drawable dr = new BitmapDrawable(backgroundBitmap);
            this.setBackgroundDrawable(dr);
            imageHasBeenAdded = true;
        } catch (Exception fnfe) {
            Log.v("FNFE", fnfe.getMessage()+"v");
        }
    }

    public void setViewSize(int givenWidth, int givenHeight, String option) {
        RelativeLayout.LayoutParams tempParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if (option.equals("width")) {
            width = givenWidth;
            tempParams.width = getDipValueinPixels(width);
        }
        if (option.equals("height")) {
            height = givenHeight;
            tempParams.height = getDipValueinPixels(height);

        }
        if (option.equals("width and height copy")) {
            height = givenHeight;
            width = givenWidth;
            tempParams.width = getDipValueinPixels(width);
            tempParams.height = getDipValueinPixels(height);
        }
        if (option.equals("width and height")) {
            height = givenHeight;
            width = givenWidth;
            tempParams.width = getDipValueinPixels(width);
            tempParams.height = getDipValueinPixels(height);
            isViewSizeEdited = true;
        }
        this.setLayoutParams(tempParams);
        //this.redrawView();
    }
    public int getViewSize(String option){
        if(option.equals("width")){
            return width;
        } else if (option.equals("height")){
            return height;
        } else if (option.equals("check")){
            if(isViewSizeEdited)
                return 0;
            else
                return -3;
        } else {
            return 0;
        }
    }
    public void setPreviewViewSize(int givenWidth, int givenHeight, String option) {
        FrameLayout.LayoutParams tempParams = (FrameLayout.LayoutParams) this.getLayoutParams();
        if (option.equals("width")) {
            width = givenWidth;
            tempParams.width = getDipValueinPixels(width);
        }
        if (option.equals("height")) {
            height = givenHeight;
            tempParams.height = getDipValueinPixels(height);
        }
        if (option.equals("width and height copy")) {
            height = givenHeight;
            width = givenWidth;
            tempParams.width = getDipValueinPixels(width);
            tempParams.height = getDipValueinPixels(height);
        }
        if (option.equals("width and height")) {
            height = givenHeight;
            width = givenWidth;
            tempParams.width = getDipValueinPixels(width);
            tempParams.height = getDipValueinPixels(height);
        }
        this.setLayoutParams(tempParams);
    }

    public Bitmap getViewBackgroundImage() {
        return backgroundBitmap;
    }
    public boolean hasBackgroundImage() {
        return imageHasBeenAdded;
    }

    public int getViewPadding(String paddingType) {
        if (paddingType.equals("left"))
            return leftPadding;
        if (paddingType.equals("right"))
            return rightPadding;
        if (paddingType.equals("top"))
            return topPadding;
        if (paddingType.equals("bottom"))
            return bottomPadding;
        return 0;
    }

    public int getViewColor(String option) {
        if (option.equals("background"))
            return backgroundColor;
        return 0;
    }

    public int getViewLastColor(String option) {
        if (option.equals("background"))
            return oldBackgroundColor;
        return 0;
    }

    public float getViewScale(String option) {
        if (option.equals("Scale X"))
            return viewScaleX;
        if (option.equals("Scale Y"))
            return viewScaleY;
        return 0;
    }

    public String getStringID() {
        return stringViewId;
    }

    public int getViewId() {
        return id;
    }

    public LinkedList<Integer> getViewLinkedViews() {
        return linkedViewIds;
    }

    public String getColorHexValue(String option) {
        if (option.equals("background"))
            return backgroundHexColor;
        else
            return null;
    }

    /**
     * Check for conflicts and add a new layout parameter if no conflict arises. Will call redrawView().
     * @param param - Layout parameter to add
     * @param linkViewId - ID of view being linked to this view with layout param (IMPORTANT: -1 for parent view)
     */
    public void addViewLayoutParam(int param, int linkViewId) {
        viewLayoutParameters.addLast(param);
        linkedViewIds.addLast(linkViewId);
        RelativeLayout.LayoutParams tempParam = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if (linkViewId == -1) {
            tempParam.addRule(param);
        } else {
            tempParam.addRule(param, linkViewId);
        }
        this.setLayoutParams(tempParam);
        this.requestLayout();
    }

    /**
     * Checks for and removes layout parameter from view.
     * @param param - Layout parameter to remove
     */
    public void removeViewLayoutParam(int param) {
        RelativeLayout.LayoutParams tempParam = (RelativeLayout.LayoutParams) this.getLayoutParams();
        for (int i = 0; i < viewLayoutParameters.size() - 1; i++) {
            if (viewLayoutParameters.get(i) == param) {
                viewLayoutParameters.remove(i);
                linkedViewIds.remove(i);
                tempParam.addRule(param, 0);
            }
        }
        redrawView();
    }

    public boolean getExportColorEdited() {
        return exportColor;
    }

    private void setExportColorEdited(boolean value) {
        exportColor = value;
    }

    public boolean getExportPaddingEdited() {
        return exportPadding;
    }

    private void setExportPaddingEdited(boolean value) {
        exportPadding = value;
    }

    /**
     * Invalidate and redraw view with all applied attributes. Call this to force a redraw of view.
     * This is helpful when you change a view property that will not automatically trigger android to
     * invalidate the view.
     */
    public void redrawView() {
        RelativeLayout.LayoutParams tempParam = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if (viewLayoutParameters.size() != 0) {
            for (int i = 0; i < (viewLayoutParameters.size()); i++) {
                if (linkedViewIds.get(i) == -1) {
                    tempParam.addRule(viewLayoutParameters.get(i));
                } else {
                    tempParam.addRule(viewLayoutParameters.get(i), linkedViewIds.get(i));
                }
            }
            this.setLayoutParams(tempParam);
            this.requestLayout();
        }
    }

    /**
     * Allows for quick creation of a view by copying over existing attributes of an already existing view.
     * @param view - view you want to copy
     * @param option - type of copy you want to occur. IE: "set Text" will copy over only attributes pertaining to text displaying. Input nothing to copy over everything.
     */
    public void initCopyView(mRelativeLayout view, String option) {
        if(option.equals("non base")){
            if(view.hasBackgroundImage()) {
                this.setViewBackgroundImage(view.getViewBackgroundImage());
                if(view.hasTintBeenAdded()) {
                    this.setViewTintColor(view.getViewTintColor(), view.getTintHexColor());
                }
            } else {
                this.setBackgroundColor(view.getViewColor("background"));
            }
            this.setViewSize(view.getViewSize("width"), view.getViewSize("height"), "width and height");
            this.setStringID(view.getStringID());
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"),
                    view.getViewPadding("top"), view.getViewPadding("bottom"));
        } else {
            //this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"),
            // view.getViewPadding("top"), view.getViewPadding("bottom"));
            //this.setViewAlpha(view.getViewAlpha());
            this.setBackgroundColor(view.getViewColor("background"));
            this.setLayoutParams(view.getLayoutParams());
            this.getLayoutParams().width = view.getWidth();
            this.getLayoutParams().height = view.getHeight();
        }
    }

    /*
    Start Helper Functions
     */

    /**
     * Toggle preview mode on and offer for a view.
     * @param value - true - preview mode, false - no preview
     */
    public void setPreview(boolean value) {
        inPreviewMode = value;
    }

    /**
     * Convert pixels into density independent pixels to ensure layout will scale across multiple screen sizes.
     * @param value - pixel value to convert
     * @return - Dp value of pixels
     */
    private int getDipValueinPixels(int value) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }
    /*
    End Helper Functions
     */
}