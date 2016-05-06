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
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "FruitsDatabaseName.db";
    public static final String FRUITS_TABLE_NAME = "FruitsTableName";

    //Table columns
    public static final String COL_ID = "_id";
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
//        db.close();//This must close to avoid two connections at the same time.

        //Seeding database
        addFruits(db, new Fruits(1, "Jackfruit", "Asia", "Summer", "antibacterial", "yellow pieces of heaven"));
        addFruits(db, new Fruits(2, "Mangosteen", "Asia", "Fall", "diuretic", "purple exterior with white flesh"));
        addFruits(db, new Fruits(3, "Cherimoya", "North America", "Spring", "anti-inflammatory", "sweet custard"));
        addFruits(db, new Fruits(4, "Lychee", "Asia", "Winter", "anti-acid", "crispy grapes"));
        addFruits(db, new Fruits(5, "Pitaya", "Asia", "Fall", "anti-aging", "mild kiwi"));
        addFruits(db, new Fruits(6, "Waterapple", "Asia", "Spring", "anti-diabetes", "tangy apple"));
        addFruits(db, new Fruits(7, "Breadfruit", "Africa", "Summer", "antioxidants", "spongy"));
        addFruits(db, new Fruits(8, "Soursop", "South America", "Spring", "antimicrobial", "sweet and tangy custard"));
        addFruits(db, new Fruits(9, "Redcurrant", "Europe", "Summer", "anti-coagulant", "bright red goodness"));
        addFruits(db, new Fruits(10, "Finger Lime", "Australia", "Winter", "antioxidant", "beautiful sour beings"));
        addFruits(db, new Fruits(11, "Persimmons", "North America", "Fall", "anti-inflammatory", "cinnamon crispy apples"));
        addFruits(db, new Fruits(12, "Cuckoo", "Africa", "Summer", "cough suppressant", "soft sweet deliciousness"));
        addFruits(db, new Fruits(13, "Guava", "South America", "Winter", "pain reliever", "fragrant bursts of firework on the tongue"));
        addFruits(db, new Fruits(14, "Ganga", "North America", "Spring", "anything", "buds, lots of buds"));
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

    //Methods for adding data to database.
    void addFruits(SQLiteDatabase db, Fruits fruits) {
//        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, fruits.getName());
        values.put(COL_REGION, fruits.getRegion());
        values.put(COL_SEASON, fruits.getSeason());
        values.put(COL_MEDICINAL, fruits.getMedicinal());
        values.put(COL_DESCRIPTION, fruits.getDescription());

        db.insert(FRUITS_TABLE_NAME, null, values);
//        db.close();
    }

     public Cursor getFruits() {
         SQLiteDatabase db = this.getReadableDatabase();

         String[] projection = new String[] {COL_ID, COL_NAME, COL_REGION, COL_SEASON};

         Cursor cursor = db.query(FRUITS_TABLE_NAME,
                projection,
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
