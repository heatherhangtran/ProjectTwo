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
    public void addFruits(Fruits fruits) {
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

    public Fruits getFruits(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FRUITS_TABLE_NAME,
                new String[] {COL_ID, COL_NAME, COL_REGION, COL_SEASON, COL_MEDICINAL, COL_DESCRIPTION },
                COL_ID + "=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null,
                null
        );

        if(cursor != null)
            cursor.moveToFirst();

        //Let's see when the app runs to see if this method is useful to get each fruit.
        Fruits fruits = new Fruits(Integer.parseInt(cursor.getString(0)),
                cursor.getString(Integer.parseInt(COL_NAME)),
                cursor.getString(Integer.parseInt(COL_REGION)),
                cursor.getString(Integer.parseInt(COL_SEASON)),
                cursor.getString(Integer.parseInt(COL_MEDICINAL)),
                cursor.getString(Integer.parseInt(COL_DESCRIPTION))
        );

        return fruits;
    }

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

    //This method will return the total number of fruits in database.
    //Stretch goal.
    //This is on the tutorial, so might as well try it out.
    public int getFruitsCount() {
        String countQuery = "SELECT * FROM " + FRUITS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        //String[] selectionArgs is set to null.
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
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
}
