package de.peterloos.petersicecreamparlor;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Order {

    private String container;
    private List<String> flavors;
    private String pickupName;
    private int scoops;

    // mandatory: default constructor that takes no arguments
    public Order() {
    }

    @Override
    public String toString() {
        return this.print();
    }

    @SuppressWarnings("unused")
    private String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("PickupName: %s", this.pickupName));
        sb.append(" - ");
        sb.append(String.format("Num Scoops: %d", this.scoops));
        sb.append(" - ");
        sb.append(String.format("Container:  %s", this.container));
        sb.append(" - ");

        if (this.flavors == null) {
            sb.append(String.format("flavors == null "));
            sb.append(" - ");
        }
        else {
            sb.append(String.format("Flavors:"));
            sb.append(" - ");

            for (int i = 0; i < this.flavors.size(); i++) {
                sb.append(String.format("   %d: %s", (i+1), this.flavors.get(i)));
                sb.append(System.getProperty("line.separator"));
            }
        }

        return sb.toString();
    }

    @SuppressWarnings("unused")
    public String getContainer() {
        return container;
    }

    @SuppressWarnings("unused")
    public void setContainer(String container) {
        this.container = container;
    }

    @SuppressWarnings("unused")
    public List<String> getFlavors() {
        return flavors;
    }

    @SuppressWarnings("unused")
    public void setFlavors(List<String> flavors) {
        this.flavors = flavors;
    }

    @SuppressWarnings("unused")
    public String getPickupName() {
        return pickupName;
    }

    @SuppressWarnings("unused")
    public void setPickupName(String pickupName) {
        this.pickupName = pickupName;
    }

    @SuppressWarnings("unused")
    public int getScoops() {
        return scoops;
    }

    @SuppressWarnings("unused")
    public void setScoops(int scoops) {
        this.scoops = scoops;
    }
}
