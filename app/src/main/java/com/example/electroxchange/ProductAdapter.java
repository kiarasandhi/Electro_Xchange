package com.example.electroxchange;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// ProductAdapter class to populate the ListView with products
public class ProductAdapter extends ArrayAdapter<Products> {

    private ArrayList<Products> productList;

    public ProductAdapter(Context context, ArrayList<Products> productList) {
        super(context, 0, productList);
        this.productList = productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Products product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }

        TextView productName = convertView.findViewById(R.id.productName);
        TextView category = convertView.findViewById(R.id.category);
        TextView description = convertView.findViewById(R.id.description);
        TextView quantity = convertView.findViewById(R.id.quantity);
        TextView price = convertView.findViewById(R.id.price);

        productName.setText(product.getProductName());
        category.setText(product.getCategory());
        description.setText(product.getDescription());
        quantity.setText(String.valueOf(product.getQuantity()));
        price.setText(String.valueOf(product.getPrice()));
        return convertView;
    }
}

