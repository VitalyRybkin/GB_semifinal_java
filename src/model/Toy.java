package model;

public class Toy {

    int id;
    String name;
    int quantity;

    public Toy(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d NAME: %-10s QTY: %d", this.getId(), this.getName(), this.getQuantity());
    }
}

