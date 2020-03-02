package com.example.split_even;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingCartAdapter extends ArrayAdapter<String> {

    private final ShoppingCart context;
    private final ArrayList<String> item_names;


    public ShoppingCartAdapter (ShoppingCart context) {
        super(context, R.layout.shoping_cart_item, context.shoppingCardItems);
        this.context=context;
        this.item_names=context.shoppingCardItems;
    }

    // Replace mocked data with our data
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.shoping_cart_item, null,true);

        TextView titleText = rowView.findViewById(R.id.title);
        titleText.setText(this.item_names.get(position));
        Button removeButton= rowView.findViewById(R.id.remove_button);

        removeButton.setOnClickListener(new PositionalOnClickListener(position) {
            @Override
            public void onClick(View removeView) {
                context.remove_shoping_cart_item(context.shoppingCardItems.get(this.position));
            }
        });
        return rowView;

    };
}

;