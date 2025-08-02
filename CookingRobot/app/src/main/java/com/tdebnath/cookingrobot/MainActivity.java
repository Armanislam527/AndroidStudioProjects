package com.tdebnath.cookingrobot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare the buttons from our layout file
    private Button buttonHotchpotch;
    private Button buttonNoodles;
    private Button buttonVegetables;
    private Button buttonFriedRice;
    private Button buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect our declared buttons to the actual buttons in the layout
        // using their unique IDs.
        buttonHotchpotch = findViewById(R.id.button_hotchpotch);
        buttonNoodles = findViewById(R.id.button_noodles);
        buttonVegetables = findViewById(R.id.button_vegetables);
        buttonFriedRice = findViewById(R.id.button_fried_rice);
        buttonExit = findViewById(R.id.button_exit);

        // Set up a click listener for the Hotchpotch button
        buttonHotchpotch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the function to open the recipe screen, passing the menu title
                openRecipeActivity(getString(R.string.menu_hotchpotch));
            }
        });

        // Set up a click listener for the Noodles button
        buttonNoodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipeActivity(getString(R.string.menu_noodles));
            }
        });

        // Set up a click listener for the Vegetables button
        buttonVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipeActivity(getString(R.string.menu_vegetables));
            }
        });

        // Set up a click listener for the Fried Rice button
        buttonFriedRice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipeActivity(getString(R.string.menu_fried_rice));
            }
        });

        // Set up a click listener for the Exit button
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity and close the app
                finish();
            }
        });
    }

    /**
     * A helper method to start the RecipeActivity.
     * @param menuTitle The title of the menu that was clicked (e.g., "Hotchpotch").
     */
    private void openRecipeActivity(String menuTitle) {
        // An Intent is used to start a new activity (screen)
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);

        // We can pass data to the next screen using "extras".
        // Here, we're passing the title of the menu.
        intent.putExtra("MENU_TITLE", menuTitle);

        // Start the new activity
        startActivity(intent);
    }
}
