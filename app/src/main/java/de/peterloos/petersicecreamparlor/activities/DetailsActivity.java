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

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import de.peterloos.petersicecreamparlor.Globals;
import de.peterloos.petersicecreamparlor.R;
import de.peterloos.petersicecreamparlor.parcels.OrderParcel;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private OrderParcel parcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_details_view);

        Log.v(Globals.TAG, "DetailsActivity::onCreate");

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
        TextView textViewHeader = this.findViewById(R.id.textViewHeader);
        TextView textViewScoops = this.findViewById(R.id.textViewScoops);
        TextView textViewFlavors = this.findViewById(R.id.textViewFlavors);
        TextView textViewContainer = this.findViewById(R.id.textViewContainer);
        TextView textViewDateTime = this.findViewById(R.id.textViewDateTime);
        Button buttonCheckout = this.findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(this);

        // fill text views with data
        String sHeader = "Order No. " + Long.toString(this.parcel.getPickupId());
        textViewHeader.setText(sHeader);

        String sScoops = String.format(
                Locale.getDefault(),
                "%d scoops",
                this.parcel.getScoops()
        );
        textViewScoops.setText(sScoops);

        String sFlavors = "";
        String[] flavors = this.parcel.getFlavors();
        if (flavors.length == 1) {
            sFlavors = flavors[0];
        } else if (flavors.length == 2) {
            sFlavors = flavors[0] + " and " + flavors[1];
        } else if (flavors.length == 3) {
            sFlavors = flavors[0] + ", " + flavors[1] + " and " + flavors[2];
        }
        textViewFlavors.setText(sFlavors);

        String sContainer = "Container: " + this.parcel.getContainer();
        textViewContainer.setText(sContainer);

        long ts = parcel.getTimeStamp();
        Date date = new Date(ts);
        String s1 = DateFormat.getDateInstance().format(date);
        String s2 = DateFormat.getTimeInstance().format(date);
        String[] parts = s2.split(":");

        String date_time = "From " + s1 + " at " + parts[0] + ":" + parts[1];
        textViewDateTime.setText(date_time);
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Checkout Order?");
        builder.setPositiveButton(
            R.string.yes_action,
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    String msg = String.format(
                        Locale.getDefault(),
                        "Checking out order with id %d !",
                        DetailsActivity.this.parcel.getPickupId()
                    );

                    Toast.makeText(DetailsActivity.this, msg, Toast.LENGTH_LONG).show();

                    // check out this order: delete corresponding data in firebase backend
                    DetailsActivity.this.deleteOrder();

                    // navigate back to main activity
                    DetailsActivity.this.finish();
                }
            }
        );
        builder.setNegativeButton(
                R.string.no_action,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(
                                DetailsActivity.this,
                                R.string.action_cancelled,
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

        // create the AlertDialog object and show it
        builder.create();
        builder.show();
    }

    public void deleteOrder() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref =
            database.
            getReference().
            child("orders").
            child(this.parcel.getKey());

        ref.removeValue();
    }
}
