package de.peterloos.petersicecreamparlor.parcels;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Locale;


@SuppressWarnings("WeakerAccess")
public class OrderParcel implements Parcelable {

    // member data
    private String key;
    private long timeStamp;
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
        this.setTimeStamp(pc.readLong());
        this.setPickupId(pc.readLong());
        this.setScoops(pc.readInt());
        this.flavors = new String[this.getScoops()];
        pc.readStringArray(this.getFlavors());
        this.setContainer(pc.readString());
    }

    // user-defined c'tor
    public OrderParcel(String key, long timeStamp, long pickupId, int scoops, String[] flavors, String container) {
        this.setKey(key);
        this.setTimeStamp(timeStamp);
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
        parcel.writeLong(this.getTimeStamp());
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

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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
    @NonNull
    public String toString() {
        return this.print();
    }

    @SuppressWarnings("unused")
    private String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.getDefault(),"PickupId: %d", this.pickupId));
        sb.append(" - ");
        sb.append(String.format(Locale.getDefault(),"TimeStamp: %d", this.timeStamp));
        sb.append(" - ");
        sb.append(String.format(Locale.getDefault(),"Num Scoops: %d", this.scoops));
        sb.append(" - ");
        sb.append(String.format(Locale.getDefault(),"Container: %s", this.container));
        sb.append(" - ");

        if (this.flavors == null) {
            sb.append("flavors == null ");
            sb.append(" - ");
        } else {
            sb.append("Flavors:");
            sb.append(" - ");
            for (int i = 0; i < this.flavors.length; i++) {
                sb.append(String.format(Locale.getDefault(),"   %d: %s", (i + 1), this.flavors[i]));
            }
        }

        return sb.toString();
    }
}
