package assignment2.sli18.utas.edu.au.lsmshopping.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Entity.ShoppingItem;
import assignment2.sli18.utas.edu.au.lsmshopping.AddItemActivity;
import assignment2.sli18.utas.edu.au.lsmshopping.R;

public class AddedListAdapter extends RecyclerView.Adapter<AddedListAdapter.ViewHolder>{

    final private List<ShoppingItem> mShoppingLists;
    private Context context;

    public AddedListAdapter (List<ShoppingItem> shoppingItems, Context context){
        mShoppingLists = shoppingItems;
        this.context = context;
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

        //edit button
        holder.addedImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 17/5/18 edit
                int position = holder.getAdapterPosition();
                ShoppingItem shoppingItem = mShoppingLists.get(position);
                final EditText editName = context.findViewById(R.id.nameEdit);
                final EditText editTag = view.findViewById(R.id.tagEdit);
                final EditText editComment = view.findViewById(R.id.commentEdit);
                final EditText editPrice = view.findViewById(R.id.priceEdit);
                final EditText editQuantity = view.findViewById(R.id.quantityEdit);
                editName.setText(shoppingItem.getName());
                editTag.setText(shoppingItem.getTag());
                editComment.setText(shoppingItem.getCommend());
                editPrice.setText(Double.toString(shoppingItem.getPrice()));
                editQuantity.setText(Integer.toString(shoppingItem.getQuantity()));


            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddedListAdapter.ViewHolder holder, int position) {
        ShoppingItem shoppingItem = mShoppingLists.get(position);
        holder.addedName.setText(shoppingItem.getName());
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
