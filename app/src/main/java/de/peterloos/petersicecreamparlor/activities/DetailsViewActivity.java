package de.peterloos.petersicecreamparlor.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import de.peterloos.petersicecreamparlor.Globals;
import de.peterloos.petersicecreamparlor.R;
import de.peterloos.petersicecreamparlor.parcels.OrderParcel;

public class DetailsViewActivity extends AppCompatActivity implements View.OnClickListener {

    private OrderParcel parcel;

    private TextView textViewHeader;
    private TextView textViewScoops;
    private TextView textViewFlavors;
    private TextView textViewContainer;
    private Button buttonCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_details_view);

        Log.v(Globals.TAG, "DetailsViewActivity::onCreate");

        // retrieve data from main activity
        this.parcel = null;
        Bundle data = this.getIntent().getExtras();
        if (data != null) {

            this.parcel = data.getParcelable(Globals.ORDER_PARCEL);
            if (this.parcel == null) {

                Log.v(Globals.TAG, "Unexpected internal Error: parcel == NULL");
                return;
            } else
                Log.v(Globals.TAG, this.parcel.toString());
        }

        // retrieve references of controls
        this.textViewHeader = this.findViewById(R.id.textViewHeader);
        this.textViewScoops = this.findViewById(R.id.textViewScoops);
        this.textViewFlavors = this.findViewById(R.id.textViewFlavors);
        this.textViewContainer = this.findViewById(R.id.textViewContainer);
        this.buttonCheckout = this.findViewById(R.id.buttonCheckout);
        this.buttonCheckout.setOnClickListener(this);

        // fill text views with data
        String sHeader = "Order No. " + Long.toString(this.parcel.getPickupId());
        this.textViewHeader.setText(sHeader);

        String sScoops = String.format(
                Locale.getDefault(),
                "%d scoops",
                this.parcel.getScoops()
        );
        this.textViewScoops.setText(sScoops);

        String sFlavors = "";
        String[] flavors = this.parcel.getFlavors();
        if (flavors.length == 1) {
            sFlavors = flavors[0];
        } else if (flavors.length == 2) {
            sFlavors = flavors[0] + " and " + flavors[1];
        } else if (flavors.length == 3) {
            sFlavors = flavors[0] + ", " + flavors[1] + " and " + flavors[2];
        }
        this.textViewFlavors.setText(sFlavors);

        String sContainer = "Container: " + this.parcel.getContainer();
        this.textViewContainer.setText(sContainer);

        // this.key = parcel.getKey();
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Checkout Order?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String msg = String.format(Locale.getDefault(), "Checking out order with id %d !",
                        DetailsViewActivity.this.parcel.getPickupId());

                Toast.makeText(DetailsViewActivity.this, msg, Toast.LENGTH_LONG).show();

                // check out this order: delete corresponding data in firebase backend
                DetailsViewActivity.this.deleteOrder();

                // navigate back to main activity
                DetailsViewActivity.this.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(
                        DetailsViewActivity.this,
                        "Checkout cancelled !",
                        Toast.LENGTH_LONG).show();
            }
        });

        // create the AlertDialog object and show it
        builder.create();
        builder.show();
    }

    public void deleteOrder() {

        // initialize database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("orders").child(this.parcel.getKey());

        if (ref != null) {
            ref.removeValue();
        }
    }
}
