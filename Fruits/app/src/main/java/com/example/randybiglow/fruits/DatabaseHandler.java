package com.example.randybiglow.fruits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RandyBiglow on 5/6/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    //All variables are public static final.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FruitsDatabaseName.db";
    public static final String FRUITS_TABLE_NAME = "FruitsTableName";

    //Table columns
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME"; //Had to make this not private for the MainActivity to pick up.
    public static final String COL_REGION = "REGION";
    public static final String COL_SEASON = "SEASON";
    public static final String COL_MEDICINAL = "MEDICINAL";
    public static final String COL_DESCRIPTION = "DESCRIPTION";

    //All of the columns together:
    public static final String[] FRUITS_COLUMNS = {COL_ID, COL_NAME, COL_REGION, COL_SEASON, COL_MEDICINAL, COL_DESCRIPTION};

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

    //Using Singleton to fetch each fruit.
    private static DatabaseHandler mInstance;
    public static DatabaseHandler getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new DatabaseHandler(context.getApplicationContext());
        }

        return mInstance;
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

         //String[] projection = new String[] {COL_ID, COL_NAME, COL_REGION, COL_SEASON};

         Cursor cursor = db.query(FRUITS_TABLE_NAME,
                FRUITS_COLUMNS,
                null,
                null,
                null,
                null,
                null,
                null
         );

         return cursor;
    }

    //Method to send data to DetailView Activity.
    public Cursor getDescriptionById(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FRUITS_TABLE_NAME,
                new String[]{COL_NAME, COL_REGION, COL_SEASON, COL_MEDICINAL, COL_DESCRIPTION},
                COL_ID+" = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            return cursor;
        } else {
            return null;
        }
    }

    //Allows database to be searchable through three different criteria.
    public Cursor searchFruits(String query) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FRUITS_TABLE_NAME,
                FRUITS_COLUMNS,
                COL_NAME + " LIKE ? OR " + COL_REGION + " LIKE ? OR " + COL_SEASON + " LIKE ?",
                new String[]{"%" + query + "%", "%" + query + "%" , "%" + query + "%"},
                null,
                null,
                null,
                null
        );

        return cursor;
    }

}
