package org.example;

public class Group extends Entity {
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Group(int id, String name, int quantity) {
        super(id, name);
        this.quantity = quantity;
    }
}
