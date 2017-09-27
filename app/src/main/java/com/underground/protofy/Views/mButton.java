package com.underground.protofy.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.Protofy.protofy.R;
import com.underground.protofy.CustomDataStructures.UIelementList;
import com.underground.protofy.Preferences.ButtonPreferences;

import java.util.LinkedList;


/* Setters and Getters

   DONE:
   1. Alpha
   2. Padding
   3. LineSpacing
   4. Text
   5. ID system
   6. Color
   7. Text Size
   8. Scale
   9. Text Style and typeface
   10. Visibility
   11. View Size in Pixels and dp


   TODO:
   1. Shadow Layer
   2. Migrate to Hashmaps for performance
 */

/**
 * Custom Button class to build upon Android's existing Button class.
 */
public class mButton extends Button {
    private String Text = this.getResources().getString(R.string.view_init_text);
    private Context mContext;
    private String stringViewId;
    private LinkedList<Integer> viewLayoutParameters;
    private LinkedList<Integer> linkedViewIds;
    private LinkedList<Integer> linkedViewLayoutParams;
    private LinkedList<String> linkedViewStringIds;
    private String textHexColor, backgroundHexColor, visibility;
    private String parentLayoutId;

    private int alpha, textAlpha, leftPadding, rightPadding, topPadding, bottomPadding,
            id, textColor, backgroundColor, typefaceStyle,
            oldBackgroundColor, oldTextColor, width, height,
            marginLeft, marginRight, marginTop, marginBottom;
    private float lineSpacingAdd, lineSpacingMulti, textSize, viewScaleX, viewScaleY;

    private boolean exportColor, exportTypeface, exportPadding, exportTextSize, exportMargins, exportLinkedViews, exportParentLayouts, exportLineSpacing, isViewSizeEdited;

    private Typeface textTypeface;
    private boolean inPreviewMode;
    private int widthWrappingParam, heightWrappingParam;

    private ButtonPreferences buttonPrefs;

    public mButton(final Context c) {
        super(c);
        mContext = c;
        initializeView();
    }

