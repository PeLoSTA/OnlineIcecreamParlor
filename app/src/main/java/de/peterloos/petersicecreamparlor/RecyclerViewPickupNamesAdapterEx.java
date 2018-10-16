package de.peterloos.petersicecreamparlor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

class ViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public ViewHolder(View itemView) {
        super(itemView);

        this.textView = itemView.findViewById(R.id.tvPickupName);
    }
}

public class RecyclerViewPickupNamesAdapterEx extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = "PeLo_Recycler";

    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private List<String> mPickupNamesKeys = new ArrayList<>();
    private List<String> mPickupNames;

    public RecyclerViewPickupNamesAdapterEx(final Context context, DatabaseReference ref) {
        mContext = context;
        mDatabaseReference = ref;

        mPickupNamesKeys = new ArrayList<>();
        mPickupNames = new ArrayList<>();

        // create child event listener
        mChildEventListener = new IceParlorChildEventListener(mPickupNames);
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // inflates row layout from xml when needed
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recyclerview_row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String name = this.mPickupNames.get(position);
        viewHolder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mPickupNames.size();
    }

    public void cleanupListener() {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
        }
    }

    private class IceParlorChildEventListener implements ChildEventListener {

        private List<String> mPickupNames;

        public IceParlorChildEventListener (List<String> pickupNames) {
            mPickupNames = pickupNames;
        }

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            // a new order has been added, add it to the displayed list
            Order order = dataSnapshot.getValue(Order.class);
            String key = dataSnapshot.getKey();

            // insert new pickup name into list
            String pickupName = order.getPickupName();
            mPickupNamesKeys.add(key);
            mPickupNames.add(pickupName);
            notifyItemInserted(mPickupNames.size() - 1);
            Log.v(TAG, "onChildAdded: added pickname  " + order.getPickupName());
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            Log.v(TAG, "onChildChanged");
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            // a pickup name has been redeemed, use the key to determine
            // if we are displaying this pickup name and if so remove it
            String key = dataSnapshot.getKey();

            int index = mPickupNamesKeys.indexOf(key);
            if (index > -1) {
                // Remove data from the list
                mPickupNamesKeys.remove(index);
                mPickupNames.remove(index);

                // update the recycler view
                notifyItemRemoved(index);
                Log.v(TAG, "onChildRemoved: removed pickname at " + Integer.toString(index));
            } else {
                Log.w(TAG, "Internal Error: onChildRemoved: unknown_child = " + key);
            }
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            Log.v(TAG, "onChildRemoved");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

            Log.v(TAG, "onCancelled");
        }
    }
}
