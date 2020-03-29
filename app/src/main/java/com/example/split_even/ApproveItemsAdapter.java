package com.example.split_even;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        View rowView = inflater.inflate(R.layout.approved_item_adapter, null, true);

        TextView titleText = rowView.findViewById(R.id.title);
        titleText.setText(this.item_names.get(position));
        Button removeButton = rowView.findViewById(R.id.remove_button);
        Button approveButton = rowView.findViewById(R.id.approve_button);

        removeButton.setOnClickListener(new PositionalOnClickListener(position) {
            @Override
            public void onClick(View removeView) {
               // System.out.println("The removed item is: " + context.approveItems.get(this.position));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("OfferedItems");
                String item_to_remove = context.approveItems.get(this.position);
                myRef.orderByChild("itemName").equalTo(item_to_remove).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot removeItem: dataSnapshot.getChildren()) {
                            removeItem.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                context.remove_item_from_screen(context.approveItems.get(this.position));
            }
        });

        approveButton.setOnClickListener(new PositionalOnClickListener(position) {
            @Override
            public void onClick(View removeView) {

                // Add the item to SharedItems

                String item_to_approve = context.approveItems.get(this.position);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("SharedItems");
                Map<String, String> temp = new HashMap<String, String>();
                temp.put("itemName", item_to_approve);
                myRef.push().setValue(temp);

                // Remove the item from OfferedItems

                DatabaseReference myRef1 = database.getReference("OfferedItems");
                myRef1.orderByChild("itemName").equalTo(item_to_approve).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren())
                        {
                            child.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                TODO: update DB
                context.remove_item_from_screen(context.approveItems.get(this.position));
            }
        });
        return rowView;

    }

    ;
}

;