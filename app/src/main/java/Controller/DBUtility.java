package Controller;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

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

    // Test
    public static void insertExampleData(){
        //List1
        ShoppingList list1 = new ShoppingList();
        list1.setName("LiquidFood");
        ShoppingItem a = new ShoppingItem();
        a.setName("Milk");
        a.setQuantity(5);
        a.setImage("Image");
        a.setTag("DailyFood");
        a.setListId(1);
        a.setPrice(5);
        a.setPurchased(true);
        ShoppingItem b = new ShoppingItem();
        b.setName("Soy");
        b.setQuantity(5);
        b.setImage("Image");
        b.setTag("DailyFood");
        b.setListId(1);
        b.setPrice(3);
        b.setPurchased(true);
        ShoppingItem c = new ShoppingItem();
        c.setName("Coke");
        c.setQuantity(5);
        c.setImage("Image");
        c.setTag("DailyFood");
        c.setListId(1);
        c.setPrice(2);
        c.setPurchased(true);
        DBUtility.insert(list1);
        DBUtility.insert(a);
        DBUtility.insert(b);
        DBUtility.insert(c);
        //List2
        ShoppingList list2 = new ShoppingList();
        list2.setName("Fruit");
        ShoppingItem a1 = new ShoppingItem();
        a1.setName("Banana");
        a1.setQuantity(5);
        a1.setImage("Image");
        a1.setTag("I don't like like");
        a1.setListId(2);
        a1.setPrice(5);
        a1.setPurchased(true);
        ShoppingItem b1 = new ShoppingItem();
        b1.setName("Apple");
        b1.setQuantity(5);
        b1.setImage("Image");
        b1.setTag("I like");
        b1.setListId(2);
        b1.setPrice(3);
        b1.setPurchased(true);
        ShoppingItem c1 = new ShoppingItem();
        c1.setName("Kiwi");
        c1.setQuantity(5);
        c1.setImage("Image");
        c1.setTag("I like");
        c1.setListId(2);
        c1.setPrice(2);
        c1.setPurchased(true);
        DBUtility.insert(list2);
        DBUtility.insert(a1);
        DBUtility.insert(b1);
        DBUtility.insert(c1);
    }
}
