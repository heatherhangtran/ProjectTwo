package com.example.randybiglow.fruits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyBiglow on 5/6/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    //All variables are private static final.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FruitsDatabaseName.db";
    private static final String FRUITS_TABLE_NAME = "FruitsTableName";

    //Table columns
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_REGION = "REGION";
    private static final String COL_SEASON = "SEASON";
    private static final String COL_MEDICINAL = "MEDICINAL";
    private static final String COL_DESCRIPTION = "DESCRIPTION";

    //All of the columns together:
    private static final String[] FRUITS_COLUMNS = {COL_ID, COL_NAME, COL_REGION, COL_MEDICINAL, COL_DESCRIPTION};

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Method to create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FRUITS_TABLE = "CREATE TABLE " + FRUITS_TABLE_NAME +
                "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_REGION + " TEXT, " +
                COL_SEASON + " TEXT, " +
                COL_MEDICINAL + " TEXT, " +
                COL_DESCRIPTION + " TEXT )";
        db.execSQL(CREATE_FRUITS_TABLE);
    }

    //Method to modify table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FRUITS_TABLE_NAME);
        onCreate(db);
    }

    //Methods for read and write operations.
    void addFruits(Fruits fruits) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID, fruits.getID());
        values.put(COL_NAME, fruits.getName());
        values.put(COL_REGION, fruits.getRegion());
        values.put(COL_SEASON, fruits.getSeason());
        values.put(COL_MEDICINAL, fruits.getMedicinal());
        values.put(COL_DESCRIPTION, fruits.getDescription());

        db.insert(FRUITS_TABLE_NAME, null, values);
        db.close();
    }

     public Cursor getFruits() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FRUITS_TABLE_NAME,
                FRUITS_COLUMNS,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Use this method to display default list (if possible).
    public List<Fruits> getAllFruits() {
        List<Fruits> fruitsList = new ArrayList<Fruits>();
        String selectQuery = "SELECT * FROM " + FRUITS_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Fruits fruits = new Fruits();
                fruits.setID(Integer.parseInt(cursor.getString(0)));
                fruits.setName(cursor.getString(1));
                fruits.setRegion(cursor.getString(2));
                fruits.setSeason(cursor.getString(3));
                fruits.setMedicinal(cursor.getString(4));
                fruits.setDescription(cursor.getString(5));

                fruitsList.add(fruits);
            }while (cursor.moveToFirst());
        }

        return fruitsList;
    }

    //Stretch goal.
    public int updateFruits(Fruits fruits) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, fruits.getName());
        values.put(COL_REGION, fruits.getRegion());
        values.put(COL_SEASON, fruits.getSeason());
        values.put(COL_MEDICINAL, fruits.getMedicinal());
        values.put(COL_DESCRIPTION, fruits.getDescription());

        return db.update(FRUITS_TABLE_NAME,
                values,
                COL_ID + " = ?",
                new String[] {String.valueOf(fruits.getID())}
                );
    }

    //Stretch goal.
    public void deleteFruits(Fruits fruits) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FRUITS_TABLE_NAME,
                COL_ID + " = ?",
                new String[] { String.valueOf(fruits.getID()) }
        );
        db.close();
    }

    //Using Singleton to fetch each fruit.
    private static DatabaseHandler mInstance;
    public static DatabaseHandler getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new DatabaseHandler(context.getApplicationContext());
        }

        return mInstance;
    }
}
