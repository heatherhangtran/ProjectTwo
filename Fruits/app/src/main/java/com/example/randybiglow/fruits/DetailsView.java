package com.example.randybiglow.fruits;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        //Utilizing the Singleton to get to this activity.
        DatabaseHandler helper = DatabaseHandler.getInstance(DetailsView.this);

        int id = getIntent().getIntExtra("_id", -1);

        if(id >= 0) {
            //Grabs all information from the database to display it in the detail page.
            Cursor cursor = helper.getDescriptionById(id);
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_NAME));
            String region = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_REGION));
            String season = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_SEASON));
            String medicinal = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_MEDICINAL));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COL_DESCRIPTION));

            TextView textView = (TextView)findViewById(R.id.nameTextView);
            TextView textView2 = (TextView)findViewById(R.id.descriptionView);
            textView.setText(name + " originates from " +
                            region + ". The peak season is " +
                            season + ". One of it's medicinal benefits is " +
                            medicinal + "."
            );

            textView2.setText( "Taste... " + description + ".");
        }
    }
}
