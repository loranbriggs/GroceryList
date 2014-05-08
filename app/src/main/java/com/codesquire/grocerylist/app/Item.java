package com.codesquire.grocerylist.app;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by loran on 3/7/14.
 */

@ParseClassName("Item")
public class Item extends ParseObject {

    public Item() {

    }

    public boolean isCrossed() {
        return getBoolean("crossed");
    }

    public void setCrossed(boolean crossed) {
        put("crossed", crossed);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescritption(String description) {
        put("description", description);
    }

    public int getQuantity() {
        return getInt("quantity");
    }

    public void setQuantity(int quantity) {
        put("quantity", quantity);
    }

    public String getUnit() {
        return getString("unit");
    }

    public void setUnit(String unit) {
        put("unit", unit);
    }
}
