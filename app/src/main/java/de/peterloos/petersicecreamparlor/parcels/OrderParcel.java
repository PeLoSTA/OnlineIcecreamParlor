package de.peterloos.petersicecreamparlor.parcels;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderParcel implements Parcelable {

    // member data
    private int pickupId;
    private int scoops;
    private String[] flavors;
    private String container;

    // static field used to regenerate object, individually or as array
    public static final Parcelable.Creator<OrderParcel> CREATOR =
            new Parcelable.Creator<OrderParcel>() {
                public OrderParcel createFromParcel(Parcel pc) {
                    return new OrderParcel(pc);
                }

                public OrderParcel[] newArray(int size) {
                    return new OrderParcel[size];
                }
            };

    // system-defined c'tor from Parcel, reads back fields IN THE ORDER they were written
    public OrderParcel(Parcel pc) {
        this.setPickupId(pc.readInt());
        this.setScoops(pc.readInt());
        pc.readStringArray(this.getFlavors());
        this.setContainer(pc.readString());
    }

    // user-defined c'tor
    public OrderParcel(String pickupId, int scoops,String[] flavors, String container) {

        // this.setPickupId(pickupId);
        this.setPickupId(123);
        this.setScoops(scoops);
        this.setFlavors(flavors);
        this.setContainer(container);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeInt(this.getPickupId());
        parcel.writeInt(this.getScoops());
        parcel.writeStringArray(this.getFlavors());
        parcel.writeString(this.getContainer());
    }

    public int getPickupId() {
        return pickupId;
    }

    public void setPickupId(int pickupId) {
        this.pickupId = pickupId;
    }

    public int getScoops() {
        return scoops;
    }

    public void setScoops(int scoops) {
        this.scoops = scoops;
    }

    public String[] getFlavors() {
        return flavors;
    }

    public void setFlavors(String[] flavors) {
        this.flavors = flavors;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }
}
