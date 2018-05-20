package assignment2.sli18.utas.edu.au.lsmshopping;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Controller.DataFilter;
import Entity.ShoppingItem;
import assignment2.sli18.utas.edu.au.lsmshopping.Adapters.ExpenditureListAdapter;

public class StatisticsActivity extends AppCompatActivity {


    static ExpenditureListAdapter expenditureListAdapter;
    static List<ShoppingItem> shoppingItems = DataFilter.getPurchasedData();
    Date start = new Date(2018-1900,1,1);
    Date end = new Date(2018-1900,9,9);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        final TextView searchEdit = findViewById(R.id.searchEdit);
        final RecyclerView expenditureRec = findViewById(R.id.expenditure_recycler);
        final Button startBtn = findViewById(R.id.start_btn);
        Button endBtn = findViewById(R.id.end_btn);
        final TextView startDate = findViewById(R.id.start_textView);
        final TextView endDate = findViewById(R.id.end_textView);
        final TextView totalPriceView = findViewById(R.id.total_price);
        Double totalPrice = 0.0;
        if (shoppingItems != null) {
            for (ShoppingItem s : shoppingItems) {
                totalPrice += s.getPrice() * s.getQuantity();
            }
        }
        totalPriceView.setText(totalPrice.toString());

        //initialize recycler view
        expenditureListAdapter = new ExpenditureListAdapter(shoppingItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        expenditureRec.setLayoutManager(layoutManager);
        expenditureRec.setAdapter(expenditureListAdapter);
        //start date
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(StatisticsActivity.this, new DatePickerDialog.
                        OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        start = new Date(year-1900,monthOfYear,dayOfMonth);
                        startDate.setText(year+"/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, 2018, 1, 1);
                datePicker.show();
                List<ShoppingItem> tempList = StatisticsActivity.statisticsFilter(searchEdit.getText().toString(), start
                        , end, shoppingItems);
                Double totalPrice = 0.0;
                if (tempList != null) {
                    for (ShoppingItem s : tempList) {
                        totalPrice += s.getPrice() * s.getQuantity();
                    }
                }
                totalPriceView.setText(totalPrice.toString());
            }
        });

        //end date
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(StatisticsActivity.this, new DatePickerDialog.
                        OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        String date = year + "/ " + (monthOfYear + 1) + "/ "
                                + dayOfMonth
                                + "/";
                        end = new Date(year-1900,monthOfYear,dayOfMonth);
                        endDate.setText(year+"/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, 2018, 9, 9);
                datePicker.show();
                List<ShoppingItem> tempList = StatisticsActivity.statisticsFilter(searchEdit.getText().toString(), start
                        , end, shoppingItems);
                Double totalPrice = 0.0;
                if (tempList != null) {
                    for (ShoppingItem s : tempList) {
                        totalPrice += s.getPrice() * s.getQuantity();
                    }
                }
                totalPriceView.setText(totalPrice.toString());
            }
        });

        //set Listener and logic for "Share" Button
        final Button shaBtn = findViewById(R.id.main_share);
        shaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shoppingList = "Shopping List\n";
                for (ShoppingItem shoppingItem : MainActivity.shoppingItems) {
                    shoppingList += "---" + shoppingItem.getName() + "---\n";
                    shoppingList += "price: " + shoppingItem.getPrice() + "$\n";
                    shoppingList += "quantity: x" + shoppingItem.getQuantity() + "\n";
                }
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "0416177711"));
                intent.putExtra("sms_body", shoppingList);
                startActivity(intent);
            }
        });

        Button finishBtn = findViewById(R.id.btn_finish);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
                StatisticsActivity.this.startActivity(intent);
            }
        });
        Button searchBtn = findViewById(R.id.btn_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 20/05/2018 delete later
                List<ShoppingItem> tempList = StatisticsActivity.statisticsFilter(searchEdit.getText().toString(), start
                        , end, shoppingItems);
                Double totalPrice = 0.0;
                if (tempList != null) {
                    for (ShoppingItem s : tempList) {
                        totalPrice += s.getPrice() * s.getQuantity();
                    }
                }
                totalPriceView.setText(totalPrice.toString());
            }
        });
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    private static List<ShoppingItem> statisticsFilter(String searchTerms, Date start, Date end, List<ShoppingItem> list) {
        shoppingItems = DataFilter.getPurchasedData();
        List<ShoppingItem> tempList = new ArrayList<>();
            for (ShoppingItem s : list) {
                if (s.getName().contains(searchTerms) || s.getTag().contains(searchTerms)) {
                    Log.d("Test", "statisticsFilter: " + "157" +
                            "\n" + start.toString() +
                            "\n" + end.toString() +
                            "\n" + s.getDate().toString());// TODO: 20/05/2018 delete later
                    if (s.getDate().after(start) && s.getDate().before(end)) {
                        tempList.add(s);
                    }
                }
            }
            if (!tempList.isEmpty()){
                expenditureListAdapter.setmShoppingItems(tempList);
                expenditureListAdapter.notifyDataSetChanged();
                return tempList;
            }else {
                expenditureListAdapter.setmShoppingItems(shoppingItems);
                expenditureListAdapter.notifyDataSetChanged();
                return shoppingItems;
            }
    }
}
