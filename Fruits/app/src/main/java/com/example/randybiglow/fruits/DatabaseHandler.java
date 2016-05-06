package com.example.randybiglow.fruits;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
