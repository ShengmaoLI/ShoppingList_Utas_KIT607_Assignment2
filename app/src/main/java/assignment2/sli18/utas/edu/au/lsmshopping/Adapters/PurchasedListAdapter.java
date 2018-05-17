package assignment2.sli18.utas.edu.au.lsmshopping.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Entity.ShoppingItem;
import assignment2.sli18.utas.edu.au.lsmshopping.R;

public class PurchasedListAdapter extends RecyclerView.Adapter<PurchasedListAdapter.ViewHolder> {
    final private List<ShoppingItem> mShoppingItems;

    public PurchasedListAdapter(List<ShoppingItem> shoppingItems){
        mShoppingItems = shoppingItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_purchased_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.purchasedItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int postion = holder.getAdapterPosition();
               mShoppingItems.remove(postion);
               notifyItemRemoved(postion);
            }
        });
        holder.purchasedItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int postion = holder.getAdapterPosition();
                mShoppingItems.remove(postion);
                notifyItemRemoved(postion);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingItem shoppingItem = mShoppingItems.get(position);
        holder.purchasedItemText.setText(shoppingItem.getName());
    }

    @Override
    public int getItemCount() {
        return mShoppingItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView purchasedItemImg;
        TextView purchasedItemText;
        public ViewHolder(View view) {
            super(view);
            purchasedItemImg = view.findViewById(R.id.purchased_list_item_img);
            purchasedItemText = view.findViewById(R.id.purchased_list_item_name);

        }
    }

}
