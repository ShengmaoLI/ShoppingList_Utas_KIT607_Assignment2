package assignment2.sli18.utas.edu.au.lsmshopping;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.ImageView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import Controller.DataFilter;
import Entity.ShoppingItem;
import assignment2.sli18.utas.edu.au.lsmshopping.Adapters.AddedListAdapter;
import assignment2.sli18.utas.edu.au.lsmshopping.Adapters.PurchasedListAdapter;

public class AddItemActivity extends AppCompatActivity {


    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;
    private String shoppingItemImagePath;
    private ImageView itemImageAdd;
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
        itemImageAdd = findViewById(R.id.item_img_add);
        ImageButton takePhoto = findViewById(R.id.imgEdit_take_photo);
        ImageButton chooseGallery = findViewById(R.id.imgEdit_choose_gallery);
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
                    shoppingItem.setImage(shoppingItemImagePath);

                    if (!shoppingItem.getName().equals("")) {
                        shoppingItem.save();
                        MainActivity.shoppingItems.clear();
                        MainActivity.shoppingItems.addAll(DataFilter.getUnpurchasedData());
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
                    shoppingItem.setImage(shoppingItemImagePath);
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
                if (itemId != -1) {
                    ShoppingItem shoppingItem = DataSupport.find(ShoppingItem.class, itemId);
                    shoppingItem.setName(editName.getText().toString());
                    shoppingItem.setTag(editTag.getText().toString());
                    shoppingItem.setCommend(editComment.getText().toString());
                    shoppingItem.setPrice(Double.parseDouble(editPrice.getText().toString()));
                    shoppingItem.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
                    shoppingItem.setImage(shoppingItemImagePath);
                    Log.d(TAG, "onClick: " + shoppingItemImagePath);

                    if (!shoppingItem.getName().equals("")) {
                        shoppingItem.save();
                        MainActivity.shoppingItems.clear();
                        MainActivity.shoppingItems.addAll(DataFilter.getUnpurchasedData());
                        MainActivity.itemDetailAdapter.notifyDataSetChanged();
                        Toast.makeText(AddItemActivity.this, "Edit Item Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddItemActivity.this, "Editing Item failed! \n"
                                + "at least type a name!", Toast.LENGTH_SHORT).show();
                    }

                }
                Iterator<ShoppingItem> itemIterator = addedList.iterator();
                while (itemIterator.hasNext()) {
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
        addedListAdapter = new AddedListAdapter(addedList, AddItemActivity.this);
        addedRecyclerView.setAdapter(addedListAdapter);

        // TODO: 19/05/2018 I'd doing here
        //take photo Listener --> to edit image
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(getExternalCacheDir(),
                        "output_image"+ new Date().getTime() +".jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(AddItemActivity.this,
                            "com.example.cameraablumtest.fileprovider",
                            outputImage);
                    shoppingItemImagePath = getImagePath(imageUri,null);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                    shoppingItemImagePath = getImagePath(imageUri,null);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        //choose gallery Listener --> edit image

        chooseGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddItemActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddItemActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    openAlbum();
                }
            }
        });

    }

    public void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));
                        itemImageAdd.setImageURI(imageUri);

                        String t = getImagePath(imageUri,null);
                        Log.d(TAG, "onActivityResult: " + imageUri);
                        //Log.d(TAG, "onActivityResult: " + Uri.parse(t));
                        //itemImageAdd.setImageURI(Uri.parse(t));
                        //itemImageAdd.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        selection);
                shoppingItemImagePath = imagePath;
            } else if ("com.android.providers.downloads.documents".equals(uri
                    .getAuthority())) {
                Uri contentUri = ContentUris
                        .withAppendedId(Uri.parse("content://downloads/public_downloads"),
                                Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
                shoppingItemImagePath = imagePath;
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
            shoppingItemImagePath = imagePath;

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
            shoppingItemImagePath = imagePath;

        }
        disPlayImage(imagePath, itemImageAdd);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imageUri = data.getData();
        String imagePath = getImagePath(uri, null);
        shoppingItemImagePath = imagePath;
        disPlayImage(imagePath, itemImageAdd);
    }

    public String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images
                        .Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public static void disPlayImage(String imagePath, ImageView imageView) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        } else {
            Log.d("Image", "disPlayImage: " + "failed to get image");
        }
    }


}
