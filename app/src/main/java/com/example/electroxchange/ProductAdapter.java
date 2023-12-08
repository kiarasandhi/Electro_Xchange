package com.example.electroxchange;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// ProductAdapter class to populate the ListView with products
public class ProductAdapter extends ArrayAdapter<Product> {

    private ArrayList<Product> productList;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        super(context, 0, productList);
        this.productList = productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Product product = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }

        // Lookup view for data population
        TextView productName = convertView.findViewById(R.id.productName);
        TextView category = convertView.findViewById(R.id.category);
        TextView description = convertView.findViewById(R.id.description);
        TextView quantity = convertView.findViewById(R.id.quantity);
        TextView price = convertView.findViewById(R.id.price);

        // Populate the data into the template view using the data object
        productName.setText(product.getProductName());
        category.setText(product.getCategory());
        description.setText(product.getDescription());
        quantity.setText(String.valueOf(product.getQuantity()));
        price.setText(String.valueOf(product.getPrice()));

        // Return the completed view to render on screen
        return convertView;
    }
}

