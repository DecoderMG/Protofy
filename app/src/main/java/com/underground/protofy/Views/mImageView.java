package com.underground.protofy.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.Protofy.protofy.R;
import com.underground.protofy.CustomDataStructures.UIelementList;
import com.underground.protofy.Preferences.ImageViewPreferences;

import java.util.LinkedList;

/**
 * Created by Dakota on 10/17/2015.
 * Custom ImageView class to build upon Android's existing ImageView class.
 */
public class mImageView extends ImageView {

    private Context mContext;
    private String stringViewId, tintHexColor;
    private LinkedList<Integer> viewLayoutParameters;
    private LinkedList<Integer> linkedViewIds;
    private LinkedList<Integer> linkedViewLayoutParams;
    private LinkedList<String> linkedViewStringIds;
    private String visibility, scaleTypeString, tintModeString;
    private String parentLayoutId;


    private Bitmap backgroundBitmap;
    private ScaleType scaleType;
    private PorterDuff.Mode tintStyle;

    private int leftPadding, rightPadding, topPadding, bottomPadding, id, width, height,
            marginLeft, marginRight, marginTop, marginBottom, oldTintColor, tintColor;

    private boolean inPreviewMode, tintHasBeenAdded;
    private boolean exportColor, exportTypeface, exportPadding, exportTextSize, exportMargins, exportLinkedViews, exportParentLayouts, exportLineSpacing, isViewSizeEdited;
    private int widthWrappingParam, heightWrappingParam;

    private ImageViewPreferences imageViewPrefs;

    public mImageView (final Context c) {
        super(c);
        mContext = c;
        InitializeView();
    }

    /**
     * Initialize the custom ImageView. Always call in constructor.
     */
    private void InitializeView(){
        viewLayoutParameters = new LinkedList<>();
        linkedViewIds = new LinkedList<>();
        linkedViewLayoutParams = new LinkedList<>();
        linkedViewStringIds = new LinkedList<>();
        imageViewPrefs = new ImageViewPreferences(mContext);

        inPreviewMode = false;

        visibility = "visible";

        stringViewId = "";
        tintHexColor = "00ffffff";
        tintColor = Color.TRANSPARENT;
        tintHasBeenAdded = false;
        scaleTypeString = "fit_xy";


        exportLinkedViews = true;
        exportMargins = true;
        exportPadding = true;
        exportParentLayouts = true;
        
        

        if (imageViewPrefs.getDefaultTintMode().equals("0")) {
            setViewTintMode("source_atop");
        } else if (imageViewPrefs.getDefaultTintMode().equals("1")) {
            setViewTintMode("source_in");
        } else if (imageViewPrefs.getDefaultTintMode().equals("2")) {
            setViewTintMode("source_over");
        } else if (imageViewPrefs.getDefaultTintMode().equals("3")) {
            setViewTintMode("multiply");
        } else if (imageViewPrefs.getDefaultTintMode().equals("4")) {
            setViewTintMode("screen");
        } else if (imageViewPrefs.getDefaultTintMode().equals("5")) {
            setViewTintMode("add");
        } else {}

        if (imageViewPrefs.getImageViewParentRelation().equals("1")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_TOP);
        } else if (imageViewPrefs.getImageViewParentRelation().equals("2")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else if (imageViewPrefs.getImageViewParentRelation().equals("3")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (imageViewPrefs.getImageViewParentRelation().equals("4")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else if (imageViewPrefs.getImageViewParentRelation().equals("5")) {
            addStartingParentLayoutParam(RelativeLayout.CENTER_IN_PARENT);
        } else if (imageViewPrefs.getImageViewParentRelation().equals("6")) {
            addStartingParentLayoutParam(RelativeLayout.CENTER_HORIZONTAL);
        } else if (imageViewPrefs.getImageViewParentRelation().equals("7")) {
            addStartingParentLayoutParam(RelativeLayout.CENTER_VERTICAL);
        } else {}

        /*switch (imageViewPrefs.getDefaultTintMode()) {
            case "0":

        }

        switch (imageViewPrefs.getImageViewDefaultScaleType()) {
            case "0":
                this.setViewScaleType(ScaleType.MATRIX);
                break;
            case "1":
                this.setViewScaleType(ScaleType.FIT_XY);
                break;
            case "2":
                this.setViewScaleType(ScaleType.FIT_START);
                break;
            case "3":
                this.setViewScaleType(ScaleType.FIT_CENTER);
                break;
            case "4":
                this.setViewScaleType(ScaleType.FIT_END);
                break;
            case "5":
                this.setViewScaleType(ScaleType.CENTER);
                break;
            case "6":
                this.setViewScaleType(ScaleType.CENTER_CROP);
                break;
            case "7":
                this.setViewScaleType(ScaleType.CENTER_INSIDE);
                break;
        }*/

        RelativeLayout.LayoutParams startParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        width = imageViewPrefs.getDefaultWidth();
        height = imageViewPrefs.getDefaultHeight();

        startParams.width = getDipValueinPixels(width);
        startParams.height = getDipValueinPixels(height);

        setLayoutParams(startParams);

        Bitmap startImage = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.imageview_default);

        leftPadding = imageViewPrefs.getDefaultImageViewLeftPadding();
        rightPadding = imageViewPrefs.getDefaultImageViewRightPadding();
        topPadding = imageViewPrefs.getDefaultImageViewTopPadding();
        bottomPadding = imageViewPrefs.getDefaultImageViewBottomPadding();

        marginLeft = imageViewPrefs.getDefaultImageViewLeftMargin();
        marginRight = imageViewPrefs.getDefaultImageViewRightMargin();
        marginBottom = imageViewPrefs.getDefaultImageViewBottomMargin();
        marginTop = imageViewPrefs.getDefaultImageViewTopMargin();

        setViewSize(width, height, "width and height");
        this.setPadding(0, 0, 0, 0);
        this.setViewMargins(0,0,0,0);
        this.setViewVisibility(visibility);
        this.setViewImage(startImage);
        //setViewTintColor(tintColor, tintHexColor);
    }

    /**
     * Associate Parent views ID that this ImageView belongs to the ImageView.
     * @param parentId - ID of parent viewgroup
     */
    public void setParentIdentifier(String parentId) {
        parentLayoutId = parentId;
    }
    public String getParentIdentifier() {
        return parentLayoutId;
    }

    /**
     * Set ImageView ID so that we can refer to button by ID
     * @param viewId - ID you wish to associate with view.
     */
    public void setViewID(int viewId) {
        id = viewId;
        this.setId(id);
    }
    public int getViewId() {
        return id;
    }

    /**
     * Set string ID to ImageView for a user friendly ID.
     * @param viewId - string representation of ID you want to set on view.
     */
    public void setStringID(String viewId) {
        stringViewId = viewId;
    }
    public String getStringID() {
        return stringViewId;
    }

    /*
    Start ImageView Appearance Setters and Getters
     */

    public void setViewImage(Bitmap bitmap) {
        try {
            //int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
            backgroundBitmap = Bitmap.createScaledBitmap(bitmap, getDipValueinPixels(width), getDipValueinPixels(height), true);
            Drawable dr = new BitmapDrawable(backgroundBitmap);
            this.setImageDrawable(dr);
            if(hasTintBeenAdded()){
                this.setViewTintColor(tintColor, tintHexColor);
            }
        } catch (Exception fnfe) {
            Log.v("FNFE", fnfe.getMessage()+"v");
        }
    }
    private void setViewInvisibleImage() {
        try {
            //int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
            Bitmap invisibleImage = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.invisible);
            Drawable dr = new BitmapDrawable(Bitmap.createScaledBitmap(invisibleImage, getDipValueinPixels(width), getDipValueinPixels(height), true));
            this.setImageDrawable(dr);
        } catch (Exception fnfe) {
            Log.v("FNFE", fnfe.getMessage()+"v");
        }
    }
    public Bitmap getViewImage() {
        return backgroundBitmap;
    }

