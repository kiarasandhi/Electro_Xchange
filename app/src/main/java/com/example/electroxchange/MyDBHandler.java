package com.example.electroxchange;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ElectroXChangeDB.db";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_PRODUCT_ID = "produkID";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_PRICE = "price";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create the products table
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS +
                "(" + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_ID + " INTEGER," + // Foreign key referencing users table
                COLUMN_PRODUCT_NAME + " TEXT," +
                COLUMN_CATEGORY + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_QUANTITY + " INTEGER," +
                COLUMN_PRICE + " REAL," +
                "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")" +
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public long addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result;
    }

    public long addProduct(int userId, String product_name, String category, String description, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_PRODUCT_NAME, product_name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_PRICE, price);
        long result = -1; // Initialize result with an invalid value

        try {
            result = db.insert(TABLE_PRODUCTS, null, values);
            // Check if insertion was successful
            if (result == -1) {
                // Log an error message if insertion failed
                Log.e("AddProduct", "Failed to insert product into database");
            }
        } catch (Exception e) {
            // Log any exceptions or errors
            Log.e("AddProductError", "Error adding product: " + e.getMessage());
        } finally {
            db.close();
        }

        return result;
    }


    public int getUserId(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1;
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }

        cursor.close();
        db.close();

        return userId;
    }
    public ArrayList<Product> getProductsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> productList = new ArrayList<>();

        String[] columns = {COLUMN_PRODUCT_NAME, COLUMN_CATEGORY, COLUMN_DESCRIPTION, COLUMN_QUANTITY, COLUMN_PRICE};
        String selection = COLUMN_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_PRODUCTS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));

                // Create Product object with the correct constructor parameters
                Product product = new Product(productName, category, description, quantity, price);
                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return productList;
    }

    public boolean updateUserDetails(String newEmail, String newFullName, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, newEmail);
        values.put(COLUMN_NAME, newFullName);
        values.put(COLUMN_PASSWORD, newPassword);

        int rowsAffected = db.update(TABLE_USERS, values, null, null);
        db.close();

        return rowsAffected > 0;
    }

}
