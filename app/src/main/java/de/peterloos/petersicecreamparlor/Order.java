package de.peterloos.petersicecreamparlor;

import android.support.annotation.NonNull;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Order {

    private String container;
    private List<String> flavors;
    private long pickupName;
    private int scoops;

    // mandatory: default constructor that takes no arguments
    public Order() {
        this.container = "";
        this.flavors = new ArrayList<>();
        this.pickupName = 0;
        this.scoops = 0;
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
        return this.container;
    }

    @SuppressWarnings("unused")
    public void setContainer(String container) {
        this.container = container;
    }

    @SuppressWarnings("unused")
    public List<String> getFlavors() {
        return this.flavors;
    }

    @SuppressWarnings("unused")
    public void setFlavors(List<String> flavors) {
        this.flavors = flavors;
    }

    @NonNull
    public long getPickupName() {
        return this.pickupName;
    }

    @SuppressWarnings("unused")
    public void setPickupName(long pickupName) {
        this.pickupName = pickupName;
    }

    @SuppressWarnings("unused")
    public int getScoops() {
        return this.scoops;
    }

    @SuppressWarnings("unused")
    public void setScoops(int scoops) {
        this.scoops = scoops;
    }
}