    /**
     * Initialize the custom Button. Always call in constructor.
     */
    private void initializeView() {
        viewLayoutParameters = new LinkedList<>();
        linkedViewIds = new LinkedList<>();
        linkedViewLayoutParams = new LinkedList<>();
        linkedViewStringIds = new LinkedList<>();
        buttonPrefs = new ButtonPreferences(mContext);

        Text = buttonPrefs.getDefaultButtonText();
        visibility = "visible";

        if (buttonPrefs.getButtonLayoutWrappingWidth().equals("0")) {
            widthWrappingParam = RelativeLayout.LayoutParams.WRAP_CONTENT;
        } else {
            widthWrappingParam = RelativeLayout.LayoutParams.MATCH_PARENT;
        }
        if (buttonPrefs.getButtonLayoutWrappingHeight().equals("0")) {
            heightWrappingParam = RelativeLayout.LayoutParams.WRAP_CONTENT;
        } else {
            heightWrappingParam = RelativeLayout.LayoutParams.MATCH_PARENT;
        }

        this.setLayoutParams(new RelativeLayout.LayoutParams(widthWrappingParam, heightWrappingParam));

        textSize = buttonPrefs.getDefaultButtonTextSize();
        viewScaleX = getScaleX();
        viewScaleY = getScaleY();
        textHexColor = String.format("%08x", buttonPrefs.getButtonTextColor());
        textColor = buttonPrefs.getButtonTextColor();
        backgroundHexColor = String.format("%08x", buttonPrefs.getButtonBackgroundColor());
        backgroundColor = buttonPrefs.getButtonBackgroundColor();
        if (buttonPrefs.getDefaultButtonFont().equals("0")) {
            textTypeface = Typeface.DEFAULT;
        } else if (buttonPrefs.getDefaultButtonFont().equals("1")) {
            textTypeface = Typeface.MONOSPACE;
        } else if (buttonPrefs.getDefaultButtonFont().equals("2")) {
            textTypeface = Typeface.SERIF;
        } else if (buttonPrefs.getDefaultButtonFont().equals("3")) {
            textTypeface = Typeface.SANS_SERIF;
        }
        if (buttonPrefs.getDefaultButtonEmphasis().equals("0")) {
            typefaceStyle = Typeface.NORMAL;
        } else if (buttonPrefs.getDefaultButtonEmphasis().equals("1")) {
            typefaceStyle = Typeface.BOLD;
        } else if (buttonPrefs.getDefaultButtonEmphasis().equals("2")) {
            typefaceStyle = Typeface.ITALIC;
        } else if (buttonPrefs.getDefaultButtonEmphasis().equals("3")) {
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

        stringViewId = "";

        leftPadding = buttonPrefs.getDefaultButtonLeftPadding();
        rightPadding = buttonPrefs.getDefaultButtonRightPadding();
        topPadding = buttonPrefs.getDefaultButtonTopPadding();
        bottomPadding = buttonPrefs.getDefaultButtonBottomPadding();

        marginLeft = buttonPrefs.getDefaultButtonLeftMargin();
        marginRight = buttonPrefs.getDefaultButtonRightMargin();
        marginBottom = buttonPrefs.getDefaultButtonBottomMargin();
        marginTop = buttonPrefs.getDefaultButtonTopMargin();

        lineSpacingAdd = buttonPrefs.getDefaultButtonLineSpacing();
        lineSpacingMulti = lineSpacingAdd / 10;


        inPreviewMode = false;

        textAlpha = 255;
        alpha = 255;

        if (buttonPrefs.getButtonParentRelation().equals("1")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_TOP);
        } else if (buttonPrefs.getButtonParentRelation().equals("2")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else if (buttonPrefs.getButtonParentRelation().equals("3")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (buttonPrefs.getButtonParentRelation().equals("4")) {
            addStartingParentLayoutParam(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else if (buttonPrefs.getButtonParentRelation().equals("5")) {
            addStartingParentLayoutParam(RelativeLayout.CENTER_IN_PARENT);
        } else if (buttonPrefs.getButtonParentRelation().equals("6")) {
            addStartingParentLayoutParam(RelativeLayout.CENTER_HORIZONTAL);
        } else if (buttonPrefs.getButtonParentRelation().equals("7")) {
            addStartingParentLayoutParam(RelativeLayout.CENTER_VERTICAL);
        } else {}

        width = 100;
        height = 100;

        setText(Text);
        setViewColor(textColor, "text", textHexColor);
        setViewColor(backgroundColor, "background", backgroundHexColor);
        setViewTypeface(textTypeface);
        setViewTypefaceStyle(typefaceStyle);
        setViewTextSize(textSize);
        setViewPadding(leftPadding, rightPadding, topPadding, bottomPadding);
        setViewMargins(marginLeft, marginRight, marginTop, marginBottom);
        setViewLineSpacing(lineSpacingAdd, lineSpacingMulti);
        setViewVisibility(visibility);
    }

    /**
     * Associate Parent views ID that this Button belongs to the Button.
     * @param parentId - ID of parent viewgroup
     */
    public void setParentIdentifier(String parentId) {
        parentLayoutId = parentId;
    }
    public String getParentIdentifier() {
        return parentLayoutId;
    }

    /**
     * Set Button ID so that we can refer to button by ID
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
     * Set string ID to Button for a user friendly ID.
     * @param viewId - string representation of ID you want to set on view.
     */
    public void setStringID(String viewId) {
        stringViewId = viewId;
    }
    public String getStringID() {
        return stringViewId;
    }



    /*
     Start Button Text Options Setters and Getters
     ---------------------------------------------
     */
    public void setViewText(String text) {
        Text = text;
        if(this.getViewVisibility().equals("visible")) {
            this.setText(Text);
        }
    }
    public String getViewText() {
        return Text;
    }

    public void setViewTypeface(Typeface mTypeface) {
        textTypeface = mTypeface;
        this.setTypeface(textTypeface, typefaceStyle);
        setExportTypefaceEdited(true);
    }
    public Typeface getViewTypeface() {
        return textTypeface;
    }
    public void setViewTypefaceStyle(int style) {
        typefaceStyle = style;
        this.setTypeface(textTypeface, typefaceStyle);
        setExportTypefaceEdited(true);
    }
    public int getViewTypefaceStyle() {
        return typefaceStyle;
    }

    public float getViewTextSize() {
        return textSize;
    }
    public void setViewTextSize(float size) {
        textSize = size;
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        setExportTextSizeEdited(true);
    }

    public void setViewLineSpacing(float add, float multi) {
        this.setLineSpacing(add, multi);
        lineSpacingAdd = add;
        lineSpacingMulti = multi;
        setExportLineSpacingEdited(true);
    }
    public float getViewLineSpacingAdd() {
        return lineSpacingAdd;
    }
    public float getViewLineSpacingMulti() {
        return lineSpacingMulti;
    }

    /*
    End Button Text Options Setters and Getters
     */

    /*
    Start Button Appearance Setters and Getters
    NOTE: Preview methods are used for preview views while methods without preview will set the final user choose values.
    -------------------------------------------
     */
    public void setViewColor(int color, String option, String hexValue) {
        if (option.equals("text")) {
            oldTextColor = textColor;
            textColor = color;
            textHexColor = hexValue;
            if(this.getViewVisibility().equals("visible")) {
                this.setTextColor(textColor);
            }
        }
        if (option.equals("background")) {
            oldBackgroundColor = backgroundColor;
            backgroundColor = color;
            backgroundHexColor = hexValue;
            if(this.getViewVisibility().equals("visible")) {
                this.setBackgroundColor(backgroundColor);
            }
        }
        setExportColorEdited(true);
    }
    public int getViewColor(String option) {
        if (option.equals("text"))
            return textColor;
        if (option.equals("background"))
            return backgroundColor;
        return 0;
    }
    public int getLastColor(String option) {
        if (option.equals("text"))
            return oldTextColor;
        if (option.equals("background"))
            return oldBackgroundColor;
        return 0;
    }

    public void setViewPadding(int left, int right, int top, int bottom) {
        leftPadding = left;
        rightPadding = right;
        topPadding = top;
        bottomPadding = bottom;
        this.setPadding(getDipValueinPixels(leftPadding), getDipValueinPixels(topPadding),
                getDipValueinPixels(rightPadding), getDipValueinPixels(bottomPadding));
        setExportPaddingEdited(true);
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
            this.setViewText(Text);
            this.setViewColor(textColor, "text", textHexColor);
            this.setViewColor(backgroundColor, "background", backgroundHexColor);
        } else if (option.equals("invisible")) {
            visibility = "invisible";
            this.setTextColor(Color.parseColor("#33000000"));
            this.setText("Invisible button");
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
    /*
    End Button Appearance Setter and Getters
     */


    /*
    Start Button Layout Setters and Getters
    ---------------------------------------
    NOTE: Layout checking algorithm and "Parent Relation", "View Relations" code is at the bottom to
    reduce hunting time
    NOTE2: Preview methods are used for preview views while methods without preview will set the final user choose values.
     */

    public void setViewMargins(int left, int right, int top, int bottom) {
        marginLeft = left;
        marginRight = right;
        marginTop = top;
        marginBottom = bottom;
        ((RelativeLayout.MarginLayoutParams) this.getLayoutParams()).setMargins(getDipValueinPixels(marginLeft), getDipValueinPixels(marginTop),
                getDipValueinPixels(marginRight), getDipValueinPixels(marginBottom));
        setExportMarginsEdited(true);
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
        this.setLayoutParams(tempParams);
        width = tempParams.width;
        height = tempParams.height;
        isViewSizeEdited = false;
        this.redrawView();
    }

    /**
     * Add starting Parent layout parameters to Button
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

        // Input check, let's make sure we don't go outside parameters.
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
     * Get the IDs of all views linked to this Button
     * @return List of View IDs
     */
    public LinkedList<Integer> getLinkedViews() {
        return linkedViewIds;
    }

    /**
     * Get all string IDs of all views linked to this Button
     * @return List of View IDs in String format
     */
    public LinkedList<String> getLinkedViewStringIds() {
        return linkedViewStringIds;
    }

    /**
     * Get all parent layout parameters assigned to the Button
     * @return - List of Layout Parameters (IE: RelativeLayout.CENTER_VERTICAL)
     */
    public LinkedList<Integer> getParentLayoutParams() {
        return viewLayoutParameters;
    }


    /**
     * Get last layout parameter linked to the Button.
     * @return last layout param applied to the Button
     */
    public int getLastChildLayoutParam() {
        return linkedViewLayoutParams.getLast();
    }

    /**
     * Get ID of the lat linked view to the Button
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
        if (option.equals("text"))
            return textHexColor;
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
            // Check to see if we're applying parameter to parent or another child view
            if (linkViewId == -1) {
                viewLayoutParameters.addLast(param);
                tempParam.addRule(param);
                setExportParentLayoutsEdited(true);
            } else {
                linkedViewIds.addLast(linkViewId);
                linkedViewStringIds.addLast(linkedViewStringId);
                linkedViewLayoutParams.addLast(param);
                tempParam.addRule(param, linkViewId);
                setExportLinkedViewsEdited(true);
                Log.v("StringIdLogged", linkedViewStringIds.getLast());
            }
            redrawView();

            // If we're applying layout parameter to a preview view, we must force multiple redraws on view.
            if (inPreviewMode) {
                this.redrawView();
            } else {
                updateLinkedViews(listOfViews);
            }
        } else {
            // Layout Parameter would force view outside of screen, notify Activity of error.
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
    End Button Layout Options Setters and Getters
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
            this.setViewText(this.Text);
            this.setViewColor(this.backgroundColor, "background", this.getColorHexValue("background"));
            this.setViewColor(this.textColor, "text", this.getColorHexValue("text"));
        } else {
            this.setText("invisible button");
            this.setBackgroundColor(Color.parseColor("#33ffffff"));
            this.setTextColor(Color.parseColor("#33000000"));
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
    public void initCopyView(mButton view, String option) {

        //The standard go to copy over all variables for setting Text and less intensive preview options
        if (option.equals("set Text")) {
            this.setText(view.getViewText());
            this.setBackgroundColor(view.getViewColor("background"));
            this.setTextColor(view.getViewColor("text"));
            this.setViewTypeface(view.getViewTypeface());
            this.setViewTypefaceStyle(view.getViewTypefaceStyle());
            this.setViewVisibility(view.getViewVisibility());
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"), view.getViewPadding("top"), view.getViewPadding("bottom"));
            this.setLayoutParams(view.getLayoutParams());
        }
        //Copy over all variables for full layout representation when user is selecting view relation options
        else if (option.equals("set id as text")) {
            this.setViewText(view.getViewText());
            this.setStringID(view.getStringID());
            this.setParentIdentifier(view.getParentIdentifier());
            this.setViewID(view.getViewId());
            this.setViewColor(view.getViewColor("background"), "background", view.getColorHexValue("background"));
            this.setViewColor(view.getViewColor("text"), "text", view.getColorHexValue("text"));
            this.setLayoutParams(view.getLayoutParams());
            //this.setViewSize(view.getViewSize("width"), view.getViewSize("height"), "width and height copy");
            this.setViewMargins(view.getViewMargins("left"), view.getViewMargins("right"), view.getViewMargins("top"), view.getViewMargins("bottom"));
            this.setViewPadding(view.getViewPadding("left"), view.getViewPadding("right"), view.getViewPadding("top"), view.getViewPadding("bottom"));
            //this.redrawView();
        }
        //Copy over all variables for any preview view
        else if (option.equals("set Text /preview")) {
            this.setText(view.getViewText());
            this.setBackgroundColor(view.getViewColor("background"));
            this.setTextColor(view.getViewColor("text"));
            this.setViewTypeface(view.getViewTypeface());
            this.setViewTypefaceStyle(view.getViewTypefaceStyle());
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
            this.setBackgroundColor(view.getViewColor("background"));
            this.setTextColor(view.getViewColor("text"));
            this.setLayoutParams(view.getLayoutParams());
        }
    }


    /*
    XML exporting checks, we only need to export values that have been modified, otherwise we simply default to Android standards.
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
        return buttonPrefs.getDefaultButtonRightMargin() != 16;
    }
    public boolean isleftMarginDefaultModified(){
        return buttonPrefs.getDefaultButtonLeftMargin() != 16;
    }
    public boolean istopMarginDefaultModified(){
        return buttonPrefs.getDefaultButtonTopMargin() != 16;
    }
    public boolean isbottomMarginDefaultModified(){
        return buttonPrefs.getDefaultButtonBottomMargin() != 16;
    }
    public boolean isrightPaddingDefaultModified(){
        return buttonPrefs.getDefaultButtonRightPadding() != 24;
    }
    public boolean isleftPaddingDefaultModified(){
        return buttonPrefs.getDefaultButtonLeftPadding() != 24;
    }
    public boolean istopPaddingDefaultModified(){
        return buttonPrefs.getDefaultButtonTopPadding() != 16;
    }
    public boolean isbottomPaddingDefaultModified(){
        return buttonPrefs.getDefaultButtonBottomPadding() != 16;
    }
    public boolean isTextFontDefaultModified(){
        return buttonPrefs.getDefaultButtonFont() != "0";
    }
    public boolean isTextEmphasisDefaultModified(){
        return buttonPrefs.getDefaultButtonEmphasis() != "0";
    }
    public boolean isTextLineSpacingDefaultModified(){
        return buttonPrefs.getDefaultButtonLineSpacing() != 10;
    }

    /*
    End XML export checks
     */

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


    /*
     Start Misc. Functions of things that might be coming later
     */
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

    public float getViewScale(String option) {
        if (option.equals("Scale X"))
            return viewScaleX;
        if (option.equals("Scale Y"))
            return viewScaleY;
        return 0;
    }
    /*
    End Misc. Functions of things that might be coming later
     */
}