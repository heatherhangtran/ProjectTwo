package com.example.randybiglow.fruits;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        //Utilizing the Singleton to get to this activity.
        DatabaseHandler helper = DatabaseHandler.getInstance(DetailsView.this);

        int id = getIntent().getIntExtra("id", -1);

        if(id >= 0) {
            //Grabs all information from the database to display it in the detail page.
            Cursor cursor = helper.getFruits(id);
            String name = cursor.getString(DatabaseHandler.getInstance())
        }
    }
}
