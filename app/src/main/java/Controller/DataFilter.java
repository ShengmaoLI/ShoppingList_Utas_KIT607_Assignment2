package Controller;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import Entity.ShoppingItem;

/**
 * Created by simon on 9/05/2018.
 */

public class DataFilter {

    private DataFilter(){}


    //copy a new ShoppingItem and set it to unpurchased
    public static ShoppingItem copyShoppingItem(ShoppingItem s){
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setName(s.getName());
        shoppingItem.setPrice(s.getPrice());
        shoppingItem.setCommend(s.getCommend());
        shoppingItem.setTag(s.getTag());
        shoppingItem.setQuantity(s.getQuantity());
        shoppingItem.setImage(s.getImage());
        return shoppingItem;
    }
    //initialize data (purchased items and sort by date)
    public static List<ShoppingItem> getPurchasedData(){
        List<ShoppingItem> tempList = DataSupport.findAll(ShoppingItem.class);
        Iterator<ShoppingItem> itemIterator = tempList.iterator();
        while (itemIterator.hasNext()){
            ShoppingItem shoppingItem = itemIterator.next();
            if (!shoppingItem.isPurchased()){
                itemIterator.remove();
            }
        }
        Collections.sort(tempList, new Comparator<ShoppingItem>() {
            @Override
            public int compare(ShoppingItem o1, ShoppingItem o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return tempList;
    }
    public static List<ShoppingItem> getUnpurchasedData() {
        List<ShoppingItem> list = new ArrayList<>();
        for (ShoppingItem s: DataSupport.findAll(ShoppingItem.class)){
            if (!s.isPurchased()){
                list.add(s);
            }
        }
        return list;
    }
    // TODO: 9/05/2018  计算总价
    public static double totalPrice(List<ShoppingItem> shoppingItemList){
        double totalPrice = 0;
        for (ShoppingItem shoppingItem : shoppingItemList){
            totalPrice += shoppingItem.getPrice() * shoppingItem.getQuantity();
        }
        return totalPrice;
    }

    // TODO: 9/05/2018  根据日期
    public static List<ShoppingItem> itemFilterByDate(List<ShoppingItem> list,Date start, Date end){
        List<ShoppingItem> l = new ArrayList<>();
        Date startDate = new Date(2000, 0, 1);
        Date endDate = new Date(2050, 11, 29);
        if (start != null){
            startDate = start;
        }
        if (end != null){
            endDate = end;
        }
        for (ShoppingItem shoppingItem : list){
            if (shoppingItem.getDate().after(startDate) && shoppingItem.getDate().before(endDate)) {
                l.add(shoppingItem);
            }
        }
        return l;
    }

    // TODO: 9/05/2018  根据Tag筛选
    public static List<ShoppingItem> itemFilterByTag(List<ShoppingItem> list, String ... tags){
        if (tags != null && tags.length > 0){

        }
        return null;
    }
    //
}
