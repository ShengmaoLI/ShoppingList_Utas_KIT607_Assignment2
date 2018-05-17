package assignment2.sli18.utas.edu.au.lsmshopping;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import Controller.DBUtility;
import Controller.DataFilter;
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
        shoppingItems = DataFilter.getUnpurchasedData();
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_main_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        itemDetailAdapter = new ItemDetailAdapter(shoppingItems);
        recyclerView.setAdapter(itemDetailAdapter);

        ImageButton addImgBtn = findViewById(R.id.main_img_btn_add);
        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 17/5/18 Addition symbol
                Toast.makeText(MainActivity.this, "Addition symbol Listener",Toast.LENGTH_SHORT).show();
            }
        });
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
                        Log.d(TAG, "onClick: \n" + s.getName() + s.getDate());
                    }
                }
                itemDetailAdapter.notifyItemRangeRemoved(0,50);
                itemDetailAdapter.notifyDataSetChanged();
            }
        });
    }


}
