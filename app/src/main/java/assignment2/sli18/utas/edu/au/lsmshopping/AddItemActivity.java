package assignment2.sli18.utas.edu.au.lsmshopping;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import Controller.DBUtility;
import Entity.ShoppingItem;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
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
        if (itemId != -1){
            ShoppingItem shoppingItem = DataSupport.find(ShoppingItem.class, itemId);
            editName.setText(shoppingItem.getName());
            editTag.setText(shoppingItem.getTag());
            editComment.setText(shoppingItem.getCommend());
            editPrice.setText(Double.toString(shoppingItem.getPrice()));
            editQuantity.setText(Integer.toString(shoppingItem.getQuantity()));
        }


        submitImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemId != -1){
                    ShoppingItem shoppingItem = DataSupport.find(ShoppingItem.class, itemId);
                    shoppingItem.setName(editName.getText().toString());
                    shoppingItem.setTag(editTag.getText().toString());
                    shoppingItem.setCommend(editComment.getText().toString());
                    shoppingItem.setPrice(Double.parseDouble(editPrice.getText().toString()));
                    shoppingItem.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
                    if (!shoppingItem.getName().equals("")){
                        shoppingItem.save();
                        MainActivity.itemDetailAdapter.notifyDataSetChanged();
                        Toast.makeText(AddItemActivity.this,"Edit Item Successfully!",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddItemActivity.this,"Editing Item failed! \n"
                                + "at least type a name!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Double price = !editPrice.getText().toString().equals("") ? Double.parseDouble(editPrice.getText().toString()) : 0;
                    Integer quantity = !editQuantity.getText().toString().equals("") ? Integer.parseInt(editQuantity.getText().toString()) : 0;
                    ShoppingItem shoppingItem = new ShoppingItem();
                    shoppingItem.setName(editName.getText().toString());
                    shoppingItem.setTag(editTag.getText().toString());
                    shoppingItem.setCommend(editComment.getText().toString());
                    shoppingItem.setPrice(price);
                    shoppingItem.setQuantity(quantity);
                    if (!shoppingItem.getName().equals("")){
                        shoppingItem.save();
                        MainActivity.itemDetailAdapter.notifyDataSetChanged();
                        Toast.makeText(AddItemActivity.this,"Add Item Successfully!",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddItemActivity.this,"Adding Item failed! \n"
                                + "at least type a name!",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}
