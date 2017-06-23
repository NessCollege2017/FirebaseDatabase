package ness.edu.firebasedatabase.models;

/**
 * A Shopping List title and owner.
 * POJO:
 */

public class ShoppingLists {
    private String ownerUID;
    private String listUID;
    private String name;

    //POJO constructor (Used for Firebase data retrieval.) snapshot.getValue(ShoppingList.class)
    public ShoppingLists() {
    }

    //Constructor * The constructor that we use to construct objects.
    public ShoppingLists(String ownerUID, String listUID, String name) {
        this.ownerUID = ownerUID;
        this.listUID = listUID;
        this.name = name;
    }

    //Getters and Setters:
    public String getOwnerUID() {
        return ownerUID;
    }
    public void setOwnerUID(String ownerUID) {
        this.ownerUID = ownerUID;
    }
    public String getListUID() {
        return listUID;
    }
    public void setListUID(String listUID) {
        this.listUID = listUID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ShoppingLists{" +
                "ownerUID='" + ownerUID + '\'' +
                ", listUID='" + listUID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
