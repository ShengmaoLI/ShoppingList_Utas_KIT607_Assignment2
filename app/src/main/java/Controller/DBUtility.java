package Controller;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import Entity.ShoppingItem;
import Entity.ShoppingList;

/**
 * Created by simon on 9/05/2018.
 */

public class DBUtility {

    private DBUtility(){}

    // TODO: 9/05/2018 创建数据库 -> Create Database
    public static void createDatabase(){
        Connector.getDatabase();
    }

    // TODO: 9/05/2018  增加
   public static <T extends DataSupport> void insert( T item ){
       item.save();
   }

    // TODO: 9/05/2018  删除
    public static void delete(Class table, int itemIdPara){
       Integer itemID = itemIdPara;
       DataSupport.deleteAll(table, "id = ?", itemID.toString());
    }

    // TODO: 9/05/2018  改
    public static <T extends DataSupport> void update( T item, int currentId ){
        Integer id = currentId;
        item.updateAll("id = ?", id.toString());
    }

    // TODO: 9/05/2018  查一条
    @SuppressWarnings("unchecked")
    public static <T extends DataSupport> T selectOneRow(Class i, int currentID) throws Exception{
        if (i == ShoppingItem.class || i == ShoppingList.class){
             return (T) DataSupport.find(i,currentID);
        }else {
            throw new Exception("Unknown Class Type in method selectOneRow");
        }
    }

    // TODO: 9/05/2018  Select all ShoppingItem according to ListID
    public static List<ShoppingItem> selectAllItemsByListID(int currentListId){
        Integer listId = currentListId;
        return DataSupport.select("*").where("listId = ? ", listId.toString()).find(ShoppingItem.class);
    }

    // TODO: 9/05/2018 查全部List
    public static List<ShoppingList> selectAllShoppingList(){
        return DataSupport.findAll(ShoppingList.class);
    }

    public static void sizeCompress(Bitmap bmp, File file) {
        // 尺寸压缩倍数,值越大，图片尺寸越小
        int ratio = 8;
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bmp.getWidth() / ratio, bmp.getHeight() / ratio);
        canvas.drawBitmap(bmp, null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