    public void setViewScaleType(ScaleType mScaleType) {
        scaleType = mScaleType;
        if (scaleType == ScaleType.FIT_XY) {
            scaleTypeString = "fitXY";
        } else if(scaleType == ScaleType.MATRIX) {
            scaleTypeString = "matrix";
        } else if (scaleType == ScaleType.FIT_CENTER) {
            scaleTypeString = "fitcenter";
        } else if (scaleType == ScaleType.CENTER) {
            scaleTypeString = "center";
        } else if (scaleType == ScaleType.CENTER_CROP) {
            scaleTypeString = "centerCrop";
        } else if (scaleType == ScaleType.CENTER_INSIDE) {
            scaleTypeString = "centerInside";
        } else if (scaleType == ScaleType.FIT_END) {
            scaleTypeString = "fitEnd";
        } else if (scaleType == ScaleType.FIT_START) {
            scaleTypeString = "fitStart";
        }
        this.setScaleType(scaleType);
    }
    public String getViewScaleType() {
        return scaleTypeString;
    }

    public void setViewTintColor(int color, String hexValue) {
        oldTintColor = tintColor;
        tintColor = color;
        tintHexColor = hexValue;
        tintHasBeenAdded = true;
        //if(relativeLayoutPrefs.getDefaultTintMode().equals("0")) {
        if(tintModeString.equals("source_atop")) {
            this.setColorFilter(tintColor, PorterDuff.Mode.SRC_ATOP);
        } else if(tintModeString.equals("source_in")) {
            this.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
        } else if(tintModeString.equals("source_over")) {
            this.setColorFilter(tintColor, PorterDuff.Mode.SRC_OVER);
        } else if(tintModeString.equals("multiply")) {
            this.setColorFilter(tintColor, PorterDuff.Mode.MULTIPLY);
        } else if(tintModeString.equals("screen")) {
            this.setColorFilter(tintColor, PorterDuff.Mode.SCREEN);
        } else if(tintModeString.equals("add")) {
            this.setColorFilter(tintColor, PorterDuff.Mode.ADD);
        }
        //this.setColorFilter(tintColor, PorterDuff.Mode.SRC_ATOP);
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

    public void setViewTintMode(String option) {
        tintModeString = option;
    }

    public String getViewTintMode() {
        return tintModeString;
    }

    public boolean hasTintBeenAdded() {
        return tintHasBeenAdded;
    }

    public void setViewPadding(int left, int right, int top, int bottom) {
        leftPadding = left;
        rightPadding = right;
        topPadding = top;
        bottomPadding = bottom;
        this.setPadding(getDipValueinPixels(leftPadding), getDipValueinPixels(topPadding),
                getDipValueinPixels(rightPadding), getDipValueinPixels(bottomPadding));
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
    public void setPreviewPadding(String paddingType, int paddingAmount) {
        if (paddingType.equals("left"))
            leftPadding = paddingAmount;
        if (paddingType.equals("right"))
            rightPadding = paddingAmount;
        if (paddingType.equals("top"))
            topPadding = paddingAmount;
        if (paddingType.equals("bottom"))
            bottomPadding = paddingAmount;

        this.setPadding(getDipValueinPixels(leftPadding), getDipValueinPixels(topPadding),
                getDipValueinPixels(rightPadding), getDipValueinPixels(bottomPadding));
    }

    public void setViewVisibility(String option){
        if(option.equals("visible")){
            visibility = "visible";
            this.setViewImage(backgroundBitmap);
            this.setVisibility(VISIBLE);
        } else if (option.equals("invisible")) {
            visibility = "invisible";
            this.setViewInvisibleImage();
            this.setBackgroundColor(Color.parseColor("#33ffffff"));
        }
    }
    public String getViewVisibility(){
        return visibility;
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
        }
        this.setViewImage(backgroundBitmap);
        this.setLayoutParams(tempParams);
        //this.redrawView();
    }
    public int getViewSize(String option){
        if(option.equals("width")){
            return width;
        } else if (option.equals("height")){
            return height;
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

    /*
    End ImageView Appearance Setter and Getters
     */

    /*
    Start ImageView Layout Setters and Getters
    NOTE: Layout checking algorithm and "Parent Relation", "View Relations" code is at the bottom to
    reduce hunting time
     */

    public void setViewMargins(int left, int right, int top, int bottom) {
        marginLeft = left;
        marginRight = right;
        marginTop = top;
        marginBottom = bottom;
        ((RelativeLayout.MarginLayoutParams) this.getLayoutParams()).setMargins(getDipValueinPixels(marginLeft), getDipValueinPixels(marginTop),
                getDipValueinPixels(marginRight), getDipValueinPixels(marginBottom));
    }
    public int getViewMargins(String marginType) {
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
    public void setPreviewMargins(String marginType, int marginAmount) {
        if (marginType.equals("left"))
            marginLeft = marginAmount;
        if (marginType.equals("right"))
            marginRight = marginAmount;
        if (marginType.equals("top"))
            marginTop = marginAmount;
        if (marginType.equals("bottom"))
            marginBottom = marginAmount;

        ((RelativeLayout.MarginLayoutParams) this.getLayoutParams()).setMargins(getDipValueinPixels(marginLeft), getDipValueinPixels(marginTop),
                getDipValueinPixels(marginRight), getDipValueinPixels(marginBottom));
    }

    public void setLayoutWrapping(int widthParam, int heightParam) {
        RelativeLayout.LayoutParams tempParams = new RelativeLayout.LayoutParams(widthParam, heightParam);
        for (int i = 0; i < viewLayoutParameters.size(); i++) {
            tempParams.addRule(viewLayoutParameters.get(i));
        }
        for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
            tempParams.addRule(linkedViewLayoutParams.get(i), linkedViewIds.get(i));
        }
        if(widthParam == RelativeLayout.LayoutParams.WRAP_CONTENT){
            tempParams.width = width;
        }
        if(heightParam == RelativeLayout.LayoutParams.WRAP_CONTENT){
            tempParams.height = height;
        }
        this.setLayoutParams(tempParams);
        this.redrawView();
    }

    /**
     * Add starting Parent layout parameters to ImageView
     * @param param - type of LayoutParam (IE: RelativeLayout.CENTER_VERTICAL)
     */
    private void addStartingParentLayoutParam(int param) {
        RelativeLayout.LayoutParams tempParam = (RelativeLayout.LayoutParams) this.getLayoutParams();
        viewLayoutParameters.addLast(param);
        tempParam.addRule(param);
    }

    /**
     * Remove a Layout Parameter specified by user.
     * @param index - index chosen from Alert Dialog
     */
    public void userRemovedLayoutParam(int index) {
        RelativeLayout.LayoutParams tempParam = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if (index > (viewLayoutParameters.size() - 1)) {
            int linkedViewIndex = index - viewLayoutParameters.size() - 1;
            tempParam.addRule(viewLayoutParameters.get(linkedViewIndex));
            linkedViewLayoutParams.remove(linkedViewIndex);
            linkedViewStringIds.remove(linkedViewIndex);
            linkedViewIds.remove(linkedViewIndex);
        } else {
            tempParam.addRule(viewLayoutParameters.get(index), 0);
            viewLayoutParameters.remove(index);
        }
    }

    /**
     * Get the IDs of all views linked to this ImageView
     * @return List of View IDs
     */
    public LinkedList<Integer> getLinkedViews() {
        return linkedViewIds;
    }

    /**
     * Get all string IDs of all views linked to this ImageView
     * @return List of View IDs in String format
     */
    public LinkedList<String> getLinkedViewStringIds() {
        return linkedViewStringIds;
    }

    /**
     * Get all parent layout parameters assigned to the ImageView
     * @return - List of Layout Parameters (IE: RelativeLayout.CENTER_VERTICAL)
     */
    public LinkedList<Integer> getParentLayoutParams() {
        return viewLayoutParameters;
    }

    /**
     * Get last layout parameter linked to the ImageView.
     * @return last layout param applied to the ImageView
     */
    public int getLastChildLayoutParam() {
        return linkedViewLayoutParams.getLast();
    }

    /**
     * Get ID of the lat linked view to the ImageView
     * @return - id of last view linked
     */
    public int getLastLinkedViewId() {
        return linkedViewIds.getLast();
    }

    /**
     * Get string ID of last linked view.
     * @return - string id of last linked view
     */
    public String getLastLinkedViewStringId() {
        return linkedViewStringIds.getLast();
    }

    /**
     * Get layout parameter rules of view
     * @return - int array of layout rules
     */
    private int[] getLayoutParamRules() {
        RelativeLayout.LayoutParams tempParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
        int[] noLayoutRules = {};
        try {
            return tempParams.getRules();
        } catch (NullPointerException e) {
            return noLayoutRules;
        }
    }

    /**
     * Set multiple linked IDs
     * @param viewIds - List of view IDs to add
     */
    public void setLinkedViewIds(LinkedList<Integer> viewIds) {
        for (int i = 0; i < viewIds.size(); i++) {
            linkedViewIds.addLast(viewIds.get(i));
        }
    }

    /**
     * Set multiple Parent Layout Parameters
     * @param viewParentLayoutPara - List of Parent Parameters to add
     */
    public void setParentLinkedViewLayoutParams(LinkedList<Integer> viewParentLayoutPara) {
        for (int i = 0; i < viewParentLayoutPara.size(); i++) {
            viewLayoutParameters.addLast(viewParentLayoutPara.get(i));
        }
    }

    /**
     * Retrieves specific linked ID
     * @param i - Location of ID to retrieve
     * @return - returns linked ID value
     */
    public int getLinkedViewId(int i) {
        return linkedViewIds.get(i);
    }

    /**
     * Retrieves specific layout parameter for a linked view
     * @param i - Location of view ID to retrieve
     * @return - Returns layout parameter of linked view
     */
    public int getLinkedViewIdLayoutParam(int i) {
        return linkedViewLayoutParams.get(i);
    }

    /**
     * Retrieves all layout parameters relating to linked views.
     * @return - List of all linked layout parameters
     */
    public LinkedList<Integer> getLinkedViewLayoutParams() {
        return linkedViewLayoutParams;
    }
    public void setLinkedViewLayoutParams(LinkedList<Integer> viewLayoutPara) {
        for (int i = 0; i < viewLayoutPara.size(); i++) {
            linkedViewLayoutParams.addLast(viewLayoutPara.get(i));
        }
    }
	/* Check for Layout conflicts and errors, if no major errors then proceed with applying Layout Rule
    Also update Linked views and other views from parent change
	 */
    public int getParentLayoutParam(int i) {
        return viewLayoutParameters.get(i);
    }
    /*	Inputs: param = Layout Option selected from either Layout Parent or View Layout Dialogs
    *			linkedViewId = Either a -1 (Parent Layout selected) or view Id which will be checked
	 *			on the removeViewLayoutParam and checkLinkedViewParam functions
	 *			viewsOnLayout = LinkedList of the currently displayed views
	 */

    /**
     * Check for conflicts and add a new layout parameter if no conflict arises. Will call redrawView().
     * @param param - Layout parameter to add
     * @param linkedViewStringId - String ID of view being linked to this view. (null for parent)
     * @param linkViewId - ID of view being linked to this view with layout param (IMPORTANT: -1 for parent view)
     * @param listOfViews - All views currently present in the Activity.
     */
    public void addViewLayoutParam(int param, String linkedViewStringId, int linkViewId, UIelementList listOfViews) {
        RelativeLayout.LayoutParams tempParam = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if (checkParentLayoutParams(param, linkViewId, listOfViews)) {
            if (linkViewId == -1) {
                viewLayoutParameters.addLast(param);
                tempParam.addRule(param);
            } else {
                linkedViewIds.addLast(linkViewId);
                linkedViewStringIds.addLast(linkedViewStringId);
                linkedViewLayoutParams.addLast(param);
                tempParam.addRule(param, linkViewId);
                Log.v("StringIdLogged", linkedViewStringIds.getLast());
            }
            redrawView();
            if (inPreviewMode) {
                this.redrawView();
            } else {
                updateLinkedViews(listOfViews);
            }
        } else {
            Intent intent = new Intent("com.Protofy.com.Protofy.protofy.LAYOUT_CONFLICT_VIEW_OUTSIDE_SCREEN");
            mContext.sendBroadcast(intent);
        }
    }

    /**
     * Retrieve array of all layout parameters assigned to this view in a readable format
     * @return - List of all layout parameters in human readable strings
     */
    public LinkedList<String> getStringLayoutParams() {
        LinkedList<String> stringLayoutParams = new LinkedList<>();
        if (!viewLayoutParameters.isEmpty()) {
            for (int i = 0; i < viewLayoutParameters.size(); i++) {
                if (viewLayoutParameters.get(i) == RelativeLayout.ALIGN_PARENT_TOP) {
                    stringLayoutParams.addLast("Align Parent Top");
                } else if (viewLayoutParameters.get(i) == RelativeLayout.ALIGN_PARENT_START) {
                    stringLayoutParams.addLast("Align Parent Start");
                } else if (viewLayoutParameters.get(i) == RelativeLayout.ALIGN_PARENT_LEFT) {
                    stringLayoutParams.addLast("Align Parent Left");
                } else if (viewLayoutParameters.get(i) == RelativeLayout.ALIGN_PARENT_RIGHT) {
                    stringLayoutParams.addLast("Align Parent Right");
                } else if (viewLayoutParameters.get(i) == RelativeLayout.ALIGN_PARENT_BOTTOM) {
                    stringLayoutParams.addLast("Align Parent Bottom");
                } else if (viewLayoutParameters.get(i) == RelativeLayout.ALIGN_PARENT_END) {
                    stringLayoutParams.addLast("Align Parent End");
                } else if (viewLayoutParameters.get(i) == RelativeLayout.CENTER_HORIZONTAL) {
                    stringLayoutParams.addLast("Center Horizontal");
                } else if (viewLayoutParameters.get(i) == RelativeLayout.CENTER_VERTICAL) {
                    stringLayoutParams.addLast("Center Veritcal");
                } else if (viewLayoutParameters.get(i) == RelativeLayout.CENTER_IN_PARENT) {
                    stringLayoutParams.addLast("Center In Parent");
                }
            }
        }
        if (!linkedViewLayoutParams.isEmpty()) {
            for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                if (linkedViewLayoutParams.get(i) == RelativeLayout.ABOVE) {
                    stringLayoutParams.addLast("Align Above " + linkedViewStringIds.get(i));
                } else if (linkedViewLayoutParams.get(i) == RelativeLayout.BELOW) {
                    stringLayoutParams.addLast("Align Below " + linkedViewStringIds.get(i));
                } else if (linkedViewLayoutParams.get(i) == RelativeLayout.LEFT_OF) {
                    stringLayoutParams.addLast("Align Left Of " + linkedViewStringIds.get(i));
                } else if (linkedViewLayoutParams.get(i) == RelativeLayout.RIGHT_OF) {
                    stringLayoutParams.addLast("Align Right Of " + linkedViewStringIds.get(i));
                } else if (linkedViewLayoutParams.get(i) == RelativeLayout.ALIGN_BASELINE) {
                    stringLayoutParams.addLast("Align BaseLine to " + linkedViewStringIds.get(i));
                } else if (linkedViewLayoutParams.get(i) == RelativeLayout.END_OF) {
                    stringLayoutParams.addLast("Align End Of " + linkedViewStringIds.get(i));
                } else if (linkedViewLayoutParams.get(i) == RelativeLayout.ALIGN_LEFT) {
                    stringLayoutParams.addLast("Align Left Edges to " + linkedViewStringIds.get(i));
                } else if (linkedViewLayoutParams.get(i) == RelativeLayout.ALIGN_RIGHT) {
                    stringLayoutParams.addLast("Align Right Edges to " + linkedViewStringIds.get(i));
                } else if (linkedViewLayoutParams.get(i) == RelativeLayout.ALIGN_TOP) {
                    stringLayoutParams.addLast("Align Top Edges to " + linkedViewStringIds.get(i));
                } else if (linkedViewLayoutParams.get(i) == RelativeLayout.ALIGN_BOTTOM) {
                    stringLayoutParams.addLast("Align Bottom Edges to " + linkedViewStringIds.get(i));
                } else if (linkedViewLayoutParams.get(i) == RelativeLayout.ALIGN_START) {
                    stringLayoutParams.addLast("Align Start Edges to " + linkedViewStringIds.get(i));
                }
            }
        }
        return stringLayoutParams;
    }

    /**
     * Checks for any layout parameter conflicts that would occur if a specific relation to parent layout parameter was applied. IE: If a view
     * has align parent top as a layout param, adding a align parent bottom would create a conflict and error. Will remove conflicting parameter from view.
     * Will also check child view associations for conflicts depending on layout parameter
     * @param param - Param to check if a conflict exists
     * @param linkedViewId - ID of view
     * @param viewsOnLayout - All views currently present in the Activity.
     * @return - returns false if conflict exists
     */
    public boolean checkParentLayoutParams(int param, int linkedViewId, UIelementList viewsOnLayout) {
        if (param == RelativeLayout.ALIGN_PARENT_BOTTOM) {
            if (checkLinkedViewParams(RelativeLayout.BELOW, "View", viewsOnLayout)) {
                Log.v("Running Check", "Checking for conflicting Parent Params to remove");
                for (int i = 0; i < viewLayoutParameters.size(); i++) {
                    int lp = viewLayoutParameters.get(i);
                    if (lp == RelativeLayout.ALIGN_PARENT_TOP || lp == RelativeLayout.CENTER_IN_PARENT
                            || lp == RelativeLayout.CENTER_VERTICAL) {
                        Log.v("Error", "Conflicting Parent Param found, removing Param");
                        removeViewLayoutParam(lp, linkedViewId);
                    }
                }
                for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                    removeViewLayoutParam(linkedViewLayoutParams.get(i), linkedViewIds.get(i));
                }
                return true;
            } else
                return false;
        } else if (param == RelativeLayout.ALIGN_PARENT_TOP) {
            Log.v("Running Check", "Checking for conflicting Parent Params to remove");
            if (checkLinkedViewParams(RelativeLayout.ABOVE, "View", viewsOnLayout)) {
                for (int i = 0; i < viewLayoutParameters.size(); i++) {
                    int lp = viewLayoutParameters.get(i);
                    if (lp == RelativeLayout.ALIGN_PARENT_BOTTOM || lp == RelativeLayout.CENTER_VERTICAL
                            || lp == RelativeLayout.CENTER_IN_PARENT) {
                        Log.v("Error", "Conflicting Parent Param found, removing Param");
                        removeViewLayoutParam(lp, linkedViewId);
                    }
                }
                for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                    removeViewLayoutParam(linkedViewLayoutParams.get(i), linkedViewIds.get(i));
                }
                return true;
            } else
                return false;
        } else if (param == RelativeLayout.CENTER_IN_PARENT) {
            for (int i = 0; i < viewLayoutParameters.size(); i++) {
                int lp = viewLayoutParameters.get(i);
                if (lp == RelativeLayout.ALIGN_PARENT_BOTTOM || lp == RelativeLayout.ALIGN_PARENT_TOP
                        || lp == RelativeLayout.CENTER_VERTICAL || lp == RelativeLayout.CENTER_HORIZONTAL
                        || lp == RelativeLayout.ALIGN_PARENT_LEFT || lp == RelativeLayout.ALIGN_PARENT_RIGHT)
                    removeViewLayoutParam(lp, linkedViewId);
            }
            return true;
        } else if (param == RelativeLayout.CENTER_VERTICAL) {
            for (int i = 0; i < viewLayoutParameters.size(); i++) {
                int lp = viewLayoutParameters.get(i);
                if (lp == RelativeLayout.ALIGN_PARENT_BOTTOM || lp == RelativeLayout.ALIGN_PARENT_TOP)
                    removeViewLayoutParam(lp, linkedViewId);
            }
            for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                removeViewLayoutParam(linkedViewLayoutParams.get(i), linkedViewIds.get(i));
            }
            return true;
        } else if (param == RelativeLayout.CENTER_HORIZONTAL) {
            for (int i = 0; i < viewLayoutParameters.size(); i++) {
                int lp = viewLayoutParameters.get(i);
                if (lp == RelativeLayout.ALIGN_PARENT_LEFT || lp == RelativeLayout.ALIGN_PARENT_RIGHT)
                    removeViewLayoutParam(lp, linkedViewId);
            }
            for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                removeViewLayoutParam(linkedViewLayoutParams.get(i), linkedViewIds.get(i));
            }
            return true;
        } else if (param == RelativeLayout.ALIGN_PARENT_RIGHT) {
            if (checkLinkedViewParams(RelativeLayout.RIGHT_OF, "View", viewsOnLayout)) {
                for (int i = 0; i < viewLayoutParameters.size(); i++) {
                    int lp = viewLayoutParameters.get(i);
                    if (lp == RelativeLayout.CENTER_HORIZONTAL || lp == RelativeLayout.ALIGN_PARENT_LEFT)
                        removeViewLayoutParam(lp, linkedViewId);
                }
                return true;
            } else
                return false;
        } else if (param == RelativeLayout.ALIGN_PARENT_LEFT) {
            if (checkLinkedViewParams(RelativeLayout.LEFT_OF, "View", viewsOnLayout)) {
                for (int i = 0; i < viewLayoutParameters.size(); i++) {
                    int lp = viewLayoutParameters.get(i);
                    if (lp == RelativeLayout.CENTER_HORIZONTAL || lp == RelativeLayout.ALIGN_PARENT_RIGHT)
                        removeViewLayoutParam(lp, linkedViewId);
                }
                return true;
            } else
                return false;
        } else if (param == RelativeLayout.ABOVE) {
            if (checkLinkedViewParams(RelativeLayout.ALIGN_PARENT_TOP, "Parent", viewsOnLayout)) {
                for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                    int lp = linkedViewLayoutParams.get(i);
                    if (lp == RelativeLayout.BELOW || lp == RelativeLayout.ALIGN_PARENT_TOP || lp == RelativeLayout.ALIGN_PARENT_BOTTOM)
                        removeViewLayoutParam(lp, linkedViewId);
                }
                return true;
            } else
                return false;

        } else if (param == RelativeLayout.BELOW) {
            if (checkLinkedViewParams(RelativeLayout.ALIGN_PARENT_BOTTOM, "Parent", viewsOnLayout)) {
                for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                    int lp = linkedViewLayoutParams.get(i);
                    if (lp == RelativeLayout.ABOVE || lp == RelativeLayout.ALIGN_PARENT_TOP || lp == RelativeLayout.ALIGN_PARENT_BOTTOM)
                        removeViewLayoutParam(lp, linkedViewId);
                }
                return true;
            } else
                return false;
        } else if (param == RelativeLayout.RIGHT_OF) {
            if (checkLinkedViewParams(RelativeLayout.ALIGN_PARENT_RIGHT, "Parent", viewsOnLayout)) {
                for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                    int lp = linkedViewLayoutParams.get(i);
                    if (lp == RelativeLayout.LEFT_OF)
                        removeViewLayoutParam(lp, linkedViewId);
                }
                return true;
            } else
                return false;
        } else if (param == RelativeLayout.LEFT_OF) {
            if (checkLinkedViewParams(RelativeLayout.ALIGN_PARENT_LEFT, "Parent", viewsOnLayout)) {
                for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                    int lp = linkedViewLayoutParams.get(i);
                    if (lp == RelativeLayout.RIGHT_OF)
                        removeViewLayoutParam(lp, linkedViewId);
                }
                return true;
            } else
                return false;
        } else if (param == RelativeLayout.ALIGN_BASELINE) {
            for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                int lp = linkedViewLayoutParams.get(i);
                if (lp == RelativeLayout.ALIGN_PARENT_TOP || lp == RelativeLayout.ALIGN_PARENT_BOTTOM
                        || lp == RelativeLayout.ABOVE || lp == RelativeLayout.BELOW)
                    removeViewLayoutParam(lp, linkedViewId);
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * Check for any layout conflicts that may arise from setting a layout parameter that links two child views together.
     * @param conflictParam - Layout parameter to check.
     * @param checkType - Type of check to perform. "View" denotes checking for other child view conflicts that may arise (Views overlapping etc.)
     *                  "Parent" checks if linking the two views would cause a conflict with the parent viewgroup. (Placing view below a view who has ALIGN PARENT BOTTOM, making the view unselectable)
     * @param viewsOnLayout - All views currently present in the Activity.
     * @return - returns true if no conflict is found.
     */
    public boolean checkLinkedViewParams(int conflictParam, String checkType, UIelementList viewsOnLayout) {

        boolean noViewConflict = true;

        if (checkType.equals("View")) {
            for (int i = 0; i < viewsOnLayout.getSize(); i++) {
                if (viewsOnLayout.getView(i).getTag() == "TEXTVIEW") {
                    mTextView tv = new mTextView(mContext);
                    tv.initCopyView((mTextView) viewsOnLayout.getView(i), "id check");
                    LinkedList<Integer> tempLinkedViewIds = tv.getLinkedViews();
                    LinkedList<Integer> tempLinkedViewLayoutParams = tv.getLinkedViewLayoutParams();
                    for (int j = 0; j < tempLinkedViewIds.size(); j++) {
                        if (this.getViewId() == tempLinkedViewIds.get(j)) {
                            if (tempLinkedViewLayoutParams.get(j) == conflictParam) {
                                noViewConflict = false;
                            }
                        }
                    }
                } else if (viewsOnLayout.getView(i).getTag() == "BUTTON") {
                    mButton b = new mButton(mContext);
                    b.initCopyView((mButton) viewsOnLayout.getView(i), "id check");
                    LinkedList<Integer> tempLinkedViewIds = b.getLinkedViews();
                    LinkedList<Integer> tempLinkedViewLayoutParams = b.getLinkedViewLayoutParams();
                    for (int j = 0; j < tempLinkedViewIds.size(); j++) {
                        if (this.getViewId() == tempLinkedViewIds.get(j)) {
                            if (tempLinkedViewLayoutParams.get(j) == conflictParam) {
                                noViewConflict = false;
                            }
                        }
                    }
                } else if (viewsOnLayout.getView(i).getTag() == "EDITTEXT") {
                    mEditText ed = new mEditText(mContext);
                    ed.initCopyView((mEditText) viewsOnLayout.getView(i), "id check");
                    LinkedList<Integer> tempLinkedViewIds = ed.getLinkedViews();
                    LinkedList<Integer> tempLinkedViewLayoutParams = ed.getLinkedViewLayoutParams();
                    for (int j = 0; j < tempLinkedViewIds.size(); j++) {
                        if (this.getViewId() == tempLinkedViewIds.get(j)) {
                            if (tempLinkedViewLayoutParams.get(j) == conflictParam) {
                                noViewConflict = false;
                            }
                        }
                    }
                }
            }
        } else if (checkType.equals("Parent")) {
            for (int i = 0; i < viewsOnLayout.getSize(); i++) {
                if (viewsOnLayout.getView(i).getTag() == "TEXTVIEW") {
                    mTextView tv = new mTextView(mContext);
                    tv.initCopyView((mTextView) viewsOnLayout.getView(i), "id check");
                    LinkedList<Integer> tempLinkedViewLayoutParams = tv.getParentLayoutParams();
                    for (int j = 0; j < tempLinkedViewLayoutParams.size(); j++) {
                        if (this.getViewId() == tempLinkedViewLayoutParams.get(j)) {
                            if (tempLinkedViewLayoutParams.get(j) == conflictParam) {
                                noViewConflict = false;
                            }
                        }
                    }
                } else if (viewsOnLayout.getView(i).getTag() == "BUTTON") {
                    mButton b = new mButton(mContext);
                    b.initCopyView((mButton) viewsOnLayout.getView(i), "id check");
                    LinkedList<Integer> tempLinkedViewLayoutParams = b.getParentLayoutParams();
                    for (int j = 0; j < tempLinkedViewLayoutParams.size(); j++) {
                        if (this.getViewId() == tempLinkedViewLayoutParams.get(j)) {
                            if (tempLinkedViewLayoutParams.get(j) == conflictParam) {
                                //Intent intent = new Intent("com.Protofy.com.Protofy.protofy.LAYOUT_CONFLICT_VIEW_OUTSIDE_SCREEN");
                                //mContext.sendBroadcast(intent);
                                noViewConflict = false;
                            }
                        }
                    }
                } else if (viewsOnLayout.getView(i).getTag() == "EDITTEXT") {
                    mEditText ed = new mEditText(mContext);
                    ed.initCopyView((mEditText) viewsOnLayout.getView(i), "id check");
                    LinkedList<Integer> tempLinkedViewLayoutParams = ed.getParentLayoutParams();
                    for (int j = 0; j < tempLinkedViewLayoutParams.size(); j++) {
                        if (this.getViewId() == tempLinkedViewLayoutParams.get(j)) {
                            if (tempLinkedViewLayoutParams.get(j) == conflictParam) {
                                noViewConflict = false;
                            }
                        }
                    }
                }
            }
        }
        if (noViewConflict)
            Log.v("LinkedView Check:", "returned True");
        else
            Log.v("LinkedView Check:", "returned False");
        return noViewConflict;
    }

    /**
     * Checks for and removes layout parameter from view.
     * @param param - Layout parameter to remove
     * @param linkedViewId - ID of view you wish to disassociate view with. (-1 for parent)
     */
    public void removeViewLayoutParam(int param, int linkedViewId) {
        RelativeLayout.LayoutParams tempParam = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if (linkedViewId == -1) {
            for (int i = 0; i < viewLayoutParameters.size(); i++) {
                if (viewLayoutParameters.get(i) == param) {
                    viewLayoutParameters.remove(i);
                    tempParam.addRule(param, 0);
                    Log.v("Parent Layout", "Param removed");
                }
            }
        } else {
            for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
                if (linkedViewLayoutParams.get(i) == param) {
                    linkedViewLayoutParams.remove(i);
                    linkedViewStringIds.remove(i);
                    linkedViewIds.remove(i);
                    tempParam.addRule(param, 0);
                    Log.v("Linked Views", "Param removed");
                }
            }
        }
    }

    /**
     * Updates the layout of all linked views to this view. IE: If you update the position of a view, you should always
     * call updateLinkedViews to redraw all linked views.
     * @param viewsOntheLayout - All views currently on the activity layout
     */
    public void updateLinkedViews(UIelementList viewsOntheLayout) {
        for (int i = 0; i < viewsOntheLayout.getSize(); i++) {
            for (int j = 0; j < linkedViewIds.size(); j++) {
                if (viewsOntheLayout.getView(i).getId() == linkedViewIds.get(j)) {
                    if (viewsOntheLayout.getView(i).getTag() == "TEXTVIEW") {
                        mTextView tv = (mTextView) viewsOntheLayout.getView(i);
                        tv.redrawView();
                    } else if (viewsOntheLayout.getView(i).getTag() == "BUTTON") {
                        mButton b = (mButton) viewsOntheLayout.getView(i);
                        b.redrawView();
                    } else if (viewsOntheLayout.getView(i).getTag() == "EDITTEXT") {
                        mEditText et = (mEditText) viewsOntheLayout.getView(i);
                        et.redrawView();
                    }
                }
            }
        }
    }

    /*
    End ImageView Layout Options Setters and Getters
     */

    /**
     * Invalidate and redraw view with all applied attributes. Call this to force a redraw of view.
     * This is helpful when you change a view property that will not automatically trigger android to
     * invalidate the view.
     */
    public void redrawView() {
        RelativeLayout.LayoutParams tempParam = (RelativeLayout.LayoutParams) this.getLayoutParams();
        for (int i = 0; i < (viewLayoutParameters.size()); i++) {
            tempParam.addRule(viewLayoutParameters.get(i));
        }
        for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
            tempParam.addRule(linkedViewLayoutParams.get(i), linkedViewIds.get(i));
        }
        this.setLayoutParams(tempParam);
        this.requestLayout();
        this.setViewMargins(this.marginLeft, this.marginRight, this.marginTop, this.marginBottom);
        this.setViewPadding(this.leftPadding, this.rightPadding, this.topPadding, this.bottomPadding);
        if(this.getViewVisibility().equals("visible")) {
            this.setViewImage(backgroundBitmap);
        } else {
            this.setViewInvisibleImage();
        }
        this.setViewTintColor(tintColor, tintHexColor);
        this.setViewSize(this.width, this.height, "width and height");
    }

    /**
     * Allows for quick creation of a view by copying over existing attributes of an already existing view.
     * @param view - view you want to copy
     * @param option - type of copy you want to occur. IE: "set Text" will copy over only attributes pertaining to text displaying. Input nothing to copy over everything.
     */
    public void initCopyView(mImageView view, String option) {

        //The standard go to copy over all variables for setting Text and less intensive preview options
        if (option.equals("set Text")) {
            this.setViewVisibility(view.getViewVisibility());
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"), view.getViewPadding("top"), view.getViewPadding("bottom"));
            this.setLayoutParams(view.getLayoutParams());
        }
        //Copy over all variables for full layout representation when user is selecting view relation options
        else if (option.equals("set id as text")) {
            this.setStringID(view.getStringID());
            this.setParentIdentifier(view.getParentIdentifier());
            this.setViewID(view.getViewId());
            this.setViewImage(view.getViewImage());
            if(view.hasTintBeenAdded()){
                this.setViewTintColor(view.getViewTintColor(), view.getTintHexColor());
            }
            this.setLayoutParams(view.getLayoutParams());
            this.setViewSize(view.getViewSize("width"), view.getViewSize("height"), "width and height copy");
            this.setViewMargins(view.getViewMargins("left"), view.getViewMargins("right"), view.getViewMargins("top"), view.getViewMargins("bottom"));
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"), view.getViewPadding("top"), view.getViewPadding("bottom"));
            //this.redrawView();
        }
        //Copy over all variables for any preview view
        else if (option.equals("set Text /preview")) {
            this.setViewImage(view.getViewImage());
            if(view.hasTintBeenAdded()){
                this.setViewTintColor(view.getViewTintColor(), view.getTintHexColor());
            }
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"), view.getViewPadding("top"), view.getViewPadding("bottom"));
            RelativeLayout.LayoutParams previewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            previewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            this.setLayoutParams(previewParams);
        }
        //Copy over all variables required to check the views ID with another view
        else if (option.equals("id check")) {
            this.setStringID(view.getStringID());
            this.setViewID(view.getViewId());
            this.setLinkedViewIds(view.getLinkedViews());
            this.setLinkedViewLayoutParams(view.getLinkedViewLayoutParams());
            this.setParentLinkedViewLayoutParams(view.getParentLayoutParams());
        }
        //Catch all copy over all variables!
        else {
            this.setViewSize(view.getViewSize("width"), view.getViewSize("height"), "width and height copy");
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"), view.getViewPadding("top"), view.getViewPadding("bottom"));
            this.setLayoutParams(view.getLayoutParams());
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
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
    /*
    End Helper Functions
     */

    public boolean getExportLineSpacingEdited() {
        return exportLineSpacing;
    }
    private void setExportLineSpacingEdited(boolean value) {
        exportLineSpacing = value;
    }
    public boolean getExportColorEdited() {
        return exportColor;
    }
    private void setExportColorEdited(boolean value) {
        exportColor = value;
    }
    public boolean getExportTypefaceEdited() {
        return exportTypeface;
    }
    private void setExportTypefaceEdited(boolean value) {
        exportTypeface = value;
    }
    public boolean getExportPaddingEdited() {
        return exportPadding;
    }
    private void setExportPaddingEdited(boolean value) {
        exportPadding = value;
    }
    public boolean getExportTextSizeEdited() {
        return exportTextSize;
    }
    private void setExportTextSizeEdited(boolean value) {
        exportTextSize = value;
    }
    public boolean getExportMarginsEdited() {
        return exportMargins;
    }
    private void setExportMarginsEdited(boolean value) {
        exportMargins = value;
    }
    public boolean getExportParentLayoutsEdited() {
        return exportParentLayouts;
    }
    private void setExportParentLayoutsEdited(boolean value) {
        exportParentLayouts = value;
    }
    public boolean getExportLinkedViewsEdited() {
        return exportLinkedViews;
    }
    private void setExportLinkedViewsEdited(boolean value) {
        exportLinkedViews = value;
    }
    public boolean isrightMarginDefaultModified(){
        return imageViewPrefs.getDefaultImageViewRightMargin() != 0;
    }
    public boolean isleftMarginDefaultModified(){
        return imageViewPrefs.getDefaultImageViewLeftMargin() != 0;
    }
    public boolean istopMarginDefaultModified(){
        return imageViewPrefs.getDefaultImageViewTopMargin() != 0;
    }
    public boolean isbottomMarginDefaultModified(){
        return imageViewPrefs.getDefaultImageViewBottomMargin() != 0;
    }
    public boolean isrightPaddingDefaultModified(){
        return imageViewPrefs.getDefaultImageViewRightPadding() != 0;
    }
    public boolean isleftPaddingDefaultModified(){
        return imageViewPrefs.getDefaultImageViewLeftPadding() != 0;
    }
    public boolean istopPaddingDefaultModified(){
        return imageViewPrefs.getDefaultImageViewTopPadding() != 0;
    }
    public boolean isbottomPaddingDefaultModified(){
        return imageViewPrefs.getDefaultImageViewBottomPadding() != 0;
    }
}
