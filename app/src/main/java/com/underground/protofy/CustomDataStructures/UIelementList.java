package com.underground.protofy.CustomDataStructures;

import android.view.View;

import com.underground.protofy.Whiteboard.Dialogs;

import java.util.LinkedList;

/**
 * Custom LinkedList controller for tracking whiteboard views. Being custom allows additional functionality in the future.
 */
public class UIelementList extends Dialogs {
    LinkedList<View> viewList;

    public void initializeUIdataStructure() {
        viewList = new LinkedList<View>();
    }

    public void addView(View view, Object object) {
        viewList.addLast(view);
        viewList.getLast().setTag(object);
    }

    public View getLast() {
        return viewList.getLast();
    }

    public View getView(int index) {
        return viewList.get(index);
    }

    public Object getViewTag(int index) {
        return viewList.get(index).getTag();
    }

    public void removeView(int index) {
        viewList.remove(index);
    }

    public int getSize() {
        return viewList.size();
    }
}
