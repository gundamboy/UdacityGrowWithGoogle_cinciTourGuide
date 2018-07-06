package com.wickedsword.retar.cincitourguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String CATEGORY = "category";
    private static final String THINGS_TO_DO_CATEGORY = "things_to_do";
    private static final String FOOD_CATEGORY = "eat_and_drink";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView things_to_do = (TextView) findViewById(R.id.things_to_do);
        TextView landmarks_to_see = (TextView) findViewById(R.id.landmarks);
        TextView eat_and_drink = (TextView) findViewById(R.id.eat_drink);
        TextView city_history = (TextView) findViewById(R.id.history);

        things_to_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set up the intent
                Intent thingsToDoIntent = new Intent(MainActivity.this, SubcategoryActivity.class);
                thingsToDoIntent.putExtra(CATEGORY, THINGS_TO_DO_CATEGORY);

                // Start the new activity
                startActivity(thingsToDoIntent);
            }
        });

        landmarks_to_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "landmarks to see", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        eat_and_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set up the intent
                Intent foodIntent = new Intent(MainActivity.this, SubcategoryActivity.class);
                foodIntent.putExtra(CATEGORY, FOOD_CATEGORY);

                // Start the new activity
                startActivity(foodIntent);
            }
        });

        city_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "city history", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
