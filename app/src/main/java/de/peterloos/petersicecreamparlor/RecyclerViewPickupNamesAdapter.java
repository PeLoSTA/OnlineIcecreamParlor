package de.peterloos.petersicecreamparlor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewPickupNamesAdapter extends RecyclerView.Adapter<RecyclerViewPickupNamesAdapter.ViewHolder> {

    private List<String> data;
    private LayoutInflater inflater;

    public RecyclerViewPickupNamesAdapter(Context context, List<String> data) {
        // data is passed into this constructor
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // inflates row layout from xml when needed
        View view = this.inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String name = this.data.get(position);
        viewHolder.getTextView().setText(name);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        // TODO: Hmmm, kann man diskutieren, ob man den braucht ?!?!?!?
        public TextView getTextView() {
            return this.textView;
        }

        public ViewHolder(View itemView) {
            super(itemView);

            this.textView = itemView.findViewById(R.id.tvPickupName);

            // itemView.setOnClickListener(this);
        }

        // TODO: AB HIER FEHLT DER GANZE REST VON DER VORLAGE !!!!!!!
    }
}
