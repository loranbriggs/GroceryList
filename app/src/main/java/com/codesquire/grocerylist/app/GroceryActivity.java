package com.codesquire.grocerylist.app;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;

import java.util.ArrayList;
import java.util.List;


public class GroceryActivity extends Activity {

    private EditText itemInput;
    private EditText quantityInput;
    private Spinner unitsInput;
    private ListView itemList;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        Parse.initialize(this, "mVOtIjVcwaiJKWvN0XNSD2isZxW4CfL0KFgdxfPJ", "oZmtxo9PtXKKDDaTLiRZRegysNkkBQFpW48bq6J9");
        ParseAnalytics.trackAppOpened(getIntent());
        ParseObject.registerSubclass(Item.class);

        unitsInput = (Spinner) findViewById(R.id.unitsInput);
        itemInput = (EditText) findViewById(R.id.itemInput);
        quantityInput = (EditText) findViewById(R.id.quantityInput);
        itemList = (ListView) findViewById(R.id.itemList);

        // drop down unit picker
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units, android.R.layout.simple_dropdown_item_1line);
        unitsInput.setAdapter(adapter);

        itemAdapter = new ItemAdapter(this, new ArrayList<Item>());
        itemList.setAdapter(itemAdapter);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = itemAdapter.getItem(position);
                TextView itemDescription = (TextView) view.findViewById(R.id.itemDescription);

                item.setCrossed(!item.isCrossed());

                if(item.isCrossed()){
                    itemDescription.setPaintFlags(itemDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    itemDescription.setPaintFlags(itemDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }

                item.saveEventually();
            }
        });

        updateData();

    }

    public void createItem(View v) {
        if (itemInput.getText().length() > 0) {
            Item item = new Item();
            item.setDescritption(itemInput.getText().toString());
            item.setCrossed(false);
            item.setQuantity( Integer.parseInt(quantityInput.getText().toString()) );
            item.setUnit(unitsInput.getSelectedItem().toString());
            item.saveEventually();
            itemInput.setText("");

            itemAdapter.insert(item, 0);
        }
    }

    public void updateData() {
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<Item>() {

            @Override
            public void done(List<Item> items, ParseException error) {
                if(items != null){
                    itemAdapter.clear();
                    itemAdapter.addAll(items);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grocery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
