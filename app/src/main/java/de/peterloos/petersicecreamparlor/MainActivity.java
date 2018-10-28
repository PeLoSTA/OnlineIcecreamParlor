package de.peterloos.petersicecreamparlor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.peterloos.petersicecreamparlor.parcels.OrderParcel;

public class MainActivity extends AppCompatActivity implements IItemClickListener {

    private static final String TAG = "PeLo_Main";

    private DatabaseReference mOrdersReference;
    private RecyclerView mPickupNamesRecyclerView;
    private RecyclerViewPickupNamesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Log.v(TAG, "MainActivity::onCreate");

        // initialize database
        mOrdersReference = FirebaseDatabase.getInstance().getReference().child("orders");

        // set up recycler view for pickup names
        mPickupNamesRecyclerView = this.findViewById(R.id.recyclerViewPickupNames);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPickupNamesRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        mPickupNamesRecyclerView.getContext(), layoutManager.getOrientation());
        mPickupNamesRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "MainActivity::onStart");

        // listen for orders
        mAdapter = new RecyclerViewPickupNamesAdapter(this, mOrdersReference);
        mAdapter.setClickListener(this);
        mPickupNamesRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "MainActivity::onStop");

        // clean up orders listener
        mAdapter.cleanupListener();
    }

    @Override
    public void onItemClick(View view, int position) {

        String text = "You clicked " + mAdapter.getItem(position) + " on row number " + position;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        String pickupId = mAdapter.getItem(position);


        Intent intent = new Intent(getApplicationContext(), DetailsViewActivity.class);
        OrderParcel parcel = new OrderParcel(pickupId, 3, new String[] {"Vanilla", "Strawberry", "Chocolate"}, "cone");
        intent.putExtra(Globals.ORDER_PARCEL, parcel);
        this.startActivity(intent);
    }
}
