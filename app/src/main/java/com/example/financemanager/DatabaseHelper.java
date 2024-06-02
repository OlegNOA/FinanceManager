package com.example.financemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //объявите все необходимые столбцы в datasabe
    public static String DATABASE_NAME = "money_manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "user";
    private static final String TABLE_TRANSACTIONS= "transactions";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_ID_TRANSACTIONS = "id_transactions";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DATE ="date";
    private static final String KEY_TOTAL ="total";
    private static final String KEY_CATEGORIES ="categories";
    private static final String KEY_TYPES = "types";
    private static final String KEY_DESCRIPTION = "description";

    //объявление команды create user TABLE для sqlite
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "("
            + KEY_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_EMAIL + " TEXT ,"
            + KEY_USERNAME+" TEXT,"
            + KEY_PASSWORD+" TEXT );";
    //объявление команды create transaction TABLE для sqlite
    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE "
            + TABLE_TRANSACTIONS + "("
            + KEY_ID_TRANSACTIONS+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_DATE + " DATE ,"
            + KEY_TYPES+" TEXT ,"
            + KEY_CATEGORIES+ " TEXT,"
            + KEY_ID_USER+" INTEGER,"
            + KEY_TOTAL+" INTEGER,"
            + KEY_DESCRIPTION+" TEXT );";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("table", CREATE_TABLE_USER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //выполните команды создания пользовательской таблицы и транзакции
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TRANSACTIONS + "'");
        onCreate(db);
    }

    //способ untuk вставить пользователя в таблицу
    public void addUser(String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //данные, которые будут вставлены, будут представлять собой сохраненные значения переменных в соответствии со столбцами таблицы
        values.put(KEY_EMAIL, email);
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);

        db.insert(TABLE_USER, null, values); //измените данные в нужных значениях на вставку в базу данных пользователя
    }



    //способ добавления транзакции
    public void addTransactions(String date, String types, String categories, int id_user ,int total, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_TYPES, types);
        values.put(KEY_CATEGORIES,categories);
        values.put(KEY_ID_USER, id_user);
        values.put(KEY_TOTAL,total);
        values.put(KEY_DESCRIPTION, description);


        db.insert(TABLE_TRANSACTIONS, null, values);
    }

    //способ untuk обновить транзакции
    public void updateTransactions(int id, String date, String types, String categories, int id_user ,int total, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_TYPES, types);
        values.put(KEY_CATEGORIES,categories);
        values.put(KEY_ID_USER, id_user);
        values.put(KEY_TOTAL,total);
        values.put(KEY_DESCRIPTION, description);
        db.update(TABLE_TRANSACTIONS, values, KEY_ID_TRANSACTIONS + " = ?", new String[]{String.valueOf(id)});
    }
}
