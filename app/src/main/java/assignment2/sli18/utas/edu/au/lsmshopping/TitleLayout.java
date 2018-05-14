package assignment2.sli18.utas.edu.au.lsmshopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TitleLayout extends LinearLayout {
    private static final String TAG = "TitleLayout";
    public TitleLayout(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        Button btnBack = findViewById(R.id.btn_title_back);
        Button btnList = findViewById(R.id.btn_title_list);
        Button btnAdd = findViewById(R.id.btn_title_entry);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity thisActivity = ((Activity) getContext());
                thisActivity.finish();
            }
        });

        btnList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((Activity) getContext()).getClass().getSimpleName().equals("MainActivity")){
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }

            }
        });

        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((Activity) getContext()).getClass().getSimpleName().equals("AddItemActivity")){
                    Intent intent = new Intent(context, AddItemActivity.class);
                    context.startActivity(intent);
                }
            }
        });


    }
}
