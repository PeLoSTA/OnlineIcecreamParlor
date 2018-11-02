package de.peterloos.petersicecreamparlor.models;

import android.support.annotation.NonNull;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@IgnoreExtraProperties
public class OrderModel {

    private String key;
    private String container;
    private List<String> flavors;
    private long pickupName;
    private int scoops;

    // mandatory: default constructor that takes no arguments
    public OrderModel() {
        this.key = "";
        this.container = "";
        this.flavors = new ArrayList<>();
        this.pickupName = 0;
        this.scoops = 0;
    }

    @Override
    @NonNull
    public String toString() {
        return this.print();
    }

    @SuppressWarnings("unused")
    public String getKey() {
        return this.key;
    }

    @SuppressWarnings("unused")
    public void setKey(String key) {
        this.key = key;
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
    public String[] getFlavorsArray() {
        String tmp[] = new String[this.flavors.size()];
        return this.flavors.toArray(tmp);
    }

    @SuppressWarnings("unused")
    public void setFlavors(List<String> flavors) {
        this.flavors = flavors;
    }

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

    private String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Key: %s", this.key));
        sb.append(" - ");
        sb.append(String.format(Locale.getDefault(),"PickupName: %d", this.pickupName));
        sb.append(" - ");
        sb.append(String.format(Locale.getDefault(),"Num Scoops: %d", this.scoops));
        sb.append(" - ");
        sb.append(String.format("Container:  %s", this.container));
        sb.append(" - ");

        if (this.flavors == null) {
            sb.append("flavors == null ");
            sb.append(" - ");
        }
        else {

            sb.append("Flavors: ");
            sb.append(" - ");

            for (int i = 0; i < this.flavors.size(); i++) {
                sb.append(String.format(Locale.getDefault(),"   %d: %s", (i+1), this.flavors.get(i)));
                sb.append(System.getProperty("line.separator"));
            }
        }

        return sb.toString();
    }
}
