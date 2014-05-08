package com.codesquire.grocerylist.app;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by loran on 3/7/14.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    private List<Item> mItems;

    public ItemAdapter(Context context, List<Item> objects) {
        super(context, R.layout.item_row_item, objects);
        this.mContext = context;
        this.mItems = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.item_row_item, null);
        }

        Item item = mItems.get(position);

        TextView itemView = (TextView) convertView.findViewById(R.id.itemDescription);
        TextView quantityView = (TextView) convertView.findViewById(R.id.itemQuantity);
        TextView unitsView = (TextView) convertView.findViewById(R.id.itemUnits);

        itemView.setText(item.getDescription());
        quantityView.setText(item.getQuantity() + "");
        unitsView.setText(item.getUnit());

        if (item.isCrossed()) {
            itemView.setPaintFlags(itemView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else{
            itemView.setPaintFlags(itemView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return convertView;
    }
}
