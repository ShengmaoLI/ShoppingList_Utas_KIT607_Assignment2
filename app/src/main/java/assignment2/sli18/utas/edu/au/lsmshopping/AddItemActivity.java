package assignment2.sli18.utas.edu.au.lsmshopping;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.w3c.dom.ProcessingInstruction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import Controller.DBUtility;
import Controller.DataFilter;
import Entity.ShoppingItem;
import assignment2.sli18.utas.edu.au.lsmshopping.Adapters.AddedListAdapter;
import assignment2.sli18.utas.edu.au.lsmshopping.Adapters.PurchasedListAdapter;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";
    public static PurchasedListAdapter purchasedListAdapter;
    public static AddedListAdapter addedListAdapter;
    public static List<ShoppingItem> purchasedList = DataFilter.getPurchasedData();
    public static List<ShoppingItem> addedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //initialize data
        purchasedList = DataFilter.getPurchasedData();

        //remove initial title layout
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        /*
         * The section of editing
         * The first section in this activity
         * */

        //get each views
        final EditText editName = findViewById(R.id.nameEdit);
        final EditText editTag = findViewById(R.id.tagEdit);
        final EditText editComment = findViewById(R.id.commentEdit);
        final EditText editPrice = findViewById(R.id.priceEdit);
        final EditText editQuantity = findViewById(R.id.quantityEdit);
        ImageButton editImgBtn = findViewById(R.id.imgEdit);
        ImageButton submitImgBtn = findViewById(R.id.submit_add);

        //get data test
        final Integer itemId = getIntent().getIntExtra("itemId", -1);
        if (itemId != -1) {
            ShoppingItem shoppingItem = DataSupport.find(ShoppingItem.class, itemId);
            editName.setText(shoppingItem.getName());
            editTag.setText(shoppingItem.getTag());
            editComment.setText(shoppingItem.getCommend());
            editPrice.setText(Double.toString(shoppingItem.getPrice()));
            editQuantity.setText(Integer.toString(shoppingItem.getQuantity()));
            //start editing mode
            ((RecyclerView) findViewById(R.id.purchased_recyclerView)).setVisibility(View.INVISIBLE);
            ((RecyclerView) findViewById(R.id.added_list_recyclerView)).setVisibility(View.INVISIBLE);
        }
        //submit listener
        submitImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemId != -1) {
                    ShoppingItem shoppingItem = DataSupport.find(ShoppingItem.class, itemId);
                    shoppingItem.setName(editName.getText().toString());
                    shoppingItem.setTag(editTag.getText().toString());
                    shoppingItem.setCommend(editComment.getText().toString());
                    shoppingItem.setPrice(Double.parseDouble(editPrice.getText().toString()));
                    shoppingItem.setQuantity(Integer.parseInt(editQuantity.getText().toString()));

                    if (!shoppingItem.getName().equals("")) {
                        shoppingItem.save();
                        MainActivity.itemDetailAdapter.notifyDataSetChanged();
                        Toast.makeText(AddItemActivity.this, "Edit Item Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddItemActivity.this, "Editing Item failed! \n"
                                + "at least type a name!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Double price = !editPrice.getText().toString().equals("") ? Double.parseDouble(editPrice.getText().toString()) : 0;
                    Integer quantity = !editQuantity.getText().toString().equals("") ? Integer.parseInt(editQuantity.getText().toString()) : 0;
                    ShoppingItem shoppingItem = new ShoppingItem();
                    shoppingItem.setName(editName.getText().toString());
                    shoppingItem.setTag(editTag.getText().toString());
                    shoppingItem.setCommend(editComment.getText().toString());
                    shoppingItem.setPrice(price);
                    shoppingItem.setQuantity(quantity);
                    if (!shoppingItem.getName().equals("")) {
                        addedList.add(shoppingItem);
                        addedListAdapter.notifyDataSetChanged();
                        Toast.makeText(AddItemActivity.this, "Add Item Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddItemActivity.this, "Adding Item failed! \n"
                                + "at least type a name!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        /*
         * Purchased List View
         *
         * */
        final RecyclerView purchasedRecyclerView = (RecyclerView) findViewById(R.id.purchased_recyclerView);
        LinearLayoutManager purchasedListLayoutManager = new LinearLayoutManager(this);
        purchasedRecyclerView.setLayoutManager(purchasedListLayoutManager);
        purchasedListAdapter = new PurchasedListAdapter(purchasedList);
        purchasedRecyclerView.setAdapter(purchasedListAdapter);
        purchasedListLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        // TODO: 18/05/2018 need to complete this listener
        Button finishBtn = findViewById(R.id.purchased_btn_finish);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator<ShoppingItem> itemIterator = addedList.iterator();
                while (itemIterator.hasNext()){
                    ShoppingItem shoppingItem = itemIterator.next();
                    shoppingItem.save();
                    MainActivity.shoppingItems.add(shoppingItem);
                    itemIterator.remove();
                }
                addedListAdapter.notifyDataSetChanged();
                MainActivity.itemDetailAdapter.notifyDataSetChanged();
                Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        /*
        * added List View
        *
        * */

        final RecyclerView addedRecyclerView = (RecyclerView) findViewById(R.id.added_list_recyclerView);
        LinearLayoutManager addedListLayoutManager = new LinearLayoutManager(this);
        addedRecyclerView.setLayoutManager(addedListLayoutManager);
        // TODO: 17/5/18 change the list to added list --> it should be a temp list
        addedListAdapter = new AddedListAdapter(addedList);
        addedRecyclerView.setAdapter(addedListAdapter);


    }

    // TODO: 18/05/2018 set edit paras, to link to AddListAdaptor, to allow editing for added list 
    public  void setEditPara(){

    }
}
