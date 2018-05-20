package assignment2.sli18.utas.edu.au.lsmshopping;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import Controller.DBUtility;
import Controller.DataFilter;
import Entity.ShoppingItem;
import Entity.ShoppingList;
import assignment2.sli18.utas.edu.au.lsmshopping.Adapters.ItemDetailAdapter;

public class MainActivity extends AppCompatActivity {

    /*
     * set static modifier for this adapter (itemDetailAdapter) since I need to notify the adapter that the data is changed
     * in AddItemActivity (another activity for this simple project)
     */
    static ItemDetailAdapter itemDetailAdapter;
    private static final String TAG = "MainActivity";// TODO: 18/05/2018  TAG is used for test, need to be removed after completing whole project
    public static List<ShoppingItem> shoppingItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get data that the user does not purchase.
        shoppingItems = DataFilter.getUnpurchasedData();
        //remove action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        //initialize recyclerView in main activity
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_main_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        itemDetailAdapter = new ItemDetailAdapter(shoppingItems);
        recyclerView.setAdapter(itemDetailAdapter);

        //set the listener for ImageButton of addition (the mid of the view, "Addition Symbol")
        ImageButton addImgBtn = findViewById(R.id.main_img_btn_add);
        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 17/5/18 Addition symbol
                Toast.makeText(MainActivity.this, "Addition symbol Listener",Toast.LENGTH_SHORT).show();
            }
        });

        //set listener and logic for "Finish" Button
        Button btnFinish = (Button) findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator<ShoppingItem> itemIterator = shoppingItems.iterator();
                while (itemIterator.hasNext()){
                    ShoppingItem s = itemIterator.next();
                    if (s.isPurchased() == true){
                        s.setDate(new Date());
                        s.update(s.getId());
                        itemIterator.remove();
                    }
                }
                itemDetailAdapter.notifyDataSetChanged();
            }
        });

        //set Listener and logic for "Expenditure" Button
        Button expBtn = findViewById(R.id.main_expenditure);
        expBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        //set Listener and logic for "Share" Button
        final Button shaBtn = findViewById(R.id.main_share);
        shaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String shoppingList = "Shopping List\n";
                    for (ShoppingItem shoppingItem : shoppingItems){
                        shoppingList += "---" + shoppingItem.getName() + "---\n";
                        shoppingList += "price: " + shoppingItem.getPrice() + "$\n";
                        shoppingList += "quantity: x" + shoppingItem.getQuantity() + "\n";
                    }
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+"0416177711"));
                    intent.putExtra("sms_body", shoppingList);
                    startActivity(intent);
                }
        });
    }

}
