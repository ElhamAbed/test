package com.elham.seconffilemanager;

import android.view.View;

/**
 * Created by Elham on 4/17/2017.
 */


public class Item implements Comparable<Item>{
    private String name;
    private String data;
    private String date;
    private String path;
    private String image;
    private boolean selected;
    private View view;
    int position;
    public Item(String n,String d, String dt, String p, String img)
    {
        name = n;
        data = d;
        date = dt;
        path = p;
        image = img;
        selected = false;
    }
    public String getName()
    {
        return name;
    }
    public String getData()
    {
        return data;
    }
    public String getDate()
    {
        return date;
    }
    public String getPath()
    {
        return path;
    }
    public String getImage() {
        return image;
    }
    public int compareTo(Item o) {
        if(this.name != null)
            return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
        else
            throw new IllegalArgumentException();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosiiton() {
        return this.position;
    }
}