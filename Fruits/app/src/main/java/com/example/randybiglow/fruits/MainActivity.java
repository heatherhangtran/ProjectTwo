package com.example.randybiglow.fruits;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler db;
    private CursorAdapter cursorAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.mainListView);
        db = DatabaseHandler.getInstance(MainActivity.this);
        Cursor mCursor = db.getFruits();
        cursorAdapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1,
                mCursor,
                new String[]{DatabaseHandler.COL_NAME},
                new int[]{android.R.id.text1},
                0
        );
        listView.setAdapter(cursorAdapter);

        handleIntent(getIntent());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsView.class);
                Cursor newCursor = (Cursor) parent.getAdapter().getItem(position);
                intent.putExtra("_id", newCursor.getInt(newCursor.getColumnIndex(DatabaseHandler.COL_ID)));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
    
    private void handleIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //Cursor searchCursor = DatabaseHandler.getInstance(MainActivity.this).searchFruits(query);
            //Using the line below instead of the comment above because searchable is not working.
            Cursor searchCursor = db.searchFruits(query);

            listView = (ListView)findViewById(R.id.mainListView);
            if (cursorAdapter == null) {
                cursorAdapter = new SimpleCursorAdapter(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        searchCursor,
                        new String[]{DatabaseHandler.COL_NAME},
                        new int[]{android.R.id.text1},
                        0
                );
                listView.setAdapter(cursorAdapter);

            }else {
                cursorAdapter.swapCursor(searchCursor);
                //cursorAdapter.notifyDataSetChanged();
                //Commenting the line above because 1. It's not needed 2. To sync it with an older version of a working project.
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }


}
