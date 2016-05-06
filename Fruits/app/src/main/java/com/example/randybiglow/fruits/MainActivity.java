package com.example.randybiglow.fruits;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        //Seeding database
        db.addFruits(new Fruits("Jackfruit", "Asia", "Summer","antibacterial","yellow pieces of heaven"));
        db.addFruits(new Fruits("Mangosteen", "Asia", "Fall", "diuretic", "purple exterior with white flesh"));
        db.addFruits(new Fruits("Cherimoya", "North America", "Spring", "anti-inflammatory","sweet custard"));
        db.addFruits(new Fruits("Lychee", "Asia", "Winter", "anti-acid", "crispy grapes"));
        db.addFruits(new Fruits("Pitaya", "Asia", "Fall", "anti-aging", "mild kiwi"));
        db.addFruits(new Fruits("Waterapple", "Asia", "Spring", "anti-diabetes", "tangy apple"));
        db.addFruits(new Fruits("Breadfruit", "Africa", "Summer", "antioxidants", "spongy"));
        db.addFruits(new Fruits("Soursop", "South America", "Spring","antimicrobial", "sweet and tangy custard"));
        db.addFruits(new Fruits("Redcurrant", "Europe", "Summer", "anti-coagulant", "bright red goodness"));
        db.addFruits(new Fruits("Finger Lime", "Australia", "Winter", "antioxidant", "beautiful sour beings"));
        db.addFruits(new Fruits("Ganga", "North America", "Spring", "anything", "buds, lots of buds"));
    }
}
