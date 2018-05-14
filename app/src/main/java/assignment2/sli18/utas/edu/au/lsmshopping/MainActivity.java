package assignment2.sli18.utas.edu.au.lsmshopping;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import Controller.DBUtility;
import Entity.ShoppingItem;
import Entity.ShoppingList;
import assignment2.sli18.utas.edu.au.lsmshopping.Adapters.ItemDetailAdapter;

public class MainActivity extends AppCompatActivity {
//---
    static ItemDetailAdapter itemDetailAdapter;
   //---
    private static final String TAG = "MainActivity";
    private List<ShoppingItem> shoppingItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shoppingItems = getData();
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_main_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        itemDetailAdapter = new ItemDetailAdapter(shoppingItems);
        recyclerView.setAdapter(itemDetailAdapter);

        ImageButton imageButton = findViewById(R.id.main_img_btn_add);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ShoppingItem s : getData()){
                    Log.d(TAG, "onClick: " + s.getName() + s.isPurchased() + s.getId());
                }
            }
        });


    }

    public static List<ShoppingItem> getData() {
            List<ShoppingItem> list = DataSupport.findAll(ShoppingItem.class);
            return list;
    }
}
