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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.peterloos.petersicecreamparlor.Globals;
import de.peterloos.petersicecreamparlor.interfaces.MyItemClickListener;
import de.peterloos.petersicecreamparlor.models.OrderModel;
import de.peterloos.petersicecreamparlor.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private List<OrderModel> orderModels;
    private List<String> keys;

    private DatabaseReference ordersRef;
    private ChildEventListener childEventListener;

    private MyItemClickListener itemClickListener;

    public RecyclerViewAdapter(final Context context) {

        this.context = context;
        this.inflater = LayoutInflater.from(this.context);

        this.orderModels = new ArrayList<>();
        this.keys = new ArrayList<>();

        // initialize database
        this.ordersRef = FirebaseDatabase.getInstance().getReference().child("orders");

        // create firebase child event listener
        this.childEventListener = new IceParlorChildEventListener();
        this.ordersRef.addChildEventListener(this.childEventListener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        // inflate row layout from xml when needed
        View view = inflater.inflate(R.layout.recyclerview_row, parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.setClickListener(this.itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        OrderModel order = this.orderModels.get(position);

        String sId = this.context.getResources().getString(R.string.pickup_id);
        String sRow = String.format(Locale.getDefault(), "%s: %s",
                sId, Long.toString(order.getPickupName()));
        viewHolder.getTextView().setText(sRow);
    }

    @Override
    public int getItemCount() {
        return this.orderModels.size();
    }

    // convenience methods for getting data at click position
    public OrderModel getOrder(int id) {
        return this.orderModels.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void cleanupListener() {
        if (this.itemClickListener != null) {
            this.itemClickListener = null;
        }

        if (this.childEventListener != null) {
            this.ordersRef.removeEventListener(this.childEventListener);
        }
    }

    private class IceParlorChildEventListener implements ChildEventListener {

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            // a new order has been added, add it to the displayed list
            OrderModel order = dataSnapshot.getValue(OrderModel.class);
            String key = dataSnapshot.getKey();

            // patch order object with firebase key
            order.setKey(key);

            // insert new order
            RecyclerViewAdapter.this.orderModels.add(order);
            RecyclerViewAdapter.this.keys.add(key);
            RecyclerViewAdapter.this.notifyItemInserted(
                    RecyclerViewAdapter.this.orderModels.size() - 1
            );

            // Log.v(Globals.TAG, "onChildAdded: added pickname  " + order.getPickupName());
            Log.v(Globals.TAG, "onChildAdded: " + order.toString());
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            Log.v(Globals.TAG, "onChildChanged");
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            Log.v(Globals.TAG, "onChildRemoved");

            // a pickup name has been redeemed, use the key to determine
            // if we are displaying this pickup name and if so remove it
            String key = dataSnapshot.getKey();

            int index = RecyclerViewAdapter.this.keys.indexOf(key);
            if (index > -1) {
                // Remove data from the list
                RecyclerViewAdapter.this.orderModels.remove(index);
                RecyclerViewAdapter.this.keys.remove(index);

                // update the recycler view
                RecyclerViewAdapter.this.notifyItemRemoved(index);
                Log.v(Globals.TAG, "onChildRemoved: removed pickname at " + Integer.toString(index));
            } else {
                Log.w(Globals.TAG, "Internal Error: onChildRemoved: unknown_child = " + key);
            }
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            Log.v(Globals.TAG, "onChildMoved");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

            Log.v(Globals.TAG, "onCancelled");
        }
    }
}

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textView;
    private MyItemClickListener clickListener;

    public ViewHolder(View itemView) {
        super(itemView);

        this.textView = itemView.findViewById(R.id.tvPickupName);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (this.clickListener != null) {
            this.clickListener.onItemClick(view, this.getAdapterPosition());
        }
    }

    public TextView getTextView() {
        return this.textView;
    }

    public void setClickListener(MyItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
