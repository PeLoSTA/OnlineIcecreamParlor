package de.peterloos.petersicecreamparlor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PeLo_Main";

    // firebase
    private DatabaseReference mOrdersReference;

    private RecyclerView mPickupNamesRecyclerView;

    private RecyclerViewPickupNamesAdapterEx mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Log.v(TAG, "MainActivity::onCreate");

        // test data to populate the recycler view with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Sheep");
        animalNames.add("Goat");
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");

        // set up recycler view for pickup names
        // ERSTE VARIANTE -- GEHT !!! -- ABER OHNE FIREBASE
//        mPickupNamesRecyclerView = this.findViewById(R.id.recyclerViewPickupNames);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new RecyclerViewPickupNamesAdapter(this, animalNames);
//        // adapter.setClickListener(this);
//        DividerItemDecoration dividerItemDecoration =
//                new DividerItemDecoration(
//                        recyclerView.getContext(), layoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
//        recyclerView.setAdapter(mAdapter);

        // set up recycler view for pickup names
        // ZWEITE VARIANTE -- GEHT !!! -- ABER OHNE FIREBASE

        // initialize database
        mOrdersReference = FirebaseDatabase.getInstance().getReference().child("orders");

        // initialize views
        mPickupNamesRecyclerView = this.findViewById(R.id.recyclerViewPickupNames);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPickupNamesRecyclerView.setLayoutManager(layoutManager);
        // adapter.setClickListener(this);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        mPickupNamesRecyclerView.getContext(), layoutManager.getOrientation());
        mPickupNamesRecyclerView.addItemDecoration(dividerItemDecoration);

//        mAdapter = new RecyclerViewPickupNamesAdapterEx(this, animalNames);
//        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.v(TAG, "MainActivity::onStart");

        // listen for orders
        mAdapter = new RecyclerViewPickupNamesAdapterEx(this, mOrdersReference);
        mPickupNamesRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.v(TAG, "MainActivity::onStop");

        // clean up orders listener
        mAdapter.cleanupListener();
    }
}
