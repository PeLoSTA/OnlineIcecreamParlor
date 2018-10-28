package de.peterloos.petersicecreamparlor.adapters;

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

import de.peterloos.petersicecreamparlor.Globals;
import de.peterloos.petersicecreamparlor.interfaces.IItemClickListener;
import de.peterloos.petersicecreamparlor.models.OrderModel;
import de.peterloos.petersicecreamparlor.R;

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTextView;
    private IItemClickListener mClickListener;

    public ViewHolder(View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.tvPickupName);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (mClickListener != null) {
            mClickListener.onItemClick(view, this.getAdapterPosition());
        }
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setClickListener(IItemClickListener clickListener) {
        this.mClickListener = clickListener;
    }
}

public class RecyclerViewPickupNamesAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mInflater;

    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private List<String> mPickupNamesKeys;
    private List<String> mPickupNames;

    private IItemClickListener mClickListener;

    public RecyclerViewPickupNamesAdapter(final Context context, DatabaseReference ref) {

        mDatabaseReference = ref;
        mInflater = LayoutInflater.from(context);

        mPickupNamesKeys = new ArrayList<>();
        mPickupNames = new ArrayList<>();

        // create firebase child event listener
        mChildEventListener = new IceParlorChildEventListener(mPickupNames);
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // inflate row layout from xml when needed
        View view = mInflater.inflate(R.layout.recyclerview_row, parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.setClickListener(mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String name = this.mPickupNames.get(position);
        viewHolder.getTextView().setText(name);
    }

    @Override
    public int getItemCount() {
        return mPickupNames.size();
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mPickupNames.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(IItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    public void cleanupListener() {
        if (mClickListener != null) {
            mClickListener = null;
        }

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

            // a new orderModel has been added, add it to the displayed list
            OrderModel orderModel = dataSnapshot.getValue(OrderModel.class);
            String key = dataSnapshot.getKey();

            // insert new pickup name into list
            long pickupName = orderModel.getPickupName();
            mPickupNamesKeys.add(key);
            mPickupNames.add("PickupId: " + Long.toString(pickupName));
            notifyItemInserted(mPickupNames.size() - 1);
            Log.v(Globals.TAG, "onChildAdded: added pickname  " + orderModel.getPickupName());
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            Log.v(Globals.TAG, "onChildChanged");
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
                Log.v(Globals.TAG, "onChildRemoved: removed pickname at " + Integer.toString(index));
            } else {
                Log.w(Globals.TAG, "Internal Error: onChildRemoved: unknown_child = " + key);
            }
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            Log.v(Globals.TAG, "onChildRemoved");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

            Log.v(Globals.TAG, "onCancelled");
        }
    }
}
