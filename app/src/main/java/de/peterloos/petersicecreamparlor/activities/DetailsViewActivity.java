package de.peterloos.petersicecreamparlor.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import de.peterloos.petersicecreamparlor.Globals;
import de.peterloos.petersicecreamparlor.R;
import de.peterloos.petersicecreamparlor.models.OrderModel;
import de.peterloos.petersicecreamparlor.parcels.OrderParcel;

public class DetailsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_details_view);

        OrderParcel anyModel = null;

        Bundle data = this.getIntent().getExtras();
        if (data != null) {

            anyModel = data.getParcelable(Globals.ORDER_PARCEL);
            Log.v(Globals.TAG, anyModel.toString());
        }

        if (anyModel == null) {

            Log.v(Globals.TAG, "nietttttttttttt");
        }
    }
}
