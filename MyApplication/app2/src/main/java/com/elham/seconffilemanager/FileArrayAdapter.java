package com.elham.seconffilemanager;


import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.list;

public class FileArrayAdapter extends ArrayAdapter<Item>{

    public static Object getS;
    private Context c;
    private int id;

    private List<Item>items;
    public SparseBooleanArray mSelectedItemsIds;
    public static  boolean showCheckBox;
    public static boolean statusLongclick2;
    public ArrayList<Integer> mSelectedItems;
    public int sf;


    public FileArrayAdapter(Context context, int textViewResourceId,
                            List<Item> objects) {
        super(context, textViewResourceId, objects);
        mSelectedItemsIds = new SparseBooleanArray();
        c = context;
        id = textViewResourceId;
        items = objects;
        mSelectedItems = new ArrayList<>();
    }

    public Item getItem(int i)
    {
        return items.get(i);
    }
    public void toggleSelection(int position) {


        selectView(position, !mSelectedItemsIds.get(position));
    }
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
    public int setPosition(int sf)
    {

        return  this.sf= sf;
    }


    public  class ViewHolder {
        protected TextView text;
        protected TextView text2;
        protected TextView text3;
        protected ImageView imageCity;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        final Item o =  items.get(position);
        if( convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(id, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text= (TextView) view.findViewById(R.id.TextView01);
            viewHolder.text2= (TextView) view.findViewById(R.id.TextView02);
            viewHolder.text3= (TextView) view.findViewById(R.id.TextViewDate);
            viewHolder.imageCity = (ImageView) view.findViewById(R.id.fd_Icon1);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
            viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Item o = (Item) cb.getTag();
                    o.setSelected(cb.isChecked());
                    toggleSelection(o.getPosiiton());

                }
            });

            o.setPosition(position);
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(o);
        }
        else
        {
            view= convertView;
            o.setPosition(position);
            ((ViewHolder) view.getTag()).checkbox.setTag(o);
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        if (showCheckBox && position !=0 ) {
            holder.checkbox.setVisibility(View.VISIBLE);
        } else

        {
            holder.checkbox.setVisibility(View.GONE);
        }
        holder.text.setText(o.getName());
        holder.text2.setText(o.getData());
        holder.text3.setText(o.getDate());
        String uri = "drawable/" + o.getImage();
        int imageResource = c.getResources().getIdentifier(uri, null, c.getPackageName());
        Drawable image = c.getResources().getDrawable(imageResource);
        holder.imageCity.setImageDrawable(image);
        holder.checkbox.setChecked(o.isSelected());
        view.setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4 : Color.TRANSPARENT);
        return view;

    }
}