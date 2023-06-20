package model;

public class Prize extends Toy {
    int weight;

    public Prize(int id, String name, int quantity, int weight) {
        super(id, name, quantity);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("ID: %-3d NAME: %-10s QTY: %-5d WEIGHT: %d", super.getId(), super.getName(), super.getQuantity(), this.getWeight());
    }
}
