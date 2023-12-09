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
    private static final String TABLE_CART = "cart";
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
    private static final String COLUMN_CART_ID = "cart_id";
    private static final String COLUMN_CART_USER_ID = "cart_user_id";
    private static final String COLUMN_CART_PRODUCT_ID = "cart_product_id";
    private static final String COLUMN_CART_PRODUCT_NAME = "cart_product_name";
    private static final String COLUMN_CART_CATEGORY = "cart_category";
    private static final String COLUMN_CART_DESCRIPTION = "cart_description";
    private static final String COLUMN_CART_QUANTITY = "cart_quantity";
    private static final String COLUMN_CART_PRICE = "cart_price";
    private static final String COLUMN_CART_TOTAL_PRICE = "cart_total_price";
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String COLUMN_TRANSACTION_ID = "transaction_id";
    private static final String COLUMN_TRANSACTION_USER_ID = "user_id";
    private static final String COLUMN_TRANSACTION_DATE = "transaction_date";
    private static final String COLUMN_TRANSACTION_TOTAL = "total_amount";
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS +
                "(" + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_ID + " INTEGER," +
                COLUMN_PRODUCT_NAME + " TEXT," +
                COLUMN_CATEGORY + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_QUANTITY + " INTEGER," +
                COLUMN_PRICE + " REAL," +
                "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")" +
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART +
                "(" + COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CART_USER_ID + " INTEGER," +
                COLUMN_CART_PRODUCT_ID + " INTEGER," +
                COLUMN_CART_PRODUCT_NAME + " TEXT," +
                COLUMN_CART_CATEGORY + " TEXT," +
                COLUMN_CART_DESCRIPTION + " TEXT," +
                COLUMN_CART_QUANTITY + " INTEGER," +
                COLUMN_CART_PRICE + " REAL," +
                COLUMN_CART_TOTAL_PRICE + " REAL," +
                "FOREIGN KEY(" + COLUMN_CART_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")" +
                ")";
        db.execSQL(CREATE_CART_TABLE);
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS +
                "(" + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TRANSACTION_USER_ID + " INTEGER," +
                COLUMN_TRANSACTION_DATE + " TEXT," +
                COLUMN_TRANSACTION_TOTAL + " REAL," +
                "FOREIGN KEY(" + COLUMN_TRANSACTION_USER_ID + ") REFERENCES " +
                TABLE_USERS + "(" + COLUMN_ID + ")" +
                ")";
        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
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
        long result = -1;

        try {
            result = db.insert(TABLE_PRODUCTS, null, values);
            if (result == -1) {
                Log.e("AddProduct", "Failed to insert product into database");
            }
        } catch (Exception e) {
            Log.e("AddProductError", "Error adding product: " + e.getMessage());
        } finally {
            db.close();
        }

        return result;
    }
    public long addToCart(int userId, int productId, String productName, String category,
                          String description, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_USER_ID, userId);
        values.put(COLUMN_CART_PRODUCT_ID, productId);
        values.put(COLUMN_CART_PRODUCT_NAME, productName);
        values.put(COLUMN_CART_CATEGORY, category);
        values.put(COLUMN_CART_DESCRIPTION, description);
        values.put(COLUMN_CART_QUANTITY, quantity);
        values.put(COLUMN_CART_PRICE, price);
        values.put(COLUMN_CART_TOTAL_PRICE, quantity * price);

        long result = db.insert(TABLE_CART, null, values);
        db.close();
        return result;
    }
    public long addTransaction(int userId, String transactionDate, double totalAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_USER_ID, userId);
        values.put(COLUMN_TRANSACTION_DATE, transactionDate);
        values.put(COLUMN_TRANSACTION_TOTAL, totalAmount);

        long result = db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();
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
    public ArrayList<Products> getProductsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> productList = new ArrayList<>();

        String[] columns = {COLUMN_PRODUCT_ID, COLUMN_PRODUCT_NAME, COLUMN_CATEGORY, COLUMN_DESCRIPTION, COLUMN_QUANTITY, COLUMN_PRICE};
        String selection = COLUMN_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_PRODUCTS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ID));
                String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));

                Products product = new Products(productId, productName, category, description, quantity, price);
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
    public ArrayList<Products> getCartItemsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> cartItems = new ArrayList<>();

        String[] columns = {COLUMN_CART_PRODUCT_ID, COLUMN_CART_PRODUCT_NAME, COLUMN_CART_CATEGORY, COLUMN_CART_DESCRIPTION, COLUMN_CART_QUANTITY, COLUMN_CART_PRICE};
        String selection = COLUMN_CART_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_CART, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(cursor.getColumnIndex(COLUMN_CART_PRODUCT_ID));
                String productName = cursor.getString(cursor.getColumnIndex(COLUMN_CART_PRODUCT_NAME));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CART_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_CART_DESCRIPTION));
                int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_CART_QUANTITY));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_CART_PRICE));

                Products cartProduct = new Products(productId, productName, category, description, quantity, price);
                cartItems.add(cartProduct);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return cartItems;
    }
    public double calculateTotalCartAmount(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalAmount = 0;

        String[] columns = {COLUMN_CART_TOTAL_PRICE};
        String selection = COLUMN_CART_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_CART, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                double totalPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_CART_TOTAL_PRICE));
                totalAmount += totalPrice;
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return totalAmount;
    }
    public boolean clearCart(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_CART, COLUMN_CART_USER_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();

        return rowsDeleted > 0;
    }
    public ArrayList<Transaction> getTransactionsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Transaction> transactionList = new ArrayList<>();

        String[] columns = {COLUMN_TRANSACTION_ID, COLUMN_TRANSACTION_DATE, COLUMN_TRANSACTION_TOTAL};
        String selection = COLUMN_TRANSACTION_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_TRANSACTIONS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int transactionId = cursor.getInt(cursor.getColumnIndex(COLUMN_TRANSACTION_ID));
                String transactionDate = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE));
                double totalAmount = cursor.getDouble(cursor.getColumnIndex(COLUMN_TRANSACTION_TOTAL));

                Transaction transaction = new Transaction(transactionId, transactionDate, totalAmount);
                transactionList.add(transaction);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return transactionList;
    }

    public boolean removeProductItem(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_PRODUCTS, COLUMN_PRODUCT_ID + "=?", new String[]{String.valueOf(productId)});
        db.close();

        return rowsDeleted > 0;
    }
    public ArrayList<Products> getProductsByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> productList = new ArrayList<>();

        String[] columns = {COLUMN_PRODUCT_ID, COLUMN_PRODUCT_NAME, COLUMN_CATEGORY, COLUMN_DESCRIPTION, COLUMN_QUANTITY, COLUMN_PRICE};
        String selection = COLUMN_CATEGORY + "=?";
        String[] selectionArgs = {category};

        Cursor cursor = db.query(TABLE_PRODUCTS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ID));
                String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
                String cat = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));

                Products product = new Products(productId, productName, cat, description, quantity, price);
                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return productList;
    }

    public ArrayList<Products> getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> productList = new ArrayList<>();

        String[] columns = {COLUMN_PRODUCT_ID, COLUMN_PRODUCT_NAME, COLUMN_CATEGORY, COLUMN_DESCRIPTION, COLUMN_QUANTITY, COLUMN_PRICE};

        Cursor cursor = db.query(TABLE_PRODUCTS, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ID));
                String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));

                Products product = new Products(productId, productName, category, description, quantity, price);
                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return productList;
    }

}
