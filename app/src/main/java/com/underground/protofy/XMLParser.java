package com.underground.protofy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.widget.RelativeLayout;

import com.underground.protofy.CustomDataStructures.UIelementList;
import com.underground.protofy.Preferences.RelativeLayoutPreferences;
import com.underground.protofy.Views.mButton;
import com.underground.protofy.Views.mEditText;
import com.underground.protofy.Views.mImageView;
import com.underground.protofy.Views.mRelativeLayout;
import com.underground.protofy.Views.mTextView;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;


public class XMLParser {
    Context mContext;
    XmlSerializer mSerializer;
    RelativeLayoutPreferences relativelayoutPrefs;

    public XMLParser(Context c) {
        mContext = c;
        relativelayoutPrefs = new RelativeLayoutPreferences(mContext);
    }


    /**
     * Parse through view elements currently on the screen and output .xml file for that layout.
     * @param fileName - File name user specifies from UI.
     * @param listOfViews - List of all views on the current working app layout.
     */
    public void writeXML(String fileName, UIelementList listOfViews) {
        File directoryFile = new File(Environment.getExternalStorageDirectory() + "/Protofy_Layouts/res/layout");
        File pictureDirectoryFile = new File(Environment.getExternalStorageDirectory() + "/Protofy_Layouts/res/drawable");
        String path = Environment.getExternalStorageDirectory() + "/Protofy_Layouts/res/drawable";
        int imageviewPictureCount = 0;

        if (!directoryFile.exists())
            directoryFile.mkdirs();
        if (!pictureDirectoryFile.exists())
            pictureDirectoryFile.mkdirs();

        File newXMLFile = new File(directoryFile, (fileName + ".xml"));
        FileOutputStream fileOutStream = null;
        mSerializer = Xml.newSerializer();

        try {
            if (!newXMLFile.exists())
                newXMLFile.createNewFile();
            if (newXMLFile.exists()) {
                newXMLFile.delete();
                newXMLFile.createNewFile();
            } else
                newXMLFile.canWrite();
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }

        try {
            fileOutStream = new FileOutputStream(newXMLFile);
        } catch (FileNotFoundException fnfe) {
            Log.e("File Not Found", fnfe.getMessage());
        }

        try {
            mSerializer.setOutput(fileOutStream, "UTF-8");
            mSerializer.startDocument(null, Boolean.valueOf(true));
            mSerializer.comment("File is Generated using the UI creator for android, Protofy! Thanks for your support and continued use of the app." +
                    "Protofy XML exporter Version: 0.2.0");
            mSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            // also set the line separator
            //mSerializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-line-separator", "\n");

            //mSerializer.comment("Setting up the base layout Params");


            for (int i = 0; i < listOfViews.getSize(); i++) {
                mRelativeLayout currentWorkingLayout = (mRelativeLayout) listOfViews.getView(i);
                UIelementList tiedViews = currentWorkingLayout.getLayoutViews();
                Object layoutTag = listOfViews.getViewTag(i);
                if(layoutTag == "RELATIVE_LAYOUT" || layoutTag == "BASE_LAYOUT") {
                    mSerializer.startTag(null, "RelativeLayout");
                    mSerializer.attribute(null, "xmlns:android", "http://schemas.android.com/apk/res/android");
                    mSerializer.attribute(null, "android:layout_width", "fill_parent");
                    mSerializer.attribute(null, "android:layout_height", "fill_parent");
                    mSerializer.attribute(null, "android:background", "#" + currentWorkingLayout.getColorHexValue("background"));

                    if(currentWorkingLayout.hasTintBeenAdded()){
                        mSerializer.attribute(null, "android:backgroundTint", "#"+currentWorkingLayout.getTintHexColor());
                        if(relativelayoutPrefs.getDefaultTintMode().equals("0")){
                            mSerializer.attribute(null, "android:backgroundTintMode", "src_atop");
                        }
                    }
                    if(currentWorkingLayout.hasBackgroundImage()) {

                        OutputStream fOut = null;
                        File file = new File(path, "baselayoutdrawable.png"); // the File to save to
                        fOut = new FileOutputStream(file);

                        Bitmap pictureBitmap = currentWorkingLayout.getViewBackgroundImage(); // obtaining the Bitmap
                        pictureBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                        fOut.flush();
                        fOut.close(); // do not forget to close the stream
                        mSerializer.attribute(null, "android:background", "@drawable/baselayoutdrawable");
                    }
                    if (currentWorkingLayout.getExportPaddingEdited()) {
                        mSerializer.attribute(null, "android:paddingLeft", "" + currentWorkingLayout.getViewPadding("left") + "dp");
                        mSerializer.attribute(null, "android:paddingRight", "" + currentWorkingLayout.getViewPadding("right") + "dp");
                        mSerializer.attribute(null, "android:paddingTop", "" + currentWorkingLayout.getViewPadding("top") + "dp");
                        mSerializer.attribute(null, "android:paddingBottom", "" + currentWorkingLayout.getViewPadding("bottom") + "dp");
                    }

                    /* -2 equals wrap_content, -1 equals fill_parent*/
                    for (int j = 0; j < tiedViews.getSize(); j++) {
                        Object tag = tiedViews.getViewTag(j);
                        if (tag == "TEXTVIEW") {
                            mTextView tv = (mTextView) tiedViews.getView(j);
                            mSerializer.comment("Generated xml for TextView with id " + tv.getStringID());
                            //tv.initCopyView((mTextView) listOfViews.getView(i), "");
                            //Start TextView Tag for given TextView
                            mSerializer.startTag(null, "TextView");
                            mSerializer.attribute(null, "android:id", "@+id/" + tv.getStringID());              //Assigns id to View for reference later (on SDK)
                            mSerializer.attribute(null, "android:text", tv.getViewText());                      //Writes current text to XMLmSerializer.attribute(null, "android:typeface", tv.getTypeface().toString()); //Writes Typeface to XML

                            if(tv.isTextFontDefaultModified()) {
                                if (tv.getViewTypeface() == Typeface.DEFAULT)
                                    mSerializer.attribute(null, "android:typeface", "normal");
                                else if (tv.getViewTypeface() == Typeface.MONOSPACE)
                                    mSerializer.attribute(null, "android:typeface", "monospace");
                                else if (tv.getViewTypeface() == Typeface.SERIF)
                                    mSerializer.attribute(null, "android:typeface", "serif");
                                else if (tv.getViewTypeface() == Typeface.SANS_SERIF)
                                    mSerializer.attribute(null, "android:typeface", "sans");
                            }

                            if(tv.isTextEmphasisDefaultModified()) {
                                if (tv.getViewTypefaceStyle() == Typeface.NORMAL)
                                    mSerializer.attribute(null, "android:textStyle", "normal");
                                else if (tv.getViewTypefaceStyle() == Typeface.BOLD)
                                    mSerializer.attribute(null, "android:textStyle", "bold");
                                else if (tv.getViewTypefaceStyle() == Typeface.ITALIC)
                                    mSerializer.attribute(null, "android:textStyle", "italic");
                                else if (tv.getViewTypefaceStyle() == Typeface.BOLD_ITALIC)
                                    mSerializer.attribute(null, "android:textStyle", "bold|italic");
                            }
                            if (tv.getExportTextSizeEdited()) {
                                mSerializer.attribute(null, "android:textSize", tv.getViewTextSize() + "sp");           //Writes textSize attribute to XML
                            }
                            if (tv.getExportColorEdited()) {
                                mSerializer.attribute(null, "android:textColor", "#" + tv.getColorHexValue("text"));      //Writes current TextColor attribute
                                mSerializer.attribute(null, "android:background", "#" + tv.getColorHexValue("background"));
                            }

                            if(tv.getViewSize("check") == 0){
                                mSerializer.attribute(null, "android:width", tv.getViewSize("width")+"dp");
                                mSerializer.attribute(null, "android:height", tv.getViewSize("height")+"dp");
                            }

					/*Start write current Layout Params to XML*/
                            if (tv.getLayoutParams().width == -1 && tv.getLayoutParams().height == -1) {
                                mSerializer.attribute(null, "android:layout_width", "fill_parent");
                                mSerializer.attribute(null, "android:layout_height", "fill_parent");
                            } else if (tv.getLayoutParams().width == -1 && tv.getLayoutParams().height == -2) {
                                mSerializer.attribute(null, "android:layout_width", "fill_parent");
                                mSerializer.attribute(null, "android:layout_height", "wrap_content");
                            } else if (tv.getLayoutParams().width == -2 && tv.getLayoutParams().height == -2) {
                                mSerializer.attribute(null, "android:layout_width", "wrap_content");
                                mSerializer.attribute(null, "android:layout_height", "wrap_content");
                            } else if (tv.getLayoutParams().width == -2 && tv.getLayoutParams().height == -1) {
                                mSerializer.attribute(null, "android:layout_width", "wrap_content");
                                mSerializer.attribute(null, "android:layout_height", "fill_parent");
                            }/*End Layout Options*/

                            if(tv.getViewVisibility().equals("invisible")){
                                mSerializer.attribute(null, "android:visibility", "invisible");
                            }

                            if (tv.getExportParentLayoutsEdited()) {
                                writeParentLayoutParams(tv.getParentLayoutParams());
                            }
                            if (tv.getExportLinkedViewsEdited()) {
                                writeLinkedViewLayoutParams(tv.getLinkedViewLayoutParams(), tv.getLinkedViewStringIds());
                            }

                            if (tv.getExportMarginsEdited()) {
                                if(tv.isleftMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginLeft", "" + tv.getViewMargins("left") + "dp");
                                } else if (tv.isrightMarginDefaultModified()){
                                    mSerializer.attribute(null, "android:layout_marginRight", "" + tv.getViewMargins("right") + "dp");
                                } else if (tv.istopMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginTop", "" + tv.getViewMargins("top") + "dp");
                                } else if (tv.isbottomMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginBottom", "" + tv.getViewMargins("bottom") + "dp");
                                }
                            }

                            if (tv.getExportPaddingEdited()) {
                                if(tv.isleftPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingLeft", tv.getViewPadding("left") + "dp");                 //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (tv.isrightPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingRight", tv.getViewPadding("right") + "dp");               //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (tv.istopPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingTop", tv.getViewPadding("top") + "dp");                   //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (tv.isbottomPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingBottom", tv.getViewPadding("bottom") + "dp");             //Writes out the padding attribute that is applied 0 if no padding applied
                                }
                            }
                            //mSerializer.attribute(null, "android:width", tv.getWidth() + "");                                   // Writes out the Views width attribute
                            //mSerializer.attribute(null, "android:height", tv.getHeight() + "");                                 // Writes out the Views height attribute
                            //mSerializer.attribute(null, "android:textScaleX", tv.getTextScaleX() + "");                       //Writes out the TextScaleX attribute

                            mSerializer.endTag(null, "TextView");                                                               //Ends TextView XML Tag
                        }
                        else if (tag == "BUTTON") {
                            mButton b = (mButton) tiedViews.getView(j);
                            mSerializer.comment("Generated xml for Button with id " + b.getStringID());
                            mSerializer.startTag(null, "Button");
                            mSerializer.attribute(null, "android:id", "@+id/" + b.getStringID());              //Assigns id to View for reference later (on SDK)
                            mSerializer.attribute(null, "android:text", b.getViewText());                      //Writes current text to XMLmSerializer.attribute(null, "android:typeface", b.getTypeface().toString()); //Writes Typeface to XML

                            if (b.isTextFontDefaultModified()) {
                                if (b.getViewTypeface() == Typeface.DEFAULT)
                                    mSerializer.attribute(null, "android:typeface", "normal");
                                else if (b.getViewTypeface() == Typeface.MONOSPACE)
                                    mSerializer.attribute(null, "android:typeface", "monospace");
                                else if (b.getViewTypeface() == Typeface.SERIF)
                                    mSerializer.attribute(null, "android:typeface", "serif");
                                else if (b.getViewTypeface() == Typeface.SANS_SERIF)
                                    mSerializer.attribute(null, "android:typeface", "sans");
                            }
                            if (b.isTextEmphasisDefaultModified()) {
                                if (b.getViewTypefaceStyle() == Typeface.NORMAL)
                                    mSerializer.attribute(null, "android:textStyle", "normal");
                                else if (b.getViewTypefaceStyle() == Typeface.BOLD)
                                    mSerializer.attribute(null, "android:textStyle", "bold");
                                else if (b.getViewTypefaceStyle() == Typeface.ITALIC)
                                    mSerializer.attribute(null, "android:textStyle", "italic");
                                else if (b.getViewTypefaceStyle() == Typeface.BOLD_ITALIC)
                                    mSerializer.attribute(null, "android:textStyle", "bold|italic");
                            }
                            if (b.getExportTextSizeEdited()) {
                                mSerializer.attribute(null, "android:textSize", b.getViewTextSize() + "sp");           //Writes textSize attribute to XML
                            }
                            mSerializer.attribute(null, "android:textColor", "#" + b.getColorHexValue("text"));      //Writes current TextColor attribute
                            mSerializer.attribute(null, "android:background", "#" + b.getColorHexValue("background"));

                            if(b.getViewSize("check") == 0){
                                mSerializer.attribute(null, "android:width", b.getViewSize("width")+"dp");
                                mSerializer.attribute(null, "android:height", b.getViewSize("height")+"dp");
                            }

                    /*Start write current Layout Params to XML*/
                            if (b.getLayoutParams().width == -1 && b.getLayoutParams().height == -1) {
                                mSerializer.attribute(null, "android:layout_width", "fill_parent");
                                mSerializer.attribute(null, "android:layout_height", "fill_parent");
                            } else if (b.getLayoutParams().width == -1 && b.getLayoutParams().height == -2) {
                                mSerializer.attribute(null, "android:layout_width", "fill_parent");
                                mSerializer.attribute(null, "android:layout_height", "wrap_content");
                            } else if (b.getLayoutParams().width == -2 && b.getLayoutParams().height == -2) {
                                mSerializer.attribute(null, "android:layout_width", "wrap_content");
                                mSerializer.attribute(null, "android:layout_height", "wrap_content");
                            } else if (b.getLayoutParams().width == -2 && b.getLayoutParams().height == -1) {
                                mSerializer.attribute(null, "android:layout_width", "wrap_content");
                                mSerializer.attribute(null, "android:layout_height", "fill_parent");
                            }/*End Layout Options*/

                            if(b.getViewVisibility().equals("invisible")){
                                mSerializer.attribute(null, "android:visibility", "invisible");
                            }

                            if (b.getExportParentLayoutsEdited()) {
                                writeParentLayoutParams(b.getParentLayoutParams());
                            }
                            if (b.getExportLinkedViewsEdited()) {
                                writeLinkedViewLayoutParams(b.getLinkedViewLayoutParams(), b.getLinkedViewStringIds());
                            }

                            if (b.getExportMarginsEdited()) {
                                if(b.isleftMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginLeft", "" + b.getViewMargins("left") + "dp");
                                } else if (b.isrightMarginDefaultModified()){
                                    mSerializer.attribute(null, "android:layout_marginRight", "" + b.getViewMargins("right") + "dp");
                                } else if (b.istopMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginTop", "" + b.getViewMargins("top") + "dp");
                                } else if (b.isbottomMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginBottom", "" + b.getViewMargins("bottom") + "dp");
                                }
                            }

                            if (b.getExportPaddingEdited()) {
                                if(b.isleftPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingLeft", b.getViewPadding("left") + "dp");                 //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (b.isrightPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingRight", b.getViewPadding("right") + "dp");               //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (b.istopPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingTop", b.getViewPadding("top") + "dp");                   //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (b.isbottomPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingBottom", b.getViewPadding("bottom") + "dp");             //Writes out the padding attribute that is applied 0 if no padding applied
                                }
                            }
                            //mSerializer.attribute(null, "android:width", b.getWidth() + "");                                   // Writes out the Views width attribute
                            //mSerializer.attribute(null, "android:height", b.getHeight() + "");                                 // Writes out the Views height attribute
                            mSerializer.endTag(null, "Button");
                        }
                        else if (tag == "EDITTEXT") {
                            mEditText ed = (mEditText) tiedViews.getView(j);
                            mSerializer.comment("Generated xml for EditText with id " + ed.getStringID());
                            mSerializer.startTag(null, "EditText");
                            mSerializer.attribute(null, "android:id", "@+id/" + ed.getStringID());              //Assigns id to View for reference later (on SDK)
                            mSerializer.attribute(null, "android:hint", ed.getViewHint());                      //Writes current text to XMLmSerializer.attribute(null, "android:typeface", ed.getTypeface().toString()); //Writes Typeface to XML
                            if (ed.isTextFontDefaultModified()) {
                                if (ed.getViewTypeface() == Typeface.DEFAULT)
                                    mSerializer.attribute(null, "android:typeface", "normal");
                                else if (ed.getViewTypeface() == Typeface.MONOSPACE)
                                    mSerializer.attribute(null, "android:typeface", "monospace");
                                else if (ed.getViewTypeface() == Typeface.SERIF)
                                    mSerializer.attribute(null, "android:typeface", "serif");
                                else if (ed.getViewTypeface() == Typeface.SANS_SERIF)
                                    mSerializer.attribute(null, "android:typeface", "sans");
                            }
                            if (ed.isTextEmphasisDefaultModified()) {
                                if (ed.getViewTypefaceStyle() == Typeface.NORMAL)
                                    mSerializer.attribute(null, "android:textStyle", "normal");
                                else if (ed.getViewTypefaceStyle() == Typeface.BOLD)
                                    mSerializer.attribute(null, "android:textStyle", "bold");
                                else if (ed.getViewTypefaceStyle() == Typeface.ITALIC)
                                    mSerializer.attribute(null, "android:textStyle", "italic");
                                else if (ed.getViewTypefaceStyle() == Typeface.BOLD_ITALIC)
                                    mSerializer.attribute(null, "android:textStyle", "bold|italic");
                            }

                            if (ed.getExportTextSizeEdited()) {
                                mSerializer.attribute(null, "android:textSize", ed.getViewTextSize() + "sp");           //Writes textSize attribute to XML
                            }
                            if (ed.getExportColorEdited()) {
                                mSerializer.attribute(null, "android:textColor", "#" + ed.getColorHexValue("text"));      //Writes current TextColor attribute
                                mSerializer.attribute(null, "android:background", "#" + ed.getColorHexValue("background"));
                            }

                            if(ed.getViewSize("check") == 0){
                                mSerializer.attribute(null, "android:width", ed.getViewSize("width")+"dp");
                                mSerializer.attribute(null, "android:height", ed.getViewSize("height")+"dp");
                            }

					/*Start write current Layout Params to XML*/
                            if (ed.getLayoutParams().width == -1 && ed.getLayoutParams().height == -1) {
                                mSerializer.attribute(null, "android:layout_width", "fill_parent");
                                mSerializer.attribute(null, "android:layout_height", "fill_parent");
                            } else if (ed.getLayoutParams().width == -1 && ed.getLayoutParams().height == -2) {
                                mSerializer.attribute(null, "android:layout_width", "fill_parent");
                                mSerializer.attribute(null, "android:layout_height", "wrap_content");
                            } else if (ed.getLayoutParams().width == -2 && ed.getLayoutParams().height == -2) {
                                mSerializer.attribute(null, "android:layout_width", "wrap_content");
                                mSerializer.attribute(null, "android:layout_height", "wrap_content");
                            } else if (ed.getLayoutParams().width == -2 && ed.getLayoutParams().height == -1) {
                                mSerializer.attribute(null, "android:layout_width", "wrap_content");
                                mSerializer.attribute(null, "android:layout_height", "fill_parent");
                            }/*End Layout Options*/

                            if(ed.getViewVisibility().equals("invisible")){
                                mSerializer.attribute(null, "android:visibility", "invisible");
                            }

                            if (ed.getExportParentLayoutsEdited()) {
                                writeParentLayoutParams(ed.getParentLayoutParams());
                            }

                            //writes out the Linked View Layout Parameters
                            if (ed.getExportLinkedViewsEdited()) {
                                writeLinkedViewLayoutParams(ed.getLinkedViewLayoutParams(), ed.getLinkedViewStringIds());
                            }

                            if (ed.getExportMarginsEdited()) {
                                if(ed.isleftMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginLeft", "" + ed.getViewMargins("left") + "dp");
                                } else if (ed.isrightMarginDefaultModified()){
                                    mSerializer.attribute(null, "android:layout_marginRight", "" + ed.getViewMargins("right") + "dp");
                                } else if (ed.istopMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginTop", "" + ed.getViewMargins("top") + "dp");
                                } else if (ed.isbottomMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginBottom", "" + ed.getViewMargins("bottom") + "dp");
                                }
                            }

                            if (ed.getExportPaddingEdited()) {
                                if(ed.isleftPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingLeft", ed.getViewPadding("left") + "dp");                 //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (ed.isrightPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingRight", ed.getViewPadding("right") + "dp");               //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (ed.istopPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingTop", ed.getViewPadding("top") + "dp");                   //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (ed.isbottomPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingBottom", ed.getViewPadding("bottom") + "dp");             //Writes out the padding attribute that is applied 0 if no padding applied
                                }
                            }
                            //mSerializer.attribute(null, "android:width", ed.getWidth() + "");                                   // Writes out the Views width attribute
                            //mSerializer.attribute(null, "android:height", ed.getHeight() + "");                                 // Writes out the Views height attribute
                            mSerializer.endTag(null, "EditText");
                        }
                        else if (tag == "IMAGEVIEW") {
                            mImageView iv = (mImageView) listOfViews.getView(j);
                            mSerializer.comment("Generated xml for ImageView with id " + iv.getStringID());
                            mSerializer.startTag(null, "ImageView");
                            mSerializer.attribute(null, "android:id", "@+id/" + iv.getStringID());              //Assigns id to View for reference later (on SDK)
                            if(iv.hasTintBeenAdded()){
                                mSerializer.attribute(null, "android:tint", "#"+iv.getTintHexColor());
                                mSerializer.attribute(null, "android:tintMode", iv.getViewTintMode());
                            }
                            OutputStream fOut = null;
                            File file = new File(path, "image"+imageviewPictureCount+".png"); // the File to save to
                            fOut = new FileOutputStream(file);

                            Bitmap pictureBitmap = iv.getViewImage(); // obtaining the Bitmap
                            pictureBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                            fOut.flush();
                            fOut.close(); // do not forget to close the stream
                            mSerializer.attribute(null, "android:background", "@drawable/image"+imageviewPictureCount);

                            imageviewPictureCount = imageviewPictureCount + 1;

                            mSerializer.attribute(null, "android:layout_width", iv.getViewSize("width")+"dp");
                            mSerializer.attribute(null, "android:layout_height", iv.getViewSize("height")+"dp");

					/*Start write current Layout Params to XML*/
                            if (iv.getLayoutParams().width == -1 && iv.getLayoutParams().height == -1) {
                                mSerializer.attribute(null, "android:layout_width", "fill_parent");
                                mSerializer.attribute(null, "android:layout_height", "fill_parent");
                            } else if (iv.getLayoutParams().width == -1 && iv.getLayoutParams().height == -2) {
                                mSerializer.attribute(null, "android:layout_width", "fill_parent");
                                mSerializer.attribute(null, "android:layout_height", "wrap_content");
                            } else if (iv.getLayoutParams().width == -2 && iv.getLayoutParams().height == -2) {
                                mSerializer.attribute(null, "android:layout_width", "wrap_content");
                                mSerializer.attribute(null, "android:layout_height", "wrap_content");
                            } else if (iv.getLayoutParams().width == -2 && iv.getLayoutParams().height == -1) {
                                mSerializer.attribute(null, "android:layout_width", "wrap_content");
                                mSerializer.attribute(null, "android:layout_height", "fill_parent");
                            }/*End Layout Options*/

                            if(iv.getViewVisibility().equals("invisible")){
                                mSerializer.attribute(null, "android:visibility", "invisible");
                            }

                            writeParentLayoutParams(iv.getParentLayoutParams());

                            //writes out the Linked View Layout Parameters
                            writeLinkedViewLayoutParams(iv.getLinkedViewLayoutParams(), iv.getLinkedViewStringIds());

                            if (iv.getExportMarginsEdited()) {
                                if(iv.isleftMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginLeft", "" + iv.getViewMargins("left") + "dp");
                                } else if (iv.isrightMarginDefaultModified()){
                                    mSerializer.attribute(null, "android:layout_marginRight", "" + iv.getViewMargins("right") + "dp");
                                } else if (iv.istopMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginTop", "" + iv.getViewMargins("top") + "dp");
                                } else if (iv.isbottomMarginDefaultModified()) {
                                    mSerializer.attribute(null, "android:layout_marginBottom", "" + iv.getViewMargins("bottom") + "dp");
                                }
                            }

                            if (iv.getExportPaddingEdited()) {
                                if(iv.isleftPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingLeft", iv.getViewPadding("left") + "dp");                 //Writes out the padding attribute that is appliiv 0 if no padding applied
                                } else if (iv.isrightPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingRight", iv.getViewPadding("right") + "dp");               //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (iv.istopPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingTop", iv.getViewPadding("top") + "dp");                   //Writes out the padding attribute that is applied 0 if no padding applied
                                } else if (iv.isbottomPaddingDefaultModified()) {
                                    mSerializer.attribute(null, "android:paddingBottom", iv.getViewPadding("bottom") + "dp");             //Writes out the padding attribute that is applied 0 if no padding applied
                                }
                            }
                            //mSerializer.attribute(null, "android:width", iv.getWidth() + "");                                   // Writes out the Views width attribute
                            //mSerializer.attribute(null, "android:height", iv.getHeight() + "");                                 // Writes out the Views height attribute
                            mSerializer.endTag(null, "ImageView");
                        }
                    }
                    mSerializer.endTag(null, "RelativeLayout");
                }
            }
            mSerializer.endDocument();
            mSerializer.flush();
            try {
                fileOutStream.flush();
                fileOutStream.close();
            } catch (NullPointerException npe) {
                Log.e("Null Pointer Exception", npe.getMessage());
            }

            Intent intent = new Intent("com.Protofy.com.Protofy.protofy.XML_SUCCESS");
            mContext.getApplicationContext().sendBroadcast(intent);


        } catch (IOException e) {
            Log.e("Exception", e.getMessage());
        }
    }

    /**
     * Handle a child views relationship to it's parent. (RelativeLayout only)
     * @param parentParams - List of all parent layout options set on child view.
     */
    private void writeParentLayoutParams(LinkedList<Integer> parentParams) {
        try {
            for (int j = 0; j < parentParams.size(); j++) {
                switch (parentParams.get(j)) {
                    case RelativeLayout.ALIGN_PARENT_LEFT:
                        mSerializer.attribute(null, "android:layout_alignParentLeft", "true");
                        break;
                    case RelativeLayout.ALIGN_PARENT_RIGHT:
                        mSerializer.attribute(null, "android:layout_alignParentRight", "true");
                        break;
                    case RelativeLayout.ALIGN_PARENT_TOP:
                        mSerializer.attribute(null, "android:layout_alignParentTop", "true");
                        break;
                    case RelativeLayout.ALIGN_PARENT_BOTTOM:
                        mSerializer.attribute(null, "android:layout_alignParentBottom", "true");
                        break;
                    case RelativeLayout.CENTER_IN_PARENT:
                        mSerializer.attribute(null, "android:layout_centerInParent", "true");
                        break;
                    case RelativeLayout.CENTER_HORIZONTAL:
                        mSerializer.attribute(null, "android:layout_centerHorizontal", "true");
                        break;
                    case RelativeLayout.CENTER_VERTICAL:
                        mSerializer.attribute(null, "android:layout_centerVertical", "true");
                        break;
                }
            }
        } catch (IOException ioe) {
            Log.e("IOException Caught", ioe.getMessage());
        }
    }

    /**
     * Handles writing xml for inner view relationships (Relative Layout). IE. RelativeLayout.ABOVE
     * @param linkedViewLayoutParams - all relationships of other views to current working view.
     * @param linkedViewStringIds - Id's of all linked views to current working view
     */
    private void writeLinkedViewLayoutParams(LinkedList<Integer> linkedViewLayoutParams, LinkedList<String> linkedViewStringIds) {
        try {
            for (int j = 0; j < linkedViewLayoutParams.size(); j++) {
                switch (linkedViewLayoutParams.get(j)) {
                    case RelativeLayout.ABOVE:
                        mSerializer.attribute(null, "android:layout_above", "@+id/" + linkedViewStringIds.get(j));
                        break;
                    case RelativeLayout.BELOW:
                        mSerializer.attribute(null, "android:layout_below", "@+id/" + linkedViewStringIds.get(j));
                        break;
                    case RelativeLayout.LEFT_OF:
                        mSerializer.attribute(null, "android:layout_toLeftOf", "@+id/" + linkedViewStringIds.get(j));
                        break;
                    case RelativeLayout.RIGHT_OF:
                        mSerializer.attribute(null, "android:layout_toRightOf", "@+id/" + linkedViewStringIds.get(j));
                        break;
                    case RelativeLayout.ALIGN_BASELINE:
                        mSerializer.attribute(null, "android:layout_alignBaseline", "@+id/" + linkedViewStringIds.get(j));
                        break;
                    case RelativeLayout.ALIGN_END:
                        mSerializer.attribute(null, "android:layout_alignEnd", "@+id/" + linkedViewStringIds.get(j));
                        break;
                    case RelativeLayout.ALIGN_LEFT:
                        mSerializer.attribute(null, "android:layout_alignLeft", "@+id/" + linkedViewStringIds.get(j));
                        break;
                    case RelativeLayout.ALIGN_RIGHT:
                        mSerializer.attribute(null, "android:layout_alignRight", "@+id/" + linkedViewStringIds.get(j));
                        break;
                    case RelativeLayout.ALIGN_TOP:
                        mSerializer.attribute(null, "android:layout_alignTop", "@+id/" + linkedViewStringIds.get(j));
                        break;
                }
                Log.v("number of Linked", linkedViewStringIds.size() + "");
                Log.v("idName", linkedViewStringIds.get(j));
            }
        } catch (IOException ioe) {
            Log.e("IOException Caught", ioe.getMessage());
        }
    }
}
