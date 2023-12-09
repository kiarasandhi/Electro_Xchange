package com.example.electroxchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CartProductAdapter extends ArrayAdapter<Products> {
    private Context mContext;
    private ArrayList<Products> mCartItems;

    public CartProductAdapter(Context context, ArrayList<Products> cartItems) {
        super(context, 0, cartItems);
        mContext = context;
        mCartItems = cartItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.cart_item_layout, parent, false);
        }

        Products currentProduct = mCartItems.get(position);

        TextView productName = listItem.findViewById(R.id.productName);
        productName.setText(currentProduct.getProductName());

        TextView productCategory = listItem.findViewById(R.id.productCategory);
        productCategory.setText(currentProduct.getCategory());

        TextView productDescription = listItem.findViewById(R.id.productDescription);
        productDescription.setText(currentProduct.getDescription());

        TextView productQuantity = listItem.findViewById(R.id.productQuantity);
        productQuantity.setText(String.valueOf(currentProduct.getQuantity()));

        TextView productPrice = listItem.findViewById(R.id.productPrice);
        productPrice.setText(String.valueOf(currentProduct.getPrice()));

        return listItem;
    }
}
