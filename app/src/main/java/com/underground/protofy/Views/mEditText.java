package com.underground.protofy.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.underground.protofy.CustomDataStructures.UIelementList;
import com.underground.protofy.Preferences.EditTextPreferences;

import java.util.LinkedList;

/**
 * Custom EditText class to build upon Android's existing EditText class.
 */
public class mEditText extends EditText {
    private String hint;
    private String hintHexColor, backgroundHexColor, tintHexColor, visibility;
    private Context mContext;
    private String stringViewId;
    private LinkedList<Integer> viewLayoutParameters;
    private LinkedList<Integer> linkedViewIds;
    private LinkedList<Integer> linkedViewLayoutParams;
    private LinkedList<String> linkedViewStringIds;
    private String parentLayoutId;

    private int alpha, leftPadding, rightPadding, topPadding, bottomPadding,
            id, textColor, backgroundColor, typefaceStyle,
            oldBackgroundColor, oldTextColor, width, height,
            marginLeft, marginRight, marginTop, marginBottom, oldTintColor, tintColor;
    private float lineSpacingAdd, lineSpacingMulti, textSize, viewScaleX, viewScaleY;

    private boolean exportColor, exportTypeface, exportPadding, exportTextSize, exportMargins, exportLinkedViews, exportParentLayouts, exportLineSpacing, isViewSizeEdited;

    private Typeface textTypeface;
    private boolean inPreviewMode;
    private int widthWrappingParam, heightWrappingParam;

    private EditTextPreferences editTextPrefs;

    public mEditText(Context context) {
        super(context);
        mContext = context;
        initializeView();
    }

    /**
     * Convert DPs to pixel value
     * @param context - context of activity
     * @return - Density in pixels
     */
    public static float convRateDpToPx(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi / 160f;
    }

