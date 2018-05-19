package assignment2.sli18.utas.edu.au.lsmshopping.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Entity.ShoppingItem;
import assignment2.sli18.utas.edu.au.lsmshopping.AddItemActivity;
import assignment2.sli18.utas.edu.au.lsmshopping.R;

public class AddedListAdapter extends RecyclerView.Adapter<AddedListAdapter.ViewHolder>{

    final private List<ShoppingItem> mShoppingLists;
    private Activity addedListActivity;
    private Context addedListContext;


    public AddedListAdapter (List<ShoppingItem> shoppingItems, Context context){
        mShoppingLists = shoppingItems;
        addedListActivity = (Activity) context;
        addedListContext = context;

    }

    @NonNull
    @Override
    public AddedListAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_added_item_detail, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //delete button
        holder.addedImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ShoppingItem shoppingItem = mShoppingLists.get(position);
                AddItemActivity.addedList.remove(shoppingItem);
                AddItemActivity.addedListAdapter.notifyDataSetChanged();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddedListAdapter.ViewHolder holder, int position) {
        ShoppingItem shoppingItem = mShoppingLists.get(position);
        holder.addedName.setText(shoppingItem.getName());
        AddItemActivity.disPlayImage( shoppingItem.getImage(), holder.addedImg);
    }

    @Override
    public int getItemCount() {
        return mShoppingLists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView addedImg;
        TextView addedName;
        ImageButton addedImgDelete;
        ImageButton addedImgEdit;
        public ViewHolder(View itemView) {
            super(itemView);
            addedImg = itemView.findViewById(R.id.added_item_detail_imgView);
            addedName = itemView.findViewById(R.id.added_item_detail_name);
            addedImgDelete = itemView.findViewById(R.id.addList_img_btn_delete);
            addedImgEdit = itemView.findViewById(R.id.addList_img_btn_edit);
        }
    }
}
