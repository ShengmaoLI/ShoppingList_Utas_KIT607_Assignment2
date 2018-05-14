package Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Entity.ShoppingItem;

/**
 * Created by simon on 9/05/2018.
 */

public class DataFilter {

    private DataFilter(){}

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
