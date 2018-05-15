package assignment2.sli18.utas.edu.au.lsmshopping.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import Entity.ShoppingItem;
import assignment2.sli18.utas.edu.au.lsmshopping.AddItemActivity;
import assignment2.sli18.utas.edu.au.lsmshopping.R;

public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.ViewHolder> {
    private List<ShoppingItem> mShoppingItems;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;
        TextView itemComment;
        TextView itemQuantity;
        TextView itemTag;
        ImageButton imgBtnDelete;
        ImageButton imgBtnEdit;
        RadioButton radioBtnPurchased;
        View itemView;
        public ViewHolder(View view){
            super(view);
            itemView = view;
            itemImage = (ImageView) view.findViewById(R.id.item_image);
            itemName = (TextView) view.findViewById(R.id.item_name);
            itemPrice = (TextView) view.findViewById(R.id.item_price);
            itemComment = (TextView) view.findViewById(R.id.item_comment);
            itemQuantity = (TextView) view.findViewById(R.id.item_quantity);
            itemTag = (TextView) view.findViewById(R.id.item_tag);
            imgBtnDelete = (ImageButton) view.findViewById(R.id.img_btn_delete);
            imgBtnEdit = (ImageButton) view.findViewById(R.id.img_btn_edit);
            radioBtnPurchased = (RadioButton) view.findViewById(R.id.item_purchased_radio);


        }
    }
    public ItemDetailAdapter(List<ShoppingItem> shoppingItems){
        mShoppingItems = shoppingItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_detail,parent,false);

        //set Listener for radio purchased
        final ViewHolder holder = new ViewHolder(view);
        holder.radioBtnPurchased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ShoppingItem shoppingItem = mShoppingItems.get(position);
                if ( !shoppingItem.isPurchased() ){
                    shoppingItem.setPurchased(true);
                    holder.radioBtnPurchased.setChecked(true);
                }else {
                    shoppingItem.setPurchased(false);
                    holder.radioBtnPurchased.setChecked(false);
                }
            }
        });

        //set Listener for Edit Button
        holder.imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ShoppingItem shoppingItem = mShoppingItems.get(position);
                Intent intent = new Intent(v.getContext(), AddItemActivity.class);
                intent.putExtra("itemId",shoppingItem.getId());
                v.getContext().startActivity(intent);
            }
        });

        //set Listener for Delete Button
        // TODO: 14/05/2018 Need to make confirmed alteration
        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ShoppingItem shoppingItem = mShoppingItems.get(position);
                Toast.makeText(v.getContext(),"you click to delete item named: " + shoppingItem.getName()
                        + "-------" + position +"--------",Toast.LENGTH_SHORT).show();
                DataSupport.delete(ShoppingItem.class, shoppingItem.getId());
                mShoppingItems.remove(position);
                //remove
                notifyItemRemoved(position);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingItem shoppingItem = mShoppingItems.get(position);
        holder.itemImage.setImageResource(R.drawable.ic_launcher_background);
        holder.itemName.setText(shoppingItem.getName());
        holder.itemTag.setText(shoppingItem.getTag());
        holder.itemQuantity.setText(new Integer(shoppingItem.getQuantity()).toString());
        holder.itemComment.setText(shoppingItem.getCommend());
        holder.itemPrice.setText(new Double(shoppingItem.getPrice()).toString());
        holder.radioBtnPurchased.setChecked(shoppingItem.isPurchased());
    }

    @Override
    public int getItemCount() {
        return mShoppingItems.size();
    }
}
