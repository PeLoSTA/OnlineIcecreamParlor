package de.peterloos.petersicecreamparlor.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import de.peterloos.petersicecreamparlor.Globals;
import de.peterloos.petersicecreamparlor.R;
import de.peterloos.petersicecreamparlor.parcels.OrderParcel;

public class DetailsViewActivity extends AppCompatActivity {

    private TextView textViewHeader;
    private TextView textViewScoops;
    private TextView textViewFlavors;
    private TextView textViewContainer;
    private Button   buttonCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_details_view);

        // retrieve data from main activity
        OrderParcel parcel = null;
        Bundle data = this.getIntent().getExtras();
        if (data != null) {

            parcel = data.getParcelable(Globals.ORDER_PARCEL);
            if (parcel == null) {

                Log.v(Globals.TAG, "Unexpected internal Error: parcel == NULL");
                return;
            }
            else
                Log.v(Globals.TAG, parcel.toString());
        }

        // retrieve references of controls
        this.textViewHeader = this.findViewById(R.id.textViewHeader);
        this.textViewScoops = this.findViewById(R.id.textViewScoops);
        this.textViewFlavors = this.findViewById(R.id.textViewFlavors);
        this.textViewContainer = this.findViewById(R.id.textViewContainer);
        this.buttonCheckout = this.findViewById(R.id.buttonCheckout);

        // fill text views with data
        String sHeader = "Order No. " + Long.toString(parcel.getPickupId());
        this.textViewHeader.setText(sHeader);

        String sScoops = String.format(Locale.getDefault(), "%d scoops", parcel.getScoops());
        this.textViewScoops.setText(sScoops);

        String sFlavors = "";
        String[] flavors = parcel.getFlavors();
        if (flavors.length == 1) {
            sFlavors = flavors[0];
        }
        else if (flavors.length == 2) {
            sFlavors = flavors[0] + " and " + flavors[1];
        }
        else if (flavors.length == 3) {
            sFlavors = flavors[0] + ", " + flavors[1] + " and " + flavors[2];
        }
        this.textViewFlavors.setText(sFlavors);

        String sContainer = "Container: " + parcel.getContainer();
        this.textViewContainer.setText(sContainer);
    }
}
