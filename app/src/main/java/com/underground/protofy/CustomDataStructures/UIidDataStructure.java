package com.underground.protofy.CustomDataStructures;

import java.util.LinkedList;

/**
 * Created by Dakota on 5/29/2015.
 */
public class UIidDataStructure {
    LinkedList<String> idList;

    public void initializeUIidDataStructure() {
        idList = new LinkedList<String>();
    }

    public void addId(String hex) {
        idList.addLast(hex);
    }

    public String getLast() {
        return idList.getLast();
    }

    public String getHexId(int index) {
        return idList.get(index);
    }

    public void removeId(int index) {
        idList.remove(index);
    }

    public int getSize() {
        return idList.size();
    }

    public LinkedList copyList() {
        return (LinkedList) idList.clone();
    }
}