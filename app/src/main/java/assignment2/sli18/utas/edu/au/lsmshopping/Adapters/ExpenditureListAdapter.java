package assignment2.sli18.utas.edu.au.lsmshopping.Adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Entity.ShoppingItem;
import assignment2.sli18.utas.edu.au.lsmshopping.R;

public class ExpenditureListAdapter extends RecyclerView.Adapter<ExpenditureListAdapter.ViewHolder> {

    List<ShoppingItem> mShoppingItems;

    public ExpenditureListAdapter(List<ShoppingItem> list) {
        mShoppingItems = list;
    }

    @NonNull
    @Override
    public ExpenditureListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_expenditure_item, parent, false);
        //set Listener for radio purchased
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenditureListAdapter.ViewHolder holder, int position) {
        ShoppingItem shoppingItem = mShoppingItems.get(position);
        holder.expenditureName.setText(shoppingItem.getName());
        holder.expenditurePrice.setText(Double.toString(shoppingItem.getPrice()));
        holder.expenditureQuantity.setText(Integer.toString(shoppingItem.getQuantity()));
        holder.expenditureTotal.setText(Double.toString(shoppingItem.getPrice() * shoppingItem.getQuantity()));


    }

    @Override
    public int getItemCount() {
        return mShoppingItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView expenditureName;
        TextView expenditurePrice;
        TextView expenditureQuantity;
        TextView expenditureTotal;

        View itemView;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            expenditureName = view.findViewById(R.id.expenditure_list_name);
            expenditurePrice = view.findViewById(R.id.expenditure_list_price);
            expenditureQuantity = view.findViewById(R.id.expenditure_list_quantity);
            expenditureTotal = view.findViewById(R.id.expenditure_list_total);

        }
    }
    public void setmShoppingItems(List<ShoppingItem> list){
        mShoppingItems = list;
    }
}
