package de.peterloos.petersicecreamparlor.parcels;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import de.peterloos.petersicecreamparlor.Globals;

@SuppressWarnings("WeakerAccess")
public class OrderParcel implements Parcelable {

    // member data
    private String key;
    private long pickupId;
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
        this.setKey(pc.readString());
        this.setPickupId(pc.readLong());
        this.setScoops(pc.readInt());
        this.flavors = new String[this.getScoops()];
        pc.readStringArray(this.getFlavors());
        this.setContainer(pc.readString());
    }

    // user-defined c'tor
    public OrderParcel(String key, long pickupId, int scoops, String[] flavors, String container) {
        this.setKey(key);
        this.setPickupId(pickupId);
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
        parcel.writeString(this.getKey());
        parcel.writeLong(this.getPickupId());
        parcel.writeInt(this.getScoops());
        parcel.writeStringArray(this.getFlavors());
        parcel.writeString(this.getContainer());
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getPickupId() {
        return this.pickupId;
    }

    public void setPickupId(long pickupId) {
        this.pickupId = pickupId;
    }

    public int getScoops() {
        return this.scoops;
    }

    public void setScoops(int scoops) {
        this.scoops = scoops;
    }

    public String[] getFlavors() {
        return this.flavors;
    }

    public void setFlavors(String[] flavors) {
        this.flavors = flavors;
    }

    public String getContainer() {
        return this.container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    @Override
    public String toString() {
        return this.print();
    }

    @SuppressWarnings("unused")
    private String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("PickupId: %d", this.pickupId));
        sb.append(" - ");
        sb.append(String.format("Num Scoops: %d", this.scoops));
        sb.append(" - ");
        sb.append(String.format("Container:  %s", this.container));
        sb.append(" - ");

        if (this.flavors == null) {
            sb.append(String.format("flavors == null "));
            sb.append(" - ");
        } else {
            sb.append(String.format("Flavors:"));
            sb.append(" - ");
            for (int i = 0; i < this.flavors.length; i++) {
                sb.append(String.format("   %d: %s", (i + 1), this.flavors[i]));
            }
        }

        return sb.toString();
    }
}
