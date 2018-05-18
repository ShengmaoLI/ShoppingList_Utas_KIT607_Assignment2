package Entity;

import android.support.annotation.NonNull;

import org.litepal.crud.DataSupport;

import java.util.Comparator;
import java.util.Date;

public class ShoppingItem extends DataSupport{
    private int id;
    private String name = "";
    private int quantity = 0;
    private String image = "";
    private String tag = "";
    private String commend = "";
    private int listId;
    private double price = 0;
    private Date date = new Date();
    private boolean purchased = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name : "";
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity >= 0 ? quantity : 0;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image != null ? image : "";
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag != null ? tag : "";
    }

    public String getCommend() {
        return commend;
    }

    public void setCommend(String commend) {
        this.commend = commend != null ? commend : "";
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price >= 0 ? price : 0;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date != null ? date : new Date();
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
