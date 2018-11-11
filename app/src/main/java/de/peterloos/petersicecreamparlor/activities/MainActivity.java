package de.peterloos.petersicecreamparlor.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import de.peterloos.petersicecreamparlor.Globals;
import de.peterloos.petersicecreamparlor.interfaces.MyItemClickListener;
import de.peterloos.petersicecreamparlor.R;
import de.peterloos.petersicecreamparlor.adapters.RecyclerViewAdapter;
import de.peterloos.petersicecreamparlor.models.OrderModel;
import de.peterloos.petersicecreamparlor.parcels.OrderParcel;

public class MainActivity extends AppCompatActivity implements MyItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Log.v(Globals.TAG, "MainActivity::onCreate");

        // set up recycler view for pickup names
        this.recyclerView = this.findViewById(R.id.recyclerViewPickupNames);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        this.recyclerView.getContext(),
                        layoutManager.getOrientation()
                );
        this.recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(Globals.TAG, "MainActivity::onStart");

        // listen for orders
        this.adapter = new RecyclerViewAdapter(this);
        this.adapter.setClickListener(this);
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(Globals.TAG, "MainActivity::onStop");

        // clean up orders listener
        this.adapter.cleanupListener();
    }

    @Override
    public void onItemClick(View view, int position) {

        OrderModel order = this.adapter.getOrder(position);
        Intent intent = new Intent(this.getApplicationContext(), DetailsViewActivity.class);
        OrderParcel parcel = new OrderParcel(
                order.getKey(),
                order.getTimeOfOrder(),
                order.getPickupName(),
                order.getScoops(),
                order.getFlavorsArray(),
                order.getContainer()
        );
        intent.putExtra(Globals.ORDER_PARCEL, parcel);
        this.startActivity(intent);
    }
}
