package assignment2.sli18.utas.edu.au.lsmshopping;

import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

import assignment2.sli18.utas.edu.au.lsmshopping.Controller.DBUtility;
import assignment2.sli18.utas.edu.au.lsmshopping.Entity.ShoppingItem;
import assignment2.sli18.utas.edu.au.lsmshopping.Entity.ShoppingList;

public class MainActivity extends AppCompatActivity {

   //---
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationView n1 = (NavigationView) findViewById(R.id.navigation_view);
                n1.setVisibility(View.GONE);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater(R.menu.title_symbol,menu);
//                return true;
//    }

    public static void showData(Class table) throws Exception{
        //show data
        if (table == ShoppingList.class){
            List<ShoppingList> list = DBUtility.selectAllShoppingList();

            for (ShoppingList sl : list){
                Log.d(TAG, "---------华丽的分割线----------");
                Log.d(TAG, "test : " + sl.getId());
                Log.d(TAG, "test : " + sl.getName());
            }
        }else if(table == ShoppingItem.class){
            List<ShoppingItem> list = DBUtility.selectAllItemsByListID(1);
            list.addAll(DBUtility.selectAllItemsByListID(2));
            for (ShoppingItem sl : list){
                Log.d(TAG, "---------华丽的分割线----------");
                Log.d(TAG, "test : " + sl.getId());
                Log.d(TAG, "test : " + sl.getName());
                Log.d(TAG, "test : " + sl.getQuantity());
                Log.d(TAG, "test : " + sl.getImage());
                Log.d(TAG, "test : " + sl.getTag());
                Log.d(TAG, "test : " + sl.getCommend());
                Log.d(TAG, "test : " + sl.getListId() + "ListID");
                Log.d(TAG, "test : " + sl.getPrice());
                Log.d(TAG, "test : " + sl.getDate());
                Log.d(TAG, "test : " + sl.isPurchased());
            }
        }else {
            throw new Exception("请给我一个我认识的朋友");
        }

    }
}
