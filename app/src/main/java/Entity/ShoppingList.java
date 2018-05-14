package Entity;

import org.litepal.crud.DataSupport;

public class ShoppingList extends DataSupport{
    private int id;
    private String name = "";
    private boolean currentList = false;

    public int getId() {
        return id;
    }

    public boolean isCurrentList() {
        return currentList;
    }

    public void setCurrentList(boolean currentList) {
        this.currentList = currentList;
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
}
