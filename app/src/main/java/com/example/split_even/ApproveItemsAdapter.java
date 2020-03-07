package com.example.split_even;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ApproveItemsAdapter extends ArrayAdapter<String> {

    private final approveNewItem context;
    private final ArrayList<String> item_names;

    public ApproveItemsAdapter(approveNewItem context) {
        super(context, R.layout.shoping_cart_item, context.approveItems);
        this.context = context;
        this.item_names = context.approveItems;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.appoved_item_adapter, null, true);

        TextView titleText = rowView.findViewById(R.id.title);
        titleText.setText(this.item_names.get(position));
        Button removeButton = rowView.findViewById(R.id.remove_button);
        Button approveButton = rowView.findViewById(R.id.approve_button);

        removeButton.setOnClickListener(new PositionalOnClickListener(position) {
            @Override
            public void onClick(View removeView) {
//                TODO: update DB
                context.remove_item_form_screen(context.approveItems.get(this.position));
            }
        });

        approveButton.setOnClickListener(new PositionalOnClickListener(position) {
            @Override
            public void onClick(View removeView) {
//                TODO: update DB
                context.remove_item_form_screen(context.approveItems.get(this.position));
            }
        });
        return rowView;

    }

    ;
}

;