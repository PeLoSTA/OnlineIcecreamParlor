package de.peterloos.petersicecreamparlor;

import android.view.View;

// interface needed to communicate between view holders and parent adapter
public interface IItemClickListener {
    void onItemClick(View view, int position);
}
