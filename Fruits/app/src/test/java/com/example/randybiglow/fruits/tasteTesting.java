package com.example.randybiglow.fruits;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by RandyBiglow on 5/6/16.
 */
public class tasteTesting {
    @Test
    public void test(){
        //Testing Database
        assertEquals("NAME", DatabaseHandler.COL_NAME);
        assertEquals("_id", DatabaseHandler.COL_ID);
        assertEquals("REGION", DatabaseHandler.COL_REGION);
        assertEquals("FruitsDatabaseName.db", DatabaseHandler.DATABASE_NAME);
        assertNotNull( DatabaseHandler.COL_DESCRIPTION);
        //My favorite test: assertNotNull!

        assertNotNull(MainActivity.class);
        //Experimented with Front-end testing, but only came up with the existence of the MainActivity class.
        //This line of test is pointless.
    }


}
