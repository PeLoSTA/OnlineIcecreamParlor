package de.peterloos.petersicecreamparlor.interfaces;

import android.view.View;

// interface needed to communicate between view holders and parent adapter
public interface MyItemClickListener {
    void onItemClick(View view, int position);
}