    /**
     * Initialize the custom EditText. Always call in constructor.
     */
    private void initializeView() {
        viewLayoutParameters = new LinkedList<>();
        linkedViewIds = new LinkedList<>();
        linkedViewLayoutParams = new LinkedList<>();
        linkedViewStringIds = new LinkedList<>();
        setClickable(true);
        setFocusable(false);

        editTextPrefs = new EditTextPreferences(mContext);

        hint = editTextPrefs.getDefaultEditTextText();
        visibility = "visible";

        if (editTextPrefs.getEditTextLayoutWrappingWidth().equals("0")) {
            widthWrappingParam = RelativeLayout.LayoutParams.WRAP_CONTENT;
        } else {
            widthWrappingParam = RelativeLayout.LayoutParams.MATCH_PARENT;
        }
        if (editTextPrefs.getEditTextLayoutWrappingHeight().equals("0")) {
            heightWrappingParam = RelativeLayout.LayoutParams.WRAP_CONTENT;
        } else {
            heightWrappingParam = RelativeLayout.LayoutParams.MATCH_PARENT;
        }

        this.setLayoutParams(new RelativeLayout.LayoutParams(widthWrappingParam, heightWrappingParam));

        textSize = editTextPrefs.getDefaultEditTextTextSize();
        viewScaleX = getScaleX();
        viewScaleY = getScaleY();
        hintHexColor = String.format("%08x", editTextPrefs.getEditTextTextColor());
        textColor = editTextPrefs.getEditTextTextColor();
        backgroundHexColor = String.format("%08x", editTextPrefs.getEditTextBackgroundColor());
        backgroundColor = editTextPrefs.getEditTextBackgroundColor();
        if (editTextPrefs.getDefaultEditTextFont().equals("0")) {
            textTypeface = Typeface.DEFAULT;
        } else if (editTextPrefs.getDefaultEditTextFont().equals("1")) {
            textTypeface = Typeface.MONOSPACE;
        } else if (editTextPrefs.getDefaultEditTextFont().equals("2")) {
            textTypeface = Typeface.SERIF;
        } else if (editTextPrefs.getDefaultEditTextFont().equals("3")) {
            textTypeface = Typeface.SANS_SERIF;
        }
        if (editTextPrefs.getDefaultEditTextEmphasis().equals("0")) {
            typefaceStyle = Typeface.NORMAL;
        } else if (editTextPrefs.getDefaultEditTextEmphasis().equals("1")) {
            typefaceStyle = Typeface.BOLD;
        } else if (editTextPrefs.getDefaultEditTextEmphasis().equals("2")) {
            typefaceStyle = Typeface.ITALIC;
        } else if (editTextPrefs.getDefaultEditTextEmphasis().equals("3")) {
            typefaceStyle = Typeface.BOLD_ITALIC;
        }

        exportColor = true;
        exportLineSpacing = true;
        exportLinkedViews = true;
        exportMargins = true;
        exportPadding = true;
        exportParentLayouts = true;
        exportTextSize = true;
        exportTypeface = true;
        isViewSizeEdited = false;

        leftPadding = editTextPrefs.getDefaultEditTextLeftPadding();
        rightPadding = editTextPrefs.getDefaultEditTextRightPadding();
        topPadding = editTextPrefs.getDefaultEditTextTopPadding();
        bottomPadding = editTextPrefs.getDefaultEditTextBottomPadding();

        marginLeft = editTextPrefs.getDefaultEditTextLeftMargin();
        marginRight = editTextPrefs.getDefaultEditTextRightMargin();
        marginBottom = editTextPrefs.getDefaultEditTextBottomMargin();
        marginTop = editTextPrefs.getDefaultEditTextTopMargin();

        lineSpacingAdd = editTextPrefs.getDefaultEditTextLineSpacing();
        lineSpacingMulti = lineSpacingAdd / 10;

        width = 100;
        height = 100;
        inPreviewMode = false;

        alpha = 255;

        if (editTextPrefs.getEditTextParentRelation().equals("1")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_TOP);
        } else if (editTextPrefs.getEditTextParentRelation().equals("2")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else if (editTextPrefs.getEditTextParentRelation().equals("3")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (editTextPrefs.getEditTextParentRelation().equals("4")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else if (editTextPrefs.getEditTextParentRelation().equals("5")) {
            addStartingParentLayoutParam(RelativeLayout.CENTER_IN_PARENT);
        } else if (editTextPrefs.getEditTextParentRelation().equals("6")) {
            addStartingParentLayoutParam(RelativeLayout.CENTER_HORIZONTAL);
        } else if (editTextPrefs.getEditTextParentRelation().equals("7")) {
            addStartingParentLayoutParam(RelativeLayout.CENTER_VERTICAL);
        } else {
        }

        setText(hint);
        setViewColor(textColor, "text", hintHexColor);
        setViewColor(backgroundColor, "background", backgroundHexColor);
        setViewTypeface(textTypeface);
        setViewTypefaceStyle(typefaceStyle);
        setViewTextSize(textSize);
        setViewAlpha(alpha);
        setViewPadding(leftPadding, rightPadding, topPadding, bottomPadding);
        setViewMargins(marginLeft, marginRight, marginTop, marginBottom);
        setViewLineSpacing(lineSpacingAdd, lineSpacingMulti);
    }

    /**
     * Associate Parent views ID that this EditText belongs to the EditText.
     * @param parentId - ID of parent viewgroup
     */
    public void setParentIdentifier(String parentId) {
        parentLayoutId = parentId;
    }
    public String getParentIdentifier() {
        return parentLayoutId;
    }

    /**
     * Set EditText ID so that we can refer to button by ID
     * @param viewId - ID you wish to associate with view.
     */
    public void setViewID(int viewId) {
        id = viewId;
        this.setId(id);
    }

    public void setViewLineSpacing(float add, float multi) {
        this.setLineSpacing(add, multi);
        lineSpacingAdd = add;
        lineSpacingMulti = multi;
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

    public void setViewPadding(int left, int right, int top, int bottom) {
        leftPadding = left;
        rightPadding = right;
        topPadding = top;
        bottomPadding = bottom;
        this.setPadding(getDipValueinPixels(leftPadding), getDipValueinPixels(topPadding),
                getDipValueinPixels(rightPadding), getDipValueinPixels(bottomPadding));
    }

    public void setViewMargins(int left, int right, int top, int bottom) {
        marginLeft = left;
        marginRight = right;
        marginTop = top;
        marginBottom = bottom;
        ((RelativeLayout.MarginLayoutParams) this.getLayoutParams()).setMargins(getDipValueinPixels(marginLeft), getDipValueinPixels(marginTop),
                getDipValueinPixels(marginRight), getDipValueinPixels(marginBottom));
    }

    public void setViewColor(int color, String option, String hexValue) {
        if (option.equals("hint")) {
            oldTextColor = textColor;
            textColor = color;
            hintHexColor = hexValue;
            if(this.getViewVisibility().equals("visible")) {
                this.setHintTextColor(textColor);
            }
        }
        if (option.equals("background")) {
            oldBackgroundColor = backgroundColor;
            backgroundColor = color;
            backgroundHexColor = hexValue;
            if(this.getViewVisibility().equals("visible")) {
                this.getBackground().setColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    public void setViewVisibility(String option){
        if(option.equals("visible")){
            visibility = "visible";
            this.setViewHint(hint);
            this.setViewColor(textColor, "hint", hintHexColor);
            this.setViewColor(backgroundColor, "background", backgroundHexColor);
        } else if (option.equals("invisible")) {
            visibility = "invisible";
            this.setHintTextColor(Color.parseColor("#33000000"));
            this.setHint("Invisible button");
            this.setBackgroundColor(Color.parseColor("#33ffffff"));
        }
    }
    public String getViewVisibility(){
        return visibility;
    }


    public void setViewScale(float scaleX, float scaleY, String option) {
        if (option.equals("Scale X")) {
            viewScaleX = scaleX;
            this.setScaleX(viewScaleX);
        }
        if (option.equals("Scale Y")) {
            viewScaleY = scaleY;
            this.setScaleY(viewScaleY);
        }
        if (option.equals("set Scale")) {
            viewScaleX = scaleX;
            viewScaleY = scaleY;
            this.setScaleX(viewScaleX);
            this.setScaleY(viewScaleY);
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
            //setViewTextSize((float) width);
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
    public void setViewSize(int givenWidth, int givenHeight, String option) {
        RelativeLayout.LayoutParams tempParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if (option.equals("width")) {
            width = givenWidth;
            tempParams.width = getDipValueinPixels(width);
        }
        if (option.equals("height")) {
            height = givenHeight;
            tempParams.height = getDipValueinPixels(height);
            //setViewTextSize((float) width);
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

    public void setLayoutWrapping(int widthParam, int heightParam) {
        RelativeLayout.LayoutParams tempParams = new RelativeLayout.LayoutParams(widthParam, heightParam);
        for (int i = 0; i < viewLayoutParameters.size(); i++) {
            tempParams.addRule(viewLayoutParameters.get(i));
        }
        for (int i = 0; i < linkedViewLayoutParams.size(); i++) {
            tempParams.addRule(linkedViewLayoutParams.get(i), linkedViewIds.get(i));
        }
        this.setLayoutParams(tempParams);
        this.redrawView();
    }

    public String getViewHint() {
        return hint;
    }

    public void setViewHint(String text) {
        hint = text;
        if(this.getViewVisibility().equals("visible")) {
            this.setHint(hint);
        }
    }

    public Typeface getViewTypeface() {
        return textTypeface;
    }

    public void setViewTypeface(Typeface mTypeface) {
        textTypeface = mTypeface;
        this.setTypeface(textTypeface, typefaceStyle);
    }

    public int getViewTypefaceStyle() {
        return typefaceStyle;
    }

    public void setViewTypefaceStyle(int style) {
        typefaceStyle = style;
        this.setTypeface(textTypeface, typefaceStyle);
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

    public float getViewLineSpacingAdd() {
        return lineSpacingAdd;
    }

    public float getViewLineSpacingMulti() {
        return lineSpacingMulti;
    }

    public int getViewAlpha() {
        return alpha;
    }

    public void setViewAlpha(int suppliedAlpha) {
        this.getBackground().setAlpha(suppliedAlpha);
        alpha = suppliedAlpha;
    }

    public int getViewColor(String option) {
        if (option.equals("hint"))
            return textColor;
        if (option.equals("background"))
            return backgroundColor;
        return 0;
    }

    public int getLastColor(String option) {
        if (option.equals("hint"))
            return oldTextColor;
        if (option.equals("background"))
            return oldBackgroundColor;
        return 0;
    }

    public float getViewTextSize() {
        return textSize;
    }

    public void setViewTextSize(float size) {
        textSize = size;
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
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

    /**
     * Set string ID to EditText for a user friendly ID.
     * @param viewId - string representation of ID you want to set on view.
     */
    public void setStringID(String viewId) {
        stringViewId = viewId;
    }

    public int getViewId() {
        return id;
    }

    /**
     * Get the IDs of all views linked to this EditText
     * @return List of View IDs
     */
    public LinkedList<Integer> getLinkedViews() {
        return linkedViewIds;
    }

    /**
     * Get all string IDs of all views linked to this EditText
     * @return List of View IDs in String format
     */
    public LinkedList<String> getLinkedViewStringIds() {
        return linkedViewStringIds;
    }

    /**
     * Get all parent layout parameters assigned to the EditText
     * @return - List of Layout Parameters (IE: RelativeLayout.CENTER_VERTICAL)
     */
    public LinkedList<Integer> getParentLayoutParams() {
        return viewLayoutParameters;
    }

    /**
     * Get last layout parameter linked to the EditText.
     * @return last layout param applied to the EditText
     */
    public int getLastChildLayoutParam() {
        return linkedViewLayoutParams.getLast();
    }

    /**
     * Get ID of the lat linked view to the EditText
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
     * Get color of background or text in hex.
     * @param option - option of either text or background color
     * @return - returns desired color hex as string
     */
    public String getColorHexValue(String option) {
        if (option.equals("hint"))
            return hintHexColor;
        if (option.equals("background"))
            return backgroundHexColor;
        else
            return null;
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

    /**
     * Add starting Parent layout parameters to EditText
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
        return editTextPrefs.getDefaultEditTextRightMargin() != 16;
    }
    public boolean isleftMarginDefaultModified(){
        return editTextPrefs.getDefaultEditTextLeftMargin() != 16;
    }
    public boolean istopMarginDefaultModified(){
        return editTextPrefs.getDefaultEditTextTopMargin() != 16;
    }
    public boolean isbottomMarginDefaultModified(){
        return editTextPrefs.getDefaultEditTextBottomMargin() != 16;
    }
    public boolean isrightPaddingDefaultModified(){
        return editTextPrefs.getDefaultEditTextRightPadding() != 24;
    }
    public boolean isleftPaddingDefaultModified(){
        return editTextPrefs.getDefaultEditTextLeftPadding() != 24;
    }
    public boolean istopPaddingDefaultModified(){
        return editTextPrefs.getDefaultEditTextTopPadding() != 16;
    }
    public boolean isbottomPaddingDefaultModified(){
        return editTextPrefs.getDefaultEditTextBottomPadding() != 16;
    }
    public boolean isTextFontDefaultModified(){
        return editTextPrefs.getDefaultEditTextFont() != "0";
    }
    public boolean isTextEmphasisDefaultModified(){
        return editTextPrefs.getDefaultEditTextEmphasis() != "0";
    }
    public boolean isTextLineSpacingDefaultModified(){
        return editTextPrefs.getDefaultEditTextLineSpacing() != 10;
    }

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
        this.setViewAlpha(this.getViewAlpha());
        this.setViewMargins(this.marginLeft, this.marginRight, this.marginTop, this.marginBottom);
        this.setViewPadding(this.leftPadding, this.rightPadding, this.topPadding, this.bottomPadding);
        if(this.getViewVisibility().equals("visible")) {
            this.setViewHint(this.hint);
            this.setViewColor(this.backgroundColor, "background", this.getColorHexValue("background"));
            this.setViewColor(this.textColor, "hint", this.getColorHexValue("hint"));
        } else {
            this.setHint("invisible edittext");
            this.setBackgroundColor(Color.parseColor("#33ffffff"));
            this.setHintTextColor(Color.parseColor("#33000000"));
        }
        if(isViewSizeEdited) {
            this.setViewSize(this.width, this.height, "width and height");
        }
        this.setViewLineSpacing(lineSpacingAdd, lineSpacingMulti);
        this.setViewTypeface(textTypeface);
        this.setViewTypefaceStyle(typefaceStyle);
        this.setViewTextSize(textSize);
    }

    /**
     * Allows for quick creation of a view by copying over existing attributes of an already existing view.
     * @param view - view you want to copy
     * @param option - type of copy you want to occur. IE: "set Text" will copy over only attributes pertaining to text displaying. Input nothing to copy over everything.
     */
    public void initCopyView(mEditText view, String option) {

        if (option.equals("set Text")) {
            this.setViewHint(view.getViewHint());
            this.setViewAlpha(view.getViewAlpha());
            this.setBackgroundColor(view.getViewColor("background"));
            this.setTextColor(view.getViewColor("hint"));
            this.setViewTypeface(view.getViewTypeface());
            this.setViewTypefaceStyle(view.getViewTypefaceStyle());
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"), view.getViewPadding("top"), view.getViewPadding("bottom"));
            this.setLayoutParams(view.getLayoutParams());
        } else if (option.equals("set id as text")) {
            this.setViewHint(view.getStringID());
            this.setStringID(view.getStringID());
            this.setParentIdentifier(view.getParentIdentifier());
            this.setViewID(view.getViewId());
            this.setViewAlpha(view.getViewAlpha());
            this.setViewColor(view.getViewColor("background"), "background", view.getColorHexValue("background"));
            this.setViewColor(view.getViewColor("hint"), "hint", view.getColorHexValue("hint"));
            this.setLayoutParams(view.getLayoutParams());
            this.setViewSize(view.getViewSize("width"), view.getViewSize("height"), "width and heightcopy ");
            this.setViewMargins(view.getViewMargins("left"), view.getViewMargins("right"), view.getViewMargins("top"), view.getViewMargins("bottom"));
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"), view.getViewPadding("top"), view.getViewPadding("bottom"));
            //this.redrawView();
        }
        //Copy over all variables for any preview view
        else if (option.equals("set Text /preview")) {
            this.setText(view.getViewHint());
            this.setViewAlpha(view.getViewAlpha());
            this.setHintTextColor(view.getViewColor("hint"));
            this.setViewTypeface(view.getViewTypeface());
            this.setViewTypefaceStyle(view.getViewTypefaceStyle());
            this.setViewSize(view.getViewSize("width"), view.getViewSize("height"), "width and height copy");
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"), view.getViewPadding("top"), view.getViewPadding("bottom"));
            RelativeLayout.LayoutParams previewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            previewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            this.setLayoutParams(previewParams);
        } else if (option.equals("id check")) {
            this.setStringID(view.getStringID());
            this.setViewID(view.getViewId());
            this.setLinkedViewIds(view.getLinkedViews());
            this.setLinkedViewLayoutParams(view.getLinkedViewLayoutParams());
            this.setParentLinkedViewLayoutParams(view.getParentLayoutParams());
        } else {
            this.setViewSize(view.getViewSize("width"), view.getViewSize("height"), "width and height copy");
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"), view.getViewPadding("top"), view.getViewPadding("bottom"));
            this.setViewAlpha(view.getViewAlpha());
            this.setBackgroundColor(view.getViewColor("background"));
            this.setTextColor(view.getViewColor("hint"));
            this.setLayoutParams(view.getLayoutParams());
        }
    }

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
}
