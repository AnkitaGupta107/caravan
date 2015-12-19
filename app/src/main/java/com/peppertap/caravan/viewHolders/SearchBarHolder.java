package com.peppertap.caravan.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.peppertap.caravan.R;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by samvedana on 19/12/15.
 */
public class SearchBarHolder extends RecyclerView.ViewHolder {
    private final MaterialEditText searchEditText;

    public SearchBarHolder(View itemView, TextWatcher textWatcher, TextView.OnEditorActionListener listener) {
        super(itemView);
        searchEditText = (MaterialEditText)itemView.findViewById(R.id.search);
        searchEditText.addTextChangedListener(textWatcher);
        searchEditText.setOnEditorActionListener(listener);
    }
}
